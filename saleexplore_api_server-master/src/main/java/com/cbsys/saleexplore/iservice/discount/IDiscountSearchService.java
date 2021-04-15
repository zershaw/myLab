package com.cbsys.saleexplore.iservice.discount;

import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.payload.pagi.QueryDiscountPd;

import java.util.List;

public interface IDiscountSearchService {
    /**
     * specific searching of discounts
     */
    List<Discount> searchDiscounts(final QueryDiscountPd requestPd, long userId);


    /**
     * search similar products
     */
    List<Discount> searchSimilarDiscounts(List<Discount> discounts, long userId, int cityId);


    /**
     * reset the lucene index for the discounts
     */
    void resetLuceneIndex();


}
