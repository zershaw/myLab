package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.enums.PopularSearchTypeEm;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface IPopularSearchService {

    /**
     * get number of [limit] of most popular search
     */
    List<String> getPopularSearch(PopularSearchTypeEm searchType, int topke);


}
