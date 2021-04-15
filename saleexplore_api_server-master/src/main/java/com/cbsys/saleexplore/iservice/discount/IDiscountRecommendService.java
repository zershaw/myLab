package com.cbsys.saleexplore.iservice.discount;

import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.payload.pagi.QueryDiscountPd;

import java.util.List;


public interface IDiscountRecommendService {

    /**
     * recommendation discounts to the users
     */
    List<Discount> recommendDiscounts(final QueryDiscountPd requestPd, long userId);



}
