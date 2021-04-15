package com.cbsys.saleexplore.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Mall {

    private long id;
    private String name;
    private String geoAddress;
    private String openingHours;
    private String phoneNumber;
    private String email;
    private long cityId;
    private String webUrl;
    private boolean isOnline;


    /*
     * geo locations
     * check here for geo-implementation: https://www.jianshu.com/p/ab314e21fa0e
     */
    private float latitude;
    private float longitude;

    /*
     * non persistent info
     */
    private List<Store> stores;

}
