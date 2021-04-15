package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.ICityService;
import com.cbsys.saleexplore.entity.City;
import com.cbsys.saleexplore.idao.ICityDAO;
import com.cbsys.saleexplore.payload.CityDetectReqPd;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CityService implements ICityService, InitializingBean {

    @Autowired
    private ICityDAO cityDAO;

    private List<City> allCities;
    private List<City> activeCities;
    private Map<Integer, City> cityMap;

    @Override
    public void afterPropertiesSet() {
        allCities = cityDAO.get(null, null, null, true);

        activeCities = new ArrayList<>();
        cityMap = new HashMap<>();

        for(City city : allCities){
            if(city.isActive()){
                activeCities.add(city);
            }
            cityMap.put(city.getId(), city);
        }
    }

    @Override
    public List<City> getActiveCities() {
        return activeCities;
    }

    @Override
    public List<City> getAllCities() {
        return allCities;
    }

    @Override
    public City getCityById(int cityId) {
        City city = cityMap.get(cityId);
        return city;
    }

    @Override
    public City detectCity(CityDetectReqPd reqPd) {
        // Find the closest city
        City detCity = null;
        double minDis = Double.MAX_VALUE;
        if(reqPd.getLatitude() != null && reqPd.getLongitude() != null){
            for(City city : activeCities){
                double dis = Math.pow(city.getLatitude() - reqPd.getLatitude(), 2)
                        + Math.pow(city.getLongitude() - reqPd.getLongitude(), 2);
                if (dis < minDis) {
                    minDis = dis;
                    detCity = city;
                }
            }
        }
        return detCity;
    }

}
