package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.enums.UserSearchHisTypeEm;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface IUserSearchHistoryService {

    void insertDiscountSearchHis(long userId, String kwQuery);

    /**
     * get number of [limit] of most recent store searching history
     */
    List<String> getDiscountSearchHis(long userId, int topk);


    /**
     * clean users discount search his
     */
    void cleanSearchHis(long userId, UserSearchHisTypeEm searchType);


    void insertStoreSearchHis(long userId, String kwQuery);


    /**
     * get number of [limit] of most recent store searching history
     */
    List<String> getStoreSearchHis(long userId, int topk);



}
