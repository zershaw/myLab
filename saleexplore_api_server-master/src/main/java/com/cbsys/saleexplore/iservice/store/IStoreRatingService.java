package com.cbsys.saleexplore.iservice.store;

import com.cbsys.saleexplore.entity.StoreRating;

public interface IStoreRatingService {

    /**
     * user rate a store
     * @return true if succeed
     */
    boolean userRate(StoreRating rating);

    /**
     * get a specific StoreRating by userId and storeId
     * @param userId the user's id
     * @param storeId the store id
     */
    StoreRating getRating(Long userId, Long storeId);


    void deleteRating(Long userId, Long storeId);

    void updateStorePopularity(long id);
}
