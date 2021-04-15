package com.cbsys.saleexplore.controller;


import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.Country;
import com.cbsys.saleexplore.iservice.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private ICountryService countryService;

    /**
     * Get the details of a specific store
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            +  "/countries")
    public List<Country> getCountries() {
        return countryService.getAllCountries();
    }


}
