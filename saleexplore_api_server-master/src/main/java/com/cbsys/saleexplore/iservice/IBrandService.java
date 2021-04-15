package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.entity.Brand;

import java.util.List;

public interface IBrandService {

    List<Brand> getAllBrands();

    List<Brand> getBrands(List<Integer> ids);

}
