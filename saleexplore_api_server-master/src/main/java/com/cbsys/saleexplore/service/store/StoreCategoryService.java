package com.cbsys.saleexplore.service.store;

import com.cbsys.saleexplore.iservice.store.IStoreCategoryService;
import com.cbsys.saleexplore.idao.IStoreCategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreCategoryService implements IStoreCategoryService {

    @Autowired
    private IStoreCategoryDAO categoryDAO;

    @Override
    public List<Integer> getStoreCategoryIds(long storeId) {
        return categoryDAO.getStoreCategoryIds(storeId);
    }
}
