package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IPopularSearchService;
import com.cbsys.saleexplore.enums.PopularSearchTypeEm;
import com.cbsys.saleexplore.idao.IPopularSearchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopularSearchService implements IPopularSearchService {


    @Autowired
    private IPopularSearchDAO popularSearchDAO;


    @Override
    public List<String> getPopularSearch(PopularSearchTypeEm searchType, int topk) {
        return popularSearchDAO.getPopularSearch(searchType, topk);
    }
}
