package com.cbsys.saleexplore.controller;

import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.entity.StoreRating;
import com.cbsys.saleexplore.exception.BadRequestEn;
import com.cbsys.saleexplore.iservice.IUserInteretsService;
import com.cbsys.saleexplore.iservice.IUserSearchHistoryService;
import com.cbsys.saleexplore.iservice.store.*;
import com.cbsys.saleexplore.iservice.*;
import com.cbsys.saleexplore.iservice.store.*;
import com.cbsys.saleexplore.payload.pagi.QueryStorePd;
import com.cbsys.saleexplore.payload.pagi.RecoStoreResPd;
import com.cbsys.saleexplore.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private IStoreService storeService;

    @Autowired
    private IStoreRatingService storeRatingService;


    @Autowired
    private IStoreCategoryService categoryService;

    @Autowired
    private IStoreImageService imageService;

    @Autowired
    private IStoreRecommendService recoSearchService;


    @Autowired
    private IStoreSearchService searchSearchService;

    @Autowired
    private IUserSearchHistoryService userHisService;


    @Autowired
    private IUserInteretsService userInteService;

    /**
     * Get the details of a specific store
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            + "/store")
    public Store getStore(@RequestParam("id") final long id,
                          @AuthenticationPrincipal CurrentUser currentUser) {

        Store store = storeService.getStore(id);
        storeService.setOpeningHours(store);

        // if this is a logged in user, we load its rating data if there is any
        if (currentUser != null) {
            long userId = currentUser.getId();
            // update the user's interest
            List<Integer> categories = categoryService.getStoreCategoryIds(id);
            if(categories != null){
                userInteService.update(userId, categories);
            }

            storeService.insertStoreSearchHistory(userId, Arrays.asList(store));
        }

        if (store != null) {
            imageService.addBaseImageUrl(store, false);

            if (currentUser != null) {
                // this is a logged in user, we try to get his rating of this store
                StoreRating userStoreRating = storeRatingService.getRating(currentUser.getId(), id);
                if (userStoreRating != null) {
                    store.setUserRatingScore(userStoreRating.getScore());
                }
            }

            return store;
        } else {
            throw new BadRequestEn("Cannot find specific item");
        }
    }

    /**
     * Searching stores api
     * kwQuery  can be used for keyword search of those discounts of this store. it can be null
     */
    @PostMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            + "/stores/search")
    public RecoStoreResPd searchStores(@RequestBody QueryStorePd requestPd,
                                       @AuthenticationPrincipal CurrentUser currentUser) {

        // get the user if logged-in
        long userId = 0;
        if (currentUser != null && requestPd.getKwQuery() != null) {
            userId = currentUser.getId();
            userHisService.insertStoreSearchHis(userId, requestPd.getKwQuery());
        }

        List<Store> storeResults = searchSearchService.searchStores(requestPd, userId);
        if (!CollectionUtils.isEmpty(storeResults)) {
            storeService.insertStoreSearchHistory(userId, storeResults);
        }
        if (storeResults == null) {
            // never return null to front-end
            storeResults = new ArrayList<Store>();
        }

        // for the stores, we set the number of discounts it has
        imageService.addBaseImageUrl(storeResults, true);
        storeService.setOpeningHours(storeResults);

        RecoStoreResPd storeResponsePd = new RecoStoreResPd(requestPd, storeResults);

        return storeResponsePd;

    }


    /**
     * Get the recommendation stores
     */
    @PostMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            + "/stores/recommendation")
    public RecoStoreResPd showStores(@RequestBody QueryStorePd requestPd,
                                     @AuthenticationPrincipal CurrentUser currentUser) {


        // get the stores based on pagination request
        long userId = 0;
        if (currentUser != null) {
            userId = currentUser.getId();
        }
        List<Store> storeResults = recoSearchService.recommendStores(requestPd, userId);
        if (storeResults == null) {
            // never return null to front-end
            storeResults = new ArrayList<Store>();
        }

        // for the stores, we set the number of discounts it has
        imageService.addBaseImageUrl(storeResults, true);
        storeService.setOpeningHours(storeResults);

        RecoStoreResPd storeResponsePd = new RecoStoreResPd(requestPd, storeResults);

        return storeResponsePd;
    }


}
