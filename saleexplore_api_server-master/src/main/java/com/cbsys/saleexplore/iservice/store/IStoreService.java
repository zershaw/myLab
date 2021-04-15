package com.cbsys.saleexplore.iservice.store;

import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.entity.Store;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface IStoreService {

    /**
     * get a single store by storeId
     */
    Store getStore(long id);


    /**
     * when we need to put the store information to the discounts.
     * the store information are treated as non-persistent infor of the discount
     * we use this function to load.
     */
    void setDiscountListStore(List<Discount> discounts, float latitude, float longitude);

    /**
     * in the discount detail page, we also need to display some store information
     */
    void setDiscountDetailStore(Discount discount);


    /**
     * update the last time discount updated time
     * @param storeId 0 will update
     */
    void updateNumberOfDiscount(long storeId);

    /**
     * deserialize the opening hours for the stores
     */
    void setOpeningHours(List<Store> stores);

    /**
     * deserialize the opening hours for the store
     */
    void setOpeningHours(Store store);


    void pushStoreToLuceneIndex(Store store);

    void insertStoreSearchHistory(long userId, List<Store> stores);
}
