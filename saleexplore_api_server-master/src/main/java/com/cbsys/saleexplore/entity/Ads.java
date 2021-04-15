package com.cbsys.saleexplore.entity;

import com.cbsys.saleexplore.enums.AdsRedirectTypeEm;
import com.cbsys.saleexplore.enums.AdsDisplayTypeEm;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Ads {

    private long id;

    // if this is a ads for a external web site
    private String redirectUrl;

    // which type is this banner
    private AdsRedirectTypeEm redirectType;

    // the banner image
    private String imageName;

    // whether this banner is still active or not
    private boolean isActive;

    // decides the display order
    private int orderIndex;

    private Timestamp createdTime;

    // where and how to display this ads
    private AdsDisplayTypeEm displayType;

}
