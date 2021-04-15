package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class StoreRating {

    @JsonIgnore
    private long userId;

    @NotBlank
    @Min(value = 0, message = "It can't be less than 0")
    @Max(value = 5, message = "It can't be more than 5")
    @ApiModelProperty(value = "a score value from 0 to 5")
    private float score;

    @NotBlank
    private long storeId;

    @JsonIgnore
    private Timestamp createdTime;

}
