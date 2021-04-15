package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DiscountImage {

    @JsonIgnore
    private long id;

    @JsonIgnore
    // the foreign key points to the id of discount
    private long discountId;

    // each discount may have several images, the order index control the order we display them
    private int orderIndex;

    // a brief title for this image, can be null
    private String title;

    // image name, cannot be null
    private String imageName;
}
