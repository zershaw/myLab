package com.cbsys.saleexplore.iservice.store;

import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.payload.pagi.QueryStorePd;

import java.util.List;

public interface IStoreSearchService {
    /**
     * search the stores
     */
    List<Store> searchStores(QueryStorePd requestPd, long userId);


    /**
     * reset the lucene index for the discounts
     */
    void resetLuceneIndex();


}
