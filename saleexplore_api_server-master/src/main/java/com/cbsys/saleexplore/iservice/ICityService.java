package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.entity.City;
import com.cbsys.saleexplore.payload.CityDetectReqPd;

import java.util.List;

public interface ICityService {

    List<City> getActiveCities();

    List<City> getAllCities();

    City getCityById(int cityId);

    /**
     *
     */
    City detectCity(CityDetectReqPd reqPd);

}
