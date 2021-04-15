package com.cbsys.saleexplore.iservice.store;

import com.cbsys.saleexplore.entity.Store;

import java.util.List;

public interface IStoreImageService {
    /**
     * add the base URL to the baseImage
     * isThumb to indicate to load thumbnail of resize image
     */
    void addBaseImageUrl(List<Store> stores, boolean isThumb);

    /**
     * add the base URL to the baseImage
     * isThumb to indicate to load thumbnail of resize image
     */
    void addBaseImageUrl(Store store, boolean isThumb);
}
