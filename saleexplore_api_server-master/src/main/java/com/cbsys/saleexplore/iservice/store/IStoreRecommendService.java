package com.cbsys.saleexplore.iservice.store;

import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.payload.pagi.QueryStorePd;

import java.util.List;

public interface IStoreRecommendService {
    /**
     * recommend the stores
     */
    List<Store> recommendStores(QueryStorePd requestPd, long userId);

}
