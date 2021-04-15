package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.entity.Ads;

import java.util.List;

public interface IAdsService {

    /**
     * get a list of banners for the home page
     */
    List<Ads> getHomeBannerAds();



    /**
     * get the splash ads
     */
    Ads getSplashAds();


}
