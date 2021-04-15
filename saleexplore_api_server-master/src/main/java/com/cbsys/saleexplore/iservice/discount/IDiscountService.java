package com.cbsys.saleexplore.iservice.discount;


import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.payload.pagi.QueryDiscountPd;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface IDiscountService {

    /**
     * get details of a single discount
     */
    Discount getDiscount(Long id);

    /**
     * return a list of discounts
     */
    List<Discount> getDiscounts(final QueryDiscountPd qPd);

    /**
     * create a new discount and save to db
     * @return  the id of the newly created discount
     */
    long createDiscount(Discount discount);


    /**
     *  increase the view counts of this discount by 1
     */
    void increaseViewCount(long discountId);

    void pushDiscountToLuceneIndex(Discount discount);

    void insertDiscountSearchHistory(long userId, List<Discount> discounts);
}
