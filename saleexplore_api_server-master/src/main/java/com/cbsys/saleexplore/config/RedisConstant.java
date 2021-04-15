package com.cbsys.saleexplore.config;

public interface RedisConstant {
    String STORE_SEARCH_HISTORY = "storeSearch::";

    String CATEGORY_SEARCH_HISTORY = "categorySearch::";

    int SEARCH_EXPIRE_SECS = 12 * 60 * 60;

    /*
     * luc keyword search
     */
    String SEARCH_KW_DISCOUNT = "user:kwSearch:discount:";
    String SEARCH_KW_STORE = "user:kwSearch:store:";
    int SEARCH_KW_EXPIRE = 24 * 60 * 60; // 1 day in seconds


    /*
     * Recommendation constants
     */
    String RECO_DISCOUNT_CITY = "user:city:discount:reco:";
    String RECO_STORE_CITY = "user:city:store:reco:";

    int RECO_CACHE_STORE_EXPIRE = 24 * 60 * 60; // 1 day in seconds
    int RECO_CACHE_DISCOUNT_EXPIRE = 24 * 60 * 60; // 1 day in seconds
    int RECO_CACHE_STORE_CITY_PAGE = 200; // we retrieve 200 items in each caching db-retrieval
    int RECO_CACHE_DISCOUNT_CITY_PAGE = 200; // we retrieve 200 items in each caching db-retrieval


}
