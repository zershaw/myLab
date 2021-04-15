package com.cbsys.saleexplore.controller;


import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.City;
import com.cbsys.saleexplore.iservice.ICityService;
import com.cbsys.saleexplore.payload.CityDetectReqPd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {

    @Autowired
    private ICityService cityService;

    /**
     * Get all the active cities
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            +  "/city/activeCities")
    public List<City> getActiveCities() {
        return cityService.getActiveCities();
    }


    /**
     * Given the user's latitude and longitude, detect the users located city
     */
    @PostMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            +  "/city/detectCity")
    public City detectCity(@RequestBody CityDetectReqPd reqPd) {
        City detCity = cityService.detectCity(reqPd);

        return detCity;
    }


}
