package com.cbsys.saleexplore.service.store;

import com.cbsys.saleexplore.iservice.IBrandService;
import com.cbsys.saleexplore.iservice.store.IStoreBrandService;
import com.cbsys.saleexplore.iservice.store.IStoreSearchService;
import com.cbsys.saleexplore.compos.nlp.POSTagger;
import com.cbsys.saleexplore.compos.search.builder.StoreLucIndexBuilder;
import com.cbsys.saleexplore.compos.search.record.StoreLucRecord;
import com.cbsys.saleexplore.compos.search.searcher.StoreLucSearcher;
import com.cbsys.saleexplore.config.RedisConstant;
import com.cbsys.saleexplore.entity.Brand;
import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.idao.IStoreDAO;
import com.cbsys.saleexplore.payload.pagi.QueryStorePd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreSearchService implements IStoreSearchService {

    @Autowired
    private IStoreDAO storeDAO;

    @Autowired
    private POSTagger posTagger;

    @Autowired
    private IStoreBrandService storeBrandService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private StoreLucIndexBuilder storeLucIndexBuilder;

    @Autowired
    private StoreLucSearcher storeIndexSearcher;


    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    @Override
    public List<Store> searchStores(QueryStorePd requestPd, long userId) {

        List<Store> results = null;
        List<Long> storeIds = null;
        if (requestPd.getKwQuery() == null || requestPd.getKwQuery().length() == 0) {
            return null;
        }

        String searchRedisKey = String.join("", RedisConstant.SEARCH_KW_STORE,
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
                storeIds = storeIndexSearcher.searchWithSpecialChar(requestPd.getKwQuery(), requestPd.getCityId() + "", 200);
                if (storeIds == null || storeIds.size() == 0) {
                    return new ArrayList<>();
                }
                // we can directly get the results here
                if(CollectionUtils.isEmpty(storeIds)){
                    return new ArrayList<>();
                }
                storeIds = storeIds.subList(0, requestPd.getLimit());
            }
            else{
                /**
                 * now there is a redis-cache already available.
                 */
                storeIds = redisTemplate.opsForList().range(searchRedisKey, requestPd.getMaxId() + 1,
                        requestPd.getMaxId() + requestPd.getLimit());
            }

            results = storeDAO.get(storeIds, null, null);

            return results;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void resetLuceneIndex() {

        storeLucIndexBuilder.deleteAll();

        List<Store> stores = storeDAO.get(null, null, null);
        List<StoreLucRecord> records = new ArrayList<>();
        for (Store store : stores) {
            StoreLucRecord record = new StoreLucRecord();

            record.setId(store.getId());

            // get the store brand
            List<Integer> brandIds = storeBrandService.getStoreBrandIds(store.getId());
            if(brandIds != null && brandIds.size() > 0){
                List<Brand> brands = brandService.getBrands(brandIds);
                StringBuilder stringBuilder = new StringBuilder();
                for (Brand brand : brands) {
                    stringBuilder.append(brand.getName() + " ");
                }
                record.setBrand(stringBuilder.toString());
            }
            // category
            StringBuilder category = new StringBuilder();
            for (Category cat : store.getCategories()) {
                category.append(cat.getName() + ",");
            }
            record.setCategory(category.toString());

            record.setInfoDescription(store.getInfoDescription());
            record.setMallName(store.getMallName());
            record.setStoreName(store.getName());
            record.setCityId(store.getCityId());

            records.add(record);
        }

        storeLucIndexBuilder.insert(records);
    }




}
