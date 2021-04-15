package com.cbsys.saleexplore.service.store;

import com.cbsys.saleexplore.iservice.ICategoryService;
import com.cbsys.saleexplore.iservice.store.IStoreRecommendService;
import com.cbsys.saleexplore.compos.search.searcher.StoreLucSearcher;
import com.cbsys.saleexplore.config.RedisConstant;
import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.entity.UserInterests;
import com.cbsys.saleexplore.enums.StoreOrderByEm;
import com.cbsys.saleexplore.idao.IStoreDAO;
import com.cbsys.saleexplore.idao.IUserInterestsDAO;
import com.cbsys.saleexplore.payload.pagi.QueryStorePd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class StoreRecommendService implements IStoreRecommendService {

    @Autowired
    private IStoreDAO storeDAO;

    @Autowired
    private StoreLucSearcher storeIndexSearcher;


    @Autowired
    private IUserInterestsDAO userInterestsDAO;


    @Autowired
    private RedisTemplate<String, Long> redisTemplate;


    @Autowired
    private ICategoryService categoryService;


    private static final int TOPK_HIGH_USER_INTEREST_CAT = 2;
    private static final int TOPK_RECENT_USER_INTEREST_CAT = 2;
    private static final float INTERESTED_PERCENT = 0.8f;


    @Override
    public List<Store> recommendStores(QueryStorePd requestPd, long userId) {
        // TODO, load more when the first page cached in redis is not enough.
        try {
            // we create a copy of request, the copy is going to be used for passing to the database sessions
            QueryStorePd requestPdTmp = new QueryStorePd(requestPd);
            List<Long> recommendStoreIds = null;

            StringBuilder recoKey = new StringBuilder(RedisConstant.RECO_STORE_CITY + userId + ":" + requestPd.getCityId());
            // if order by distance which for nearby
            if (requestPdTmp.getOrderBy() == StoreOrderByEm.DISTANCE) {
                recoKey.append(":nearby");
            }

            if (requestPd.getMaxId() == 0) {
                /**
                 * a new reco-session of this user
                 * we first remove previous cache
                 */
                redisTemplate.delete(recoKey.toString());
                recommendStoreIds = fetchNewItems(requestPdTmp, userId, requestPd.getCityId());

                if (recommendStoreIds != null) {
                    // save to the redis
                    redisTemplate.opsForList().rightPushAll(recoKey.toString(), recommendStoreIds);
                    redisTemplate.expire(recoKey.toString(), RedisConstant.RECO_CACHE_STORE_EXPIRE, TimeUnit.SECONDS);

                    // we can directly get the results here
                    if (recommendStoreIds.size() > requestPd.getLimit()) {
                        recommendStoreIds = recommendStoreIds.subList(0, requestPd.getLimit());
                    }
                }
            } else {
                /**
                 * now there is a redis-cache already available.
                 */
                recommendStoreIds = redisTemplate.opsForList().range(recoKey.toString(), requestPd.getMaxId() + 1,
                        requestPd.getMaxId() + requestPd.getLimit());
                // TODO paging to load more when the cache is not enough. not easy for the current reco-method
            }

            // get the store details
            if (recommendStoreIds != null && recommendStoreIds.size() > 0) {
                return storeDAO.get(recommendStoreIds, null, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * The current reco method is to get some user interested items based on user's interestes categories
     * Then we get some latest items. and combine with the interested items. We do a shuffle after combine
     * The intestes take a percent of INTERESTED_PERCENT and the total of items is: RECO_CACHE_STORE_CITY_PAGE
     */
    private List<Long> fetchNewItems(QueryStorePd requestPdTmp,
                                     long userId,
                                     int cityId) {
        try {
            List<Long> interestedStoreIds = null;
            List<Long> latestStoreIds = null;

            // get the user's intersted categories
            Set<String> interestedCats = getInterestedCats(userId);
            if (interestedCats.size() > 0) {
                // if the user has some interested categories
                Set<Long> interestedIdsSet = new HashSet<>();
                for (String catName : interestedCats) {
                    // for each category we return 100 results
                    List<Long> resIds = storeIndexSearcher.searchWithSpecialChar(catName, cityId + "", 100);
                    for (long resId : resIds) {
                        interestedIdsSet.add(resId);
                    }
                }
                List<Long> interestedIdsList = new ArrayList<>();
                interestedIdsList.addAll(interestedIdsSet);

                // make sure not a single category cover all the things
                Collections.shuffle(interestedIdsList);

                requestPdTmp.setLimit((int) (RedisConstant.RECO_CACHE_STORE_CITY_PAGE * INTERESTED_PERCENT));
                interestedStoreIds = storeDAO.getIds(interestedIdsList, requestPdTmp, null);

            }

            // except the interested ones, we will some latest ones, here we set the number of latest ones to get
            if (interestedStoreIds != null) {
                requestPdTmp.setLimit(RedisConstant.RECO_CACHE_STORE_CITY_PAGE - interestedStoreIds.size());
            } else {
                requestPdTmp.setLimit(RedisConstant.RECO_CACHE_STORE_CITY_PAGE);
            }
            // get the latest ones, exclude the already fetched ones interestedDiscountIds
            latestStoreIds = storeDAO.getIds(null, requestPdTmp, interestedStoreIds);


            // combine the final discountIds
            if (!CollectionUtils.isEmpty(interestedStoreIds)) {
                // we do a shuffle after combine
                if(!CollectionUtils.isEmpty(latestStoreIds)){
                    interestedStoreIds.addAll(latestStoreIds);
                }
                return interestedStoreIds;
            }else if(!CollectionUtils.isEmpty(latestStoreIds)){
                return latestStoreIds;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private Set<String> getInterestedCats(long userId) {

        List<UserInterests> topKHighest = userInterestsDAO.getTopKHighest(userId, TOPK_HIGH_USER_INTEREST_CAT);
        List<UserInterests> topKRecent = userInterestsDAO.getTopKRecent(userId, TOPK_RECENT_USER_INTEREST_CAT);
        Set<String> catsNames = new HashSet<>();
        for (UserInterests ui : topKHighest) {
            Category cat = categoryService.getCategoryById(ui.getCategoryId());
            if (cat != null) {
                catsNames.add(cat.getName());
            }
        }
        for (UserInterests ui : topKRecent) {
            Category cat = categoryService.getCategoryById(ui.getCategoryId());
            if (cat != null) {
                catsNames.add(cat.getName());
            }
        }
        return catsNames;
    }
}
