package com.cbsys.saleexplore.controller;

import com.cbsys.saleexplore.iservice.*;
import com.cbsys.saleexplore.iservice.discount.*;
import com.google.gson.Gson;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.entity.DiscountImage;
import com.cbsys.saleexplore.entity.DiscountRating;
import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.iservice.*;
import com.cbsys.saleexplore.iservice.discount.*;
import com.cbsys.saleexplore.iservice.micro.IImageTagService;
import com.cbsys.saleexplore.iservice.store.IStoreCategoryService;
import com.cbsys.saleexplore.iservice.store.IStoreService;
import com.cbsys.saleexplore.payload.ApiResponsePd;
import com.cbsys.saleexplore.payload.DiscountUploadPd;
import com.cbsys.saleexplore.payload.pagi.QueryDiscountPd;
import com.cbsys.saleexplore.payload.pagi.RecoDiscountResPd;
import com.cbsys.saleexplore.security.CurrentUser;
import com.cbsys.saleexplore.util.RandomStringUtil;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DiscountController {


    @Autowired
    private IDiscountService discountService;

    @Autowired
    private IDiscountImageService imageService;

    @Autowired
    private IDiscountSearchService discountSearchService;

    @Autowired
    private IDiscountRecommendService discountRecommendService;


    @Autowired
    private IStoreService storeService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IDiscountRatingService ratingService;

    @Autowired
    private IProfaneFilterService profaneFilterService;

    @Autowired
    private IImageTagService imageTagService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserSearchHistoryService userHisService;

    @Autowired
    private IUserInteretsService userInteService;

    @Autowired
    private IStoreCategoryService storeCategoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountController.class);


    /**
     * Get the details of a specific discount
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC
            + ConstantConfig.API_VCODE_ONE + "/discount")
    public Discount getDiscount(@RequestParam("id") final long id,
                                @AuthenticationPrincipal CurrentUser currentUser) {

        Discount discount = discountService.getDiscount(id);

        // if this is a logged in user, we load its rating data if there is any
        if (currentUser != null) {
            long userId = currentUser.getId();
            DiscountRating rating = ratingService.getRating(userId, id);
            discount.setUserRating(rating);

            // update the user's interest category
            userInteService.update(userId, discount.getCategoryId());
            discountService.insertDiscountSearchHistory(userId, Arrays.asList(discount));
        }

        // increase the number of views for this account
        discountService.increaseViewCount(id);


        // increase the view count
        discount.setViewCount(discount.getViewCount() + 1);

        // load some extra information for this request
        storeService.setDiscountDetailStore(discount);
        categoryService.setDiscountCategoryDetail(discount);
        imageService.addBaseImageUrl(discount, false);

        return discount;

    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succeed"),
            @ApiResponse(code = 1, message = "The title has illegal characters"),
            @ApiResponse(code = 2, message = "Your description has illegal characters"),
            @ApiResponse(code = 3, message = "Your picture has illegal content")
    })
    @PostMapping(value = ConstantConfig.URL_PREFIX_API
            + ConstantConfig.API_VCODE_ONE + "/discount/create")
    public ResponseEntity createDiscount(@RequestParam("files") MultipartFile[] uploadingFiles,
                                         @RequestParam("info") String discountJson,
                                         @AuthenticationPrincipal CurrentUser currentUser) {


        long userId = currentUser.getId();
        List<byte[]> imageContents = new ArrayList<>();

        // get image contents
        try {
            for (MultipartFile image : uploadingFiles) {
                imageContents.add(image.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // deserialize the json string to Discount
        DiscountUploadPd uploadPd = new Gson().fromJson(discountJson, DiscountUploadPd.class);
        Discount discount = uploadPd.createDiscount();

        // check for any profane characters
        if (profaneFilterService.containsProfaneText(uploadPd.getTitle())) {
            // the username has illegal characters
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }
        if (profaneFilterService.containsProfaneText(uploadPd.getInfoDescription())) {
            ApiResponsePd apiResponse = new ApiResponsePd(false, 2);
            return ResponseEntity.ok(apiResponse);
        }
        // check for profane images
        for (byte[] imageContent : imageContents) {
            if (profaneFilterService.containsProfaneImage(imageContent)) {
                ApiResponsePd apiResponse = new ApiResponsePd(false, 3);
                return ResponseEntity.ok(apiResponse);
            }
        }


        // get store info
        Store store = storeService.getStore(uploadPd.getStoreId());
        discount.setCreatorId(userId);

        // set category info
        if(discount.getCategoryId() == 0){
            List<Integer> storeCategories = storeCategoryService.getStoreCategoryIds(uploadPd.getStoreId());
            if(storeCategories != null && storeCategories.size() > 0){
                // we set the category as the stores if the discount category not set
                discount.setCategoryId(storeCategories.get(0));
            }
        }

        /////////////////////////////////////////////////////////
        // image has 64 characters
        /////////////////////////////////////////////////////////
        List<DiscountImage> discountImages = new ArrayList<>();
        RandomStringUtil randomStringUtil = new RandomStringUtil(64);

        for (int i = 0; i < imageContents.size(); i++) {

            // process each uploaded image
            DiscountImage discountImage = new DiscountImage();

            discountImage.setImageName(randomStringUtil.nextString() + ".jpeg");
            discountImage.setOrderIndex(i);

            discountImages.add(discountImage);

            // save the image to the image server
            try {
                String imageName = fileService.uploadImage(imageContents.get(i));
                discountImage.setImageName(imageName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        discount.setDiscountImages(discountImages);
        discountService.createDiscount(discount);

        // extract the tags and save them
        imageTagService.tagDiscountImage(discount.getId(), imageContents);

        // we need update the store's number of discounts
        storeService.updateNumberOfDiscount(discount.getStoreId());


        ApiResponsePd apiResponse = new ApiResponsePd(true, discount.getId());
        return ResponseEntity.ok(apiResponse);

    }


    /**
     * get the valid discounts of a store, not including the non-valid ones
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            + "/discounts")
    public List<Discount> getStoreValidDiscounts(@RequestParam("storeId") long storeId,
                                                 @RequestParam("limit") int limit,
                                                 @RequestParam("page") int page) {

        QueryDiscountPd qPd = new QueryDiscountPd();

        qPd.setStoreId(storeId);
        qPd.setMaxId(page * limit);
        qPd.setLimit(limit);

        // get the discounts of this store based on the request parameters
        List<Discount> discounts = discountService.getDiscounts(qPd);
        imageService.addBaseImageUrl(discounts, true);
        return discounts;

    }


    /**
     * search discounts
     *
     * @return results are returned in a page-limit pagination
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            + "/discounts/similarItems")
    public List<Discount> similarItems(@RequestParam("discountId") long discountId,
                                       @RequestParam("cityId") int cityId,
                                       @AuthenticationPrincipal CurrentUser currentUser) {

        Discount discount = discountService.getDiscount(discountId);
        if (discount == null) {
            LOGGER.warn("discountId {} not exist", discountId);
            return new ArrayList<>();
        }
        // get the user if logged-in
        long userId = 0;
        if (currentUser != null) {
            userId = currentUser.getId();
        }

        List<Discount> similarItems = discountSearchService.searchSimilarDiscounts(Arrays.asList(discount), userId, cityId);
        imageService.addBaseImageUrl(similarItems, true);
        return similarItems;
    }

    /**
     * search discounts
     *
     * @return results are returned in a page-limit pagination
     */
    @PostMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            + "/discounts/search")
    public RecoDiscountResPd searchDiscounts(@RequestBody QueryDiscountPd requestPd,
                                             @AuthenticationPrincipal CurrentUser currentUser) {

        // get the user if logged-in
        long userId = 0;
        if (currentUser != null && requestPd.getKwQuery() != null) {
            userId = currentUser.getId();
            userHisService.insertDiscountSearchHis(userId, requestPd.getKwQuery());
        }

        // get the discounts data
        List<Discount> discountsResults = discountSearchService.searchDiscounts(requestPd, userId);
        if (!CollectionUtils.isEmpty(discountsResults)) {
            discountService.insertDiscountSearchHistory(userId, discountsResults);
        }

        // here we add store info and category infor of those discounts to the response
        if (discountsResults == null) {
            // never return null to front-end
            discountsResults = new ArrayList<>();
        }


        categoryService.setDiscountCategoryDetail(discountsResults);
        storeService.setDiscountListStore(discountsResults, requestPd.getLatitude(), requestPd.getLongitude());
        imageService.addBaseImageUrl(discountsResults, true);

        // set the pagi response
        RecoDiscountResPd discountResponsePd = new RecoDiscountResPd(requestPd, discountsResults);

        return discountResponsePd;

    }


    /**
     * recommend discounts
     * https://www.sitepoint.com/paginating-real-time-data-cursor-based-pagination/
     */
    @PostMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC
            + ConstantConfig.API_VCODE_ONE + "/discounts/recommendation")
    public RecoDiscountResPd getRecommendDiscounts(@RequestBody QueryDiscountPd requestPd,
                                                   @AuthenticationPrincipal CurrentUser currentUser) {

        // get the user if logged-in
        long userId = 0;
        if (currentUser != null) {
            userId = currentUser.getId();

            /////////////////////////////////////////////
            // We update the user's last active time here
            /////////////////////////////////////////////
            if (requestPd.getLatitude() != 0 && requestPd.getLongitude() != 0 && requestPd.getMaxId() == 0) {
                userService.updateLastActive(userId,
                        requestPd.getLatitude(),
                        requestPd.getLongitude());
            }
        }

        // get the discounts data
        List<Discount> discountsResults = discountRecommendService.recommendDiscounts(requestPd, userId);

        if (discountsResults == null) {
            // never return null to front-end
            discountsResults = new ArrayList<>();
        }


        // here we add store info and category infor of those discounts to the response
        categoryService.setDiscountCategoryDetail(discountsResults);
        storeService.setDiscountListStore(discountsResults, requestPd.getLatitude(), requestPd.getLongitude());
        imageService.addBaseImageUrl(discountsResults, true);

        // set the pagi response
        RecoDiscountResPd discountResponsePd = new RecoDiscountResPd(requestPd, discountsResults);

        return discountResponsePd;

    }


}
