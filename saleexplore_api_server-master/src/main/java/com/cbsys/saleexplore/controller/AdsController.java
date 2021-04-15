package com.cbsys.saleexplore.controller;


import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.Ads;
import com.cbsys.saleexplore.iservice.IAdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;


@RestController
public class AdsController {

    @Autowired
    private IAdsService bannerService;

    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC
            + ConstantConfig.API_VCODE_ONE + "/adsBanner")
    public List<Ads> adsBanner(){

        List<Ads> adsBanners = bannerService.getHomeBannerAds();

        return adsBanners;

    }

    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC
            + ConstantConfig.API_VCODE_ONE + "/adsSplash")
    public Ads splashAds(){
        // TODO advertisement system
        Random rand = new Random();
        int n = rand.nextInt(2);
        if(n == -1){
            // NOW NEVER REACH THIS LINE
            Ads splashAds = bannerService.getSplashAds();
            return splashAds;
        }else{
            return null;
        }

    }

}
