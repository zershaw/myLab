package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class StoreBrand {

    @JsonIgnore
    private long id;

    @JsonIgnore
    private long storeId;

    @JsonIgnore
    private int brandId;

}
