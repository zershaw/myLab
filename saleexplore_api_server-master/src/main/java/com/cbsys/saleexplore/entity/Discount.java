package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cbsys.saleexplore.enums.DiscountTypeEm;
import lombok.Data;

import java.sql.Timestamp;

import java.util.List;
@Data
public class Discount {

    private long id;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp publishTime;

    @JsonIgnore
    private Timestamp lastUpdatedTime;

    private String title;          // JSON
    private int viewCount;
    private int likeCount;
    private int dislikeCount;
    private String infoDescription;

    private long storeId;
    private long creatorId;
    private int creatorRole; // 0 means the mobile users and 1 means the merchant or admin
    private int categoryId;

    private String relatedLinks;    // JSON
    private String videoName;

    // the isOnline flag should be consist with its belonged store. we have it in the discount table
    // for query efficiency
    private boolean isOnline;
    private boolean isValid;
    private boolean isDeleted;

    private float originalPrice;
    private float finalPrice;
    private float savingAmount;
    private DiscountTypeEm discountType;
    private String discountTag;


    /**********************************************
     * non persistent info of discount table *
     **********************************************/
    private List<DiscountImage> discountImages;
    private Object store = null;
    private String categoryName = null;
    // place holder for user rating of this discount for frontend purpose
    private DiscountRating userRating = null;




}
