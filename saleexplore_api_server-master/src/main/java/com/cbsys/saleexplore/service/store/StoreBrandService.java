package com.cbsys.saleexplore.service.store;

import com.cbsys.saleexplore.iservice.store.IStoreBrandService;
import com.cbsys.saleexplore.idao.IStoreBrandDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreBrandService implements IStoreBrandService {

    @Autowired
    private IStoreBrandDAO brandDAO;

    @Override
    public List<Integer> getStoreBrandIds(long storeId) {
        return brandDAO.getStoreBrandIds(storeId);
    }
}
