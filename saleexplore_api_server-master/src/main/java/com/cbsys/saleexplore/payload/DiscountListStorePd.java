package com.cbsys.saleexplore.payload;

import lombok.Data;

/**
 * a brief information of the store to be loaded with the discount lists
 */
@Data
public class DiscountListStorePd {

    private long id;
    private String name;
    private String mallName;
    private float distance;

}
