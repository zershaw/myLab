package com.cbsys.saleexplore.service.discount;

import com.cbsys.saleexplore.exception.BadRequestEn;
import com.cbsys.saleexplore.iservice.ICategoryService;
import com.cbsys.saleexplore.iservice.discount.IDiscountService;
import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.entity.DiscountImage;
import com.cbsys.saleexplore.idao.IDiscountDAO;
import com.cbsys.saleexplore.idao.IDiscountImageDAO;
import com.cbsys.saleexplore.idao.IStoreDAO;
import com.cbsys.saleexplore.payload.DiscountDetailStorePd;
import com.cbsys.saleexplore.payload.pagi.QueryDiscountPd;
import com.cbsys.saleexplore.compos.search.builder.DiscountLucIndexBuilder;
import com.cbsys.saleexplore.compos.search.record.DiscountLucRecord;
import com.cbsys.saleexplore.util.SearchCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class DiscountService implements IDiscountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountService.class);

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private IDiscountDAO discountDAO;

    @Autowired
    private IStoreDAO storeDAO;

    @Autowired
    private IDiscountImageDAO discountImageDAO;

    @Autowired
    private DiscountLucIndexBuilder indexBuilder;

    @Autowired
    private ICategoryService categoryService;


    @Override
    public Discount getDiscount(Long id) {

        // the discount images will be set in the get method
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<Discount> discounts = discountDAO.get(ids, null, null);

        if (discounts.size() == 1) {
            return discounts.get(0);
        } else {
            throw new BadRequestEn("Cannot find specific item");
        }
    }

    @Override
    public List<Discount> getDiscounts(QueryDiscountPd qPd) {

        List<Discount> discounts = discountDAO.get(null, qPd, null);

        return discounts;
    }



    @Override
    @Transactional
    public long createDiscount(Discount discount) {
        /*
         * first insert a discount and then insert the images
         */
        discountDAO.insert(discount);
        if (discount.getDiscountImages() != null) {
            for (DiscountImage image : discount.getDiscountImages()) {
                image.setDiscountId(discount.getId());
                discountImageDAO.insert(image);
            }
        }

        // push to index asynchronously
        pushDiscountToLuceneIndex(discount);

        return discount.getId();
    }

    @Override
    public void increaseViewCount(long discountId) {
        discountDAO.increaseViewCount(discountId);
    }



    /**
     * push the discount to the lucene index
     */
    @Override
    @Async
    public void pushDiscountToLuceneIndex(Discount discount){

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

        indexBuilder.insert(record);
    }

    @Override
    @Async
    public void insertDiscountSearchHistory(long userId, List<Discount> discounts) {
        try {
            LOGGER.info("begin to update userId {} discount search cache", userId);
            Set<String> storeIds = discounts.stream().map(discount -> String.valueOf(discount.getStoreId())).collect(Collectors.toSet());
            Set<String> categoryIds = discounts.stream().map(discount -> String.valueOf(discount.getCategoryId())).collect(Collectors.toSet());
            redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
                redisConnection.multi();
                SearchCacheUtil.insertStoreSearch(redisConnection, userId, storeIds);
                SearchCacheUtil.insertCategorySearch(redisConnection, userId, categoryIds);
                redisConnection.exec();
                return null;
            });

        } catch (Exception e) {
            LOGGER.error("exception occurs during insert search history", e);
        }
    }


}
