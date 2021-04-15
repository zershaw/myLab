package com.cbsys.saleexplore.service.discount;

import com.cbsys.saleexplore.iservice.ICategoryService;
import com.cbsys.saleexplore.iservice.discount.IDiscountRecommendService;
import com.cbsys.saleexplore.config.RedisConstant;
import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.entity.UserInterests;
import com.cbsys.saleexplore.enums.DiscountOrderByEm;
import com.cbsys.saleexplore.idao.IDiscountDAO;
import com.cbsys.saleexplore.idao.IUserInterestsDAO;
import com.cbsys.saleexplore.payload.pagi.QueryDiscountPd;
import com.cbsys.saleexplore.compos.search.searcher.DiscountLucSearcher;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DiscountRecommendService implements IDiscountRecommendService {

    @Autowired
    private IDiscountDAO discountDAO;

    @Autowired
    private IUserInterestsDAO userInterestsDAO;

    @Autowired
    private DiscountLucSearcher discountIndexSearcher;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    private static final int TOPK_HIGH_USER_INTEREST_CAT = 2;
    private static final int TOPK_RECENT_USER_INTEREST_CAT = 2;
    private static final float INTERESTED_PERCENT = 0.8f;

    @Override
    public List<Discount> recommendDiscounts(QueryDiscountPd requestPd,
                                             long userId) {
        // TODO, load more when the first page cached in redis is not enough.
        try {
            // we create a copy of request, the copy is going to be used for passing to the database sessions
            QueryDiscountPd requestPdTmp = new QueryDiscountPd(requestPd);
            List<Long> recomDiscountIds = null;

            StringBuilder recoKey = new StringBuilder(RedisConstant.RECO_DISCOUNT_CITY + userId + ":" + requestPd.getCityId());
            // The following suffix is for each tab of the frontend
            if (requestPdTmp.getOrderBy() == DiscountOrderByEm.DISTANCE) {
                recoKey.append(":nearby");
            }else if(requestPdTmp.isOnlyUpComing() == true){
                recoKey.append(":upcoming");
            }

            if (requestPd.getMaxId() == 0) {
                /**
                 * a new reco-session of this user
                 * we first remove previous cache
                 */
                redisTemplate.delete(recoKey.toString());
                recomDiscountIds = fetchNewItems(requestPdTmp, userId, requestPd.getCityId());

                if (recomDiscountIds != null) {
                    // save to the redis
                    redisTemplate.opsForList().rightPushAll(recoKey.toString(), recomDiscountIds);
                    redisTemplate.expire(recoKey.toString(), RedisConstant.RECO_CACHE_DISCOUNT_EXPIRE, TimeUnit.SECONDS);

                    // we can directly get the results here
                    if (recomDiscountIds.size() > requestPd.getLimit()) {
                        recomDiscountIds = recomDiscountIds.subList(0, requestPd.getLimit());
                    }
                }

            } else {
                /**
                 * now there is a redis-cache already available.
                 */
                recomDiscountIds = redisTemplate.opsForList().range(recoKey.toString(), requestPd.getMaxId() + 1,
                        requestPd.getMaxId() + requestPd.getLimit());

                // TODO paging to load more when the cache is not enough. not easy for the current reco-method
            }

            // get the store details
            if (recomDiscountIds != null && recomDiscountIds.size() > 0) {
                return discountDAO.get(recomDiscountIds, null, null);
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
    private List<Long> fetchNewItems(QueryDiscountPd requestPdTmp,
                                     long userId,
                                     int cityId) {
        try {
            List<Long> interestedDiscountIds = null;
            List<Long> latestDiscountIds = null;

            // get the user's intersted categories
            Set<String> interestedCats = getInterestedCats(userId);
            if (interestedCats.size() > 0) {
                // if the user has some interested categories
                Set<Long> interestedIdsSet = new HashSet<>();

                for (String catName : interestedCats) {
                    // for each category we return 100 results
                    List<Long> resIds = discountIndexSearcher.searchWithSpecialChar(catName, cityId + "", 100);
                    for (Long resId : resIds) {
                        interestedIdsSet.add(resId);
                    }
                }
                List<Long> interestedIdsList = new ArrayList<>();
                interestedIdsList.addAll(interestedIdsSet);

                // make sure not a single category cover all the things
                Collections.shuffle(interestedIdsList);
                requestPdTmp.setLimit((int) (RedisConstant.RECO_CACHE_DISCOUNT_CITY_PAGE * INTERESTED_PERCENT));
                interestedDiscountIds = discountDAO.getIds(interestedIdsList, requestPdTmp, null);

            }

            // except the interested ones, we will some latest ones, here we set the number of latest ones to get
            if (interestedDiscountIds != null) {
                requestPdTmp.setLimit(RedisConstant.RECO_CACHE_DISCOUNT_CITY_PAGE - interestedDiscountIds.size());
            } else {
                requestPdTmp.setLimit(RedisConstant.RECO_CACHE_DISCOUNT_CITY_PAGE);
            }

            // get the latest ones, exclude the already fetched ones interestedDiscountIds
            latestDiscountIds = discountDAO.getIds(null, requestPdTmp, interestedDiscountIds);

            // combine the final discountIds
            if (!CollectionUtils.isEmpty(interestedDiscountIds)) {
                if(!CollectionUtils.isEmpty(latestDiscountIds)){
                    interestedDiscountIds.addAll(latestDiscountIds);
                }
                return interestedDiscountIds;
            }else if(!CollectionUtils.isEmpty(latestDiscountIds)){
                return latestDiscountIds;
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
