package com.cbsys.saleexplore.payload;

import lombok.Data;

/**
 * a brief information of the store to be loaded with the discount details
 */
@Data
public class DiscountDetailStorePd {

    private long id;
    private String name;
    private String mallName;

    private String phoneNumber;
    private String email;

    private String cityName = null;
    private int cityId;

    private float latitude;
    private float longitude;


}
