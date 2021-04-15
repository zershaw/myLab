package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Data
public class Store {

    private long id;
    private String name;
    private String webUrl;
    private String location;
    private String infoDescription;
    private String phoneNumber;
    private String openingHours;
    private String email;

    private long mallId;
    private int cityId;

    /*
     * whether this shop has claimed by any owner
     */
    private boolean isVerified;
    private boolean isDeleted;

    /*
     * store the popularity of this
     */
    private float popularity;

    private int numberOfDiscounts;

    @JsonIgnore
    private Timestamp lastUpdatedTime;

    /*
     * geo locations
     * check here for geo-implementation: https://www.jianshu.com/p/ab314e21fa0e
     */
    private float latitude;
    private float longitude;

    /*
     * non-persistent data
     */
    List<StoreImage> storeImages = null;
    List<Category> categories = null;
    private String cityName = null;
    private String mallName = null;
    private float userRatingScore = 0;
    private int isOpenNow = 2; // whether this store is open or not, 0 means closed, 1 means open 2 means not know yet
    private float distance = 0; // in km(s)

}
