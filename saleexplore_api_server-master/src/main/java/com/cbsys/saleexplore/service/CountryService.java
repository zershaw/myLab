package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.ICountryService;
import com.cbsys.saleexplore.entity.Country;
import com.cbsys.saleexplore.idao.ICountryDAO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountryService implements ICountryService, InitializingBean {

    @Autowired
    private ICountryDAO countryDAO;


    private List<Country> allCountries;
    private Map<Integer, Country> countryMap;


    @Override
    public List<Country> getAllCountries() {
        return allCountries;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        allCountries = countryDAO.get(null, null, null);;
        countryMap = new HashMap<>();

        for(Country country : allCountries){
            countryMap.put(country.getId(), country);
        }
    }
}
