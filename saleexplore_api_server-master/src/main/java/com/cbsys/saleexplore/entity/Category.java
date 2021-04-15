package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Category {

    @JsonIgnore
    private int id;
    @JsonIgnore
    private int parentId;

    private String name;

    @JsonIgnore
    private String synonyms;

}
