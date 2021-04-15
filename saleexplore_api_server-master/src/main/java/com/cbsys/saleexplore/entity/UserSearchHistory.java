package com.cbsys.saleexplore.entity;

import com.cbsys.saleexplore.enums.UserSearchHisTypeEm;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserSearchHistory {
    private long id;
    private long userId;
    private String kwQuery;
    private Timestamp createdTime;
    private UserSearchHisTypeEm searchType; // check for
}
