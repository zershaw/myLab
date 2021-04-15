package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IAdsService;
import com.cbsys.saleexplore.entity.Ads;
import com.cbsys.saleexplore.enums.AdsDisplayTypeEm;
import com.cbsys.saleexplore.idao.IAdsDAO;
import com.cbsys.saleexplore.config.ConstantConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsService implements IAdsService {


    @Autowired
    private IAdsDAO adsBannerDAO;

    @Override
    public List<Ads> getHomeBannerAds() {
        // TODO now manually get 4 banners
        List<Ads> adsBanners = adsBannerDAO.get(null, null, null,
                AdsDisplayTypeEm.BANNER, 4);
        for(Ads banner : adsBanners){
            banner.setImageName(ConstantConfig.IMAGE_SERVER_RESIZE_URL + banner.getImageName());
        }

        return adsBanners;
    }

    @Override
    public Ads getSplashAds() {
        // TODO now manually get 1 ads
        List<Ads> adsBanners = adsBannerDAO.get(null, null, null,
                AdsDisplayTypeEm.SPLASH, 1);


        if(adsBanners.size() >= 1){
            for(Ads banner : adsBanners){
                banner.setImageName(ConstantConfig.IMAGE_SERVER_RESIZE_URL + banner.getImageName());
            }
            return adsBanners.get(0);
        }

        return null;
    }
}
