package com.cbsys.saleexplore.service.discount;

import com.cbsys.saleexplore.iservice.ICategoryService;
import com.cbsys.saleexplore.iservice.discount.IDiscountSearchService;
import com.cbsys.saleexplore.compos.nlp.POSTagger;
import com.cbsys.saleexplore.compos.search.builder.DiscountLucIndexBuilder;
import com.cbsys.saleexplore.compos.search.record.DiscountLucRecord;
import com.cbsys.saleexplore.compos.search.searcher.DiscountLucSearcher;
import com.cbsys.saleexplore.config.RedisConstant;
import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.idao.IDiscountDAO;
import com.cbsys.saleexplore.idao.IStoreDAO;
import com.cbsys.saleexplore.payload.DiscountDetailStorePd;
import com.cbsys.saleexplore.payload.pagi.QueryDiscountPd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class DiscountSearchService implements IDiscountSearchService {

    private Set<String> blackList;

    @Autowired
    private POSTagger posTagger;

    @Autowired
    private IStoreDAO storeDAO;


    @Autowired
    private IDiscountDAO discountDAO;

    @Autowired
    private ICategoryService categoryService;


    @Autowired
    private DiscountLucSearcher discountIndexSearcher;

    @Autowired
    private DiscountLucIndexBuilder discountLucIndexBuilder;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;


    public DiscountSearchService(POSTagger posTagger) {
        this.posTagger = posTagger;
        blackList = new HashSet<>();

        blackList.add("pair");
        blackList.add("pairs");
        blackList.add("piece");
        blackList.add("pieces");
        blackList.add("kilo");
        blackList.add("kilos");
        blackList.add("batch");
        blackList.add("batches");
        blackList.add("drop");
        blackList.add("drops");
        blackList.add("hive");
        blackList.add("hives");
        blackList.add("hundred");
        blackList.add("anything");
        blackList.add("something");
        blackList.add("nothing");
        blackList.add("hundreds");
    }


    @Override
    public List<Discount> searchDiscounts(QueryDiscountPd requestPd, long userId) {
        // check for null
        List<Discount> results = null;
        List<Long> discountIds;
        if (StringUtils.isEmpty(requestPd.getKwQuery())) {
            return null;
        }

        String searchRedisKey = String.join("", RedisConstant.SEARCH_KW_DISCOUNT,
                userId + ":" + requestPd.getCityId());

        try {
            if(requestPd.getMaxId() == 0) {
                /**
                 * a new search-session of this user
                 * we first remove previous cache
                 */
                redisTemplate.delete(searchRedisKey);

                // lemmatize the query so as to make plural easy search
                requestPd.setKwQuery(posTagger.lemmatizeByStandfordNLPCore(requestPd.getKwQuery()));

                // TODO, those Ids must be using filter again. BUT we haven't run thourgh this
                // List<Long> discountIds = new ArrayList<>();
                discountIds = discountIndexSearcher.searchWithSpecialChar(requestPd.getKwQuery(),
                        requestPd.getCityId() + "", 200);

                if (discountIds == null || discountIds.size() == 0) {
                    return new ArrayList<>();
                }

                redisTemplate.opsForList().rightPushAll(searchRedisKey, discountIds);
                redisTemplate.expire(searchRedisKey, RedisConstant.SEARCH_KW_EXPIRE, TimeUnit.SECONDS);

                // we can directly get the results here
                if (discountIds.size() > requestPd.getLimit()) {
                    discountIds = discountIds.subList(0, requestPd.getLimit());
                }

            }else{
                /**
                 * now there is a redis-cache already available.
                 */
                discountIds = redisTemplate.opsForList().range(searchRedisKey, requestPd.getMaxId() + 1,
                        requestPd.getMaxId() + requestPd.getLimit());
            }

            if (CollectionUtils.isEmpty(discountIds)) {
                return new ArrayList<>();
            }

            results = discountDAO.get(discountIds, null, null);

            return results;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    @Override
    public List<Discount> searchSimilarDiscounts(List<Discount> discounts, long userId, int cityId) {
        // compose the keyword query
        StringBuilder query = new StringBuilder();
        Category cat = categoryService.getCategoryById(discounts.get(0).getCategoryId());
        if (cat != null) {
            query.append(cat.getName());
        }

        // List<Long> discountIds = new ArrayList<>();
        try {
            List<Long> similarDiscounts = discountIndexSearcher.searchWithSpecialChar(query.toString(), cityId + "", 100);
            if (CollectionUtils.isEmpty(similarDiscounts)) {
                return new ArrayList<>();
            }

            QueryDiscountPd requestPd = new QueryDiscountPd();
            requestPd.setCityId(cityId);
            requestPd.setLimit(10);
            List<Discount> results = discountDAO.get(similarDiscounts, requestPd, null);

            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void resetLuceneIndex() {
        discountLucIndexBuilder.deleteAll();
        QueryDiscountPd qpd = new QueryDiscountPd();
        qpd.setOnlyValid(true);

        List<Discount> discounts = discountDAO.get(null, qpd, null);
        List<DiscountLucRecord> records = new ArrayList<>();

        for(Discount discount : discounts){
            DiscountLucRecord record = new DiscountLucRecord();
            record.setId(discount.getId());
            record.setInfoDescription(discount.getInfoDescription());

            DiscountDetailStorePd store = storeDAO.getDiscountDetailStorePd(discount.getStoreId());

            record.setMallName(store.getMallName());
            record.setTitle(discount.getTitle());
            record.setStoreName(store.getName());
            record.setCityId(store.getCityId());

            Category category = categoryService.getCategoryById(discount.getCategoryId());
            if(category != null){
                record.setCategory(category.getName());
            }

            records.add(record);
        }

        discountLucIndexBuilder.insert(records);

    }

}
