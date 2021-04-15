package com.cbsys.saleexplore.service.store;

import com.cbsys.saleexplore.iservice.IBrandService;
import com.cbsys.saleexplore.iservice.store.IStoreBrandService;
import com.cbsys.saleexplore.iservice.store.IStoreService;
import com.cbsys.saleexplore.compos.search.builder.StoreLucIndexBuilder;
import com.cbsys.saleexplore.compos.search.record.StoreLucRecord;
import com.cbsys.saleexplore.entity.Brand;
import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.idao.IStoreDAO;
import com.cbsys.saleexplore.payload.DiscountDetailStorePd;
import com.cbsys.saleexplore.payload.DiscountListStorePd;
import com.cbsys.saleexplore.payload.pagi.QueryStorePd;
import com.cbsys.saleexplore.util.OpeningHourUtil;
import com.cbsys.saleexplore.util.SearchCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StoreService implements IStoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private IStoreDAO storeDAO;

    @Autowired
    private StoreLucIndexBuilder indexBuilder;

    @Autowired
    private IStoreBrandService storeBrandService;

    @Autowired
    private IBrandService brandService;


    @Override
    public Store getStore(long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);

        QueryStorePd qpd = new QueryStorePd();
        List<Store> stores = storeDAO.get(ids, qpd, null);

        if (stores.size() >= 1) {
            return stores.get(0);
        } else {
            return null;
        }
    }


    @Override
    public void setDiscountListStore(List<Discount> discounts, float latitude, float longitude) {
        if (discounts == null || discounts.size() == 0) {
            return;
        }
        /*
         * We load some store information to the discount
         * the store information not persistent in the discount table
         */

        List<Long> storeIds = new ArrayList<>();
        Map<Long, List<Discount>> mapDiscounts = new HashMap<>();

        for (Discount discount : discounts) {

            storeIds.add(discount.getStoreId());

            if (!mapDiscounts.containsKey(discount.getStoreId())) {

                // if the key not there
                List<Discount> arrayDiscounts = new ArrayList<>();

                arrayDiscounts.add(discount);

                mapDiscounts.put(discount.getStoreId(), arrayDiscounts);

            } else {

                // if the key already there.
                mapDiscounts.get(discount.getStoreId()).add(discount);
            }
        }

        if (storeIds.size() != 0) {
            // If the discountIds has nothing, the storeIds will be nothing.
            List<DiscountListStorePd> stores = storeDAO.getDiscountListStorePd(storeIds, latitude, longitude);
            for (DiscountListStorePd store : stores) {
                for (Discount discount : mapDiscounts.get(store.getId())) {
                    discount.setStore(store);
                }
            }
        }
    }

    @Override
    public void setDiscountDetailStore(Discount discount) {
        DiscountDetailStorePd store = storeDAO.getDiscountDetailStorePd(discount.getStoreId());
        discount.setStore(store);
    }

    @Override
    @Async
    public void updateNumberOfDiscount(long storeId) {
        // update the number of discounts for all the stores
        storeDAO.updateDiscountNumber(storeId);
    }



    /**
     * set the store opening hour for the stores and isOpenNow
     */
    @Override
    public void setOpeningHours(List<Store> stores) {
        for (Store store : stores) {
            setOpeningHours(store);
        }
    }

    @Override
    public void setOpeningHours(Store store) {
        if (store.getOpeningHours() == null || store.getOpeningHours().length() == 0) {
            // we don't have opening hour data
            return;
        }

        List<String[]> deserializedHour = OpeningHourUtil.openingHoursDeserializer(store.getOpeningHours());

        // deserialize the opening hours from encoded format
        StringBuilder hourString = new StringBuilder();
        for (String[] hour : deserializedHour) {
            hourString.append(hour[0]);
            hourString.append(",");
            hourString.append(hour[1]);
            hourString.append(";");
        }
        store.setOpeningHours(hourString.toString());
        store.setIsOpenNow(OpeningHourUtil.isOpen(deserializedHour) == true ? 1 : 0);
    }



    /**
     * push to the lucene index
     */
    @Override
    @Async
    public void pushStoreToLuceneIndex(Store store){

        StoreLucRecord record = new StoreLucRecord();

        record.setId(store.getId());

        // get the store brand
        List<Integer> brandIds = storeBrandService.getStoreBrandIds(store.getId());
        List<Brand> brands = brandService.getBrands(brandIds);
        StringBuilder stringBuilder = new StringBuilder();
        for(Brand brand : brands){
            stringBuilder.append(brand.getName() + " ");
        }

        record.setBrand(stringBuilder.toString());
        StringBuilder category = new StringBuilder();
        for(Category cat : store.getCategories()){
            category.append(cat.getName() + ",");
        }
        record.setCategory(category.toString());

        record.setInfoDescription(store.getInfoDescription());
        record.setMallName(store.getMallName());
        record.setStoreName(store.getName());
        record.setCityId(store.getCityId());

        indexBuilder.insert(record);
    }

    @Override
    @Async
    public void insertStoreSearchHistory(long userId, List<Store> stores) {
        try {
            LOGGER.info("begin to update userId {} store search cache", userId);
            Set<String> storeIds = stores.stream().map(store -> String.valueOf(store.getId())).collect(Collectors.toSet());
            redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
                redisConnection.multi();
                SearchCacheUtil.insertStoreSearch(redisConnection, userId, storeIds);
                redisConnection.exec();
                return null;
            });
        } catch (Exception e) {
            LOGGER.error("exception occurs during insert search history", e);
        }
    }


}
