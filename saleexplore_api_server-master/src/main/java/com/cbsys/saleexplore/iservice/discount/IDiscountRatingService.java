package com.cbsys.saleexplore.iservice.discount;

import com.cbsys.saleexplore.entity.DiscountRating;
import com.cbsys.saleexplore.payload.DiscountRatingCountPd;

public interface IDiscountRatingService {
    /**
     * user rate a discount
     * @return true if succeed
     */
    boolean userRate(DiscountRating rating);

    /**
     * get a discount rating from db
     */
    DiscountRating getRating(Long userId, Long discountId);

    /**
     * delete a discount rating from db
     */
    void deleteRating(Long userId, Long discountId);

    /**
     * update the number of discount rating, which are likes/dislikes
     */
    void updateDiscountRatingCount(long id, DiscountRatingCountPd countPd);

    /**
     * get the number of discount rating, which are likes/dislikes
     */
    DiscountRatingCountPd getDiscountRatingCount(long discountId);

}
