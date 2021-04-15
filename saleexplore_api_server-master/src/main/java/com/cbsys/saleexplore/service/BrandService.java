package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IBrandService;
import com.cbsys.saleexplore.entity.Brand;
import com.cbsys.saleexplore.idao.IBrandDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService implements IBrandService {


    @Autowired
    private IBrandDAO brandDAO;

    @Override
    public List<Brand> getAllBrands() {
        return brandDAO.get(null);
    }

    @Override
    public List<Brand> getBrands(List<Integer> ids) {
        return brandDAO.get(ids);
    }
}
