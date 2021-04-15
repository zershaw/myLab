package com.cbsys.saleexplore.payload;

import com.cbsys.saleexplore.enums.DiscountTypeEm;
import lombok.Data;

@Data
public class DiscountUpdatePd {

    private DiscountTypeEm discountType;
    private Long startTime;
    private Long endTime;
    private String title;
    private String infoDescription;
    private Integer storeId;
    private Integer categoryId;
    private String discountTags;

    public DiscountUpdatePd() {
        discountType = null;
        startTime = null;
        endTime = null;
        title = null;
        infoDescription = null;
        storeId = null;
        categoryId = null;
        discountTags = null;
    }
}
