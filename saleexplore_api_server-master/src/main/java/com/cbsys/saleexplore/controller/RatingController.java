package com.cbsys.saleexplore.controller;


import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.DiscountRating;
import com.cbsys.saleexplore.entity.StoreRating;
import com.cbsys.saleexplore.enums.DiscountRatingEnum;
import com.cbsys.saleexplore.iservice.discount.IDiscountRatingService;
import com.cbsys.saleexplore.iservice.store.IStoreRatingService;
import com.cbsys.saleexplore.payload.DiscountRatingCountPd;
import com.cbsys.saleexplore.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {

    @Autowired
    private IStoreRatingService storeRatingService;

    @Autowired
    private IDiscountRatingService discountRatingService;


    /**
     * Given the user's latitude and longitude, detect the users located city
     */
    @PostMapping(value = ConstantConfig.URL_PREFIX_API + ConstantConfig.API_VCODE_ONE
            + "/rating/store")
    public void userRateStore(@RequestBody StoreRating reqPd,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        reqPd.setUserId(currentUser.getId());
        storeRatingService.userRate(reqPd);

        // update the popularity which is the average of the score
        storeRatingService.updateStorePopularity(reqPd.getStoreId());

    }


    @PostMapping(value = ConstantConfig.URL_PREFIX_API + ConstantConfig.API_VCODE_ONE
            + "/rating/discount")
    public DiscountRatingCountPd userRateDiscount(@RequestBody DiscountRating reqPd,
                                                  @AuthenticationPrincipal CurrentUser currentUser) {
        reqPd.setUserId(currentUser.getId());

        DiscountRatingCountPd currentCounts = discountRatingService.getDiscountRatingCount(reqPd.getDiscountId());
        DiscountRating currentRating = discountRatingService.getRating(reqPd.getUserId(), reqPd.getDiscountId());

        /*
         * We first persist the rating to the database
         */
        if(currentRating == null){
            // not allow cancel
            if (reqPd.getScore() == DiscountRatingEnum.LIKE.getCode()) {
                // the user like the discount
                currentCounts.setLikeCount(currentCounts.getLikeCount() + 1);

            } else if (reqPd.getScore() == DiscountRatingEnum.DISLIKE.getCode()) {
                // the user dislike the discount
                currentCounts.setDislikeCount(currentCounts.getDislikeCount() + 1);
            }
        }
        else{
            if(currentRating.getScore() == reqPd.getScore()){
                return currentCounts;
            }
            if (reqPd.getScore() == DiscountRatingEnum.CANCLE.getCode()) {
                // the user cancle his previous rating score
                discountRatingService.deleteRating(reqPd.getUserId(), reqPd.getDiscountId());

                // the user cancle his previous rating
                if(currentRating.getScore() == 1){
                    currentCounts.setLikeCount(currentCounts.getLikeCount() - 1);
                }else{
                    currentCounts.setDislikeCount(currentCounts.getDislikeCount() - 1);
                }
            }
            else if (reqPd.getScore() == DiscountRatingEnum.LIKE.getCode()) {
                // the user like the discount
                currentCounts.setLikeCount(currentCounts.getLikeCount() + 1);

                if(currentRating != null && currentRating.getScore() == DiscountRatingEnum.DISLIKE.getCode()){
                    currentCounts.setDislikeCount(currentCounts.getDislikeCount() - 1);
                }
            } else if (reqPd.getScore() == DiscountRatingEnum.DISLIKE.getCode()) {
                // the user dislike the discount
                currentCounts.setDislikeCount(currentCounts.getDislikeCount() + 1);

                if(currentRating != null && currentRating.getScore() == DiscountRatingEnum.LIKE.getCode()){
                    currentCounts.setLikeCount(currentCounts.getLikeCount() - 1);
                }
            }

        }
        discountRatingService.userRate(reqPd);

        // update the user discount rating
        discountRatingService.updateDiscountRatingCount(reqPd.getDiscountId(),
                currentCounts);

        // we return the updated like and dislike counts
        return currentCounts;

    }


}
