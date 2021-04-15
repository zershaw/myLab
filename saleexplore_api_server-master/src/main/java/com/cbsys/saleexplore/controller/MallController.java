package com.cbsys.saleexplore.controller;

import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.Mall;
import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.iservice.IMallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class MallController {

    @Autowired
    private IMallService mallService;

    class SortbyName implements Comparator<Store>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Store a, Store b)
        {
            return a.getName().compareTo(b.getName());
        }
    }

    /**
     * Get the list of malls within the city. not using the pagination yet
     * @return the mall data also has the stores of those malls.
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            +  "/malls")
    public List<Mall> getMalls(@RequestParam("cityId") final long cityId) {

        List<Mall> malls = mallService.getMallWithStoreByCity(cityId);
        for(Mall mall : malls){
            //sort the stores by name
            Collections.sort(mall.getStores(), new SortbyName());
        }
        return malls;
    }

}
