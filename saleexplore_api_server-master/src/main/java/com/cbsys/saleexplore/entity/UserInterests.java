package com.cbsys.saleexplore.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInterests {

    private long userId;

    private int categoryId;

    private int score;

    private Timestamp createdTime;

    public UserInterests(long userId, int categoryId){
        this.userId = userId;
        this.categoryId = categoryId;
    }

}
