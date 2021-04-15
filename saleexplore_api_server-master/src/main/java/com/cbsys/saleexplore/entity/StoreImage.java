package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class StoreImage {

    @JsonIgnore
    private long id;

    @JsonIgnore
    // the store it belongs to
    private long storeId;

    // each discount may have several images, the order index control the order we display them
    private int orderIndex;

    // a brief title for this image, can be null
    private String title;

    // image name, cannot be null
    private String imageName;

}

