package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class DiscountRating {

    @JsonIgnore
    private long userId;

    @ApiModelProperty(value = "like is 1, dislike is 0. 2 means the user cancle previous request",
            allowableValues = "0, 1, 2")
    @NotBlank
    private float score;

    @NotBlank
    private long discountId;

    @JsonIgnore
    private Timestamp createdTime;

}
