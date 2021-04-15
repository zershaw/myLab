package com.cbsys.saleexplore.payload;


import com.cbsys.saleexplore.util.DateTimeUtil;
import lombok.Data;
import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.enums.DiscountTypeEm;
import org.joda.time.DateTime;
import org.joda.time.tz.CachedDateTimeZone;

import java.sql.Timestamp;
import java.time.Instant;

@Data
public class DiscountUploadPd {

    private String startTime;
    private String endTime;
    private String title;          // JSON
    private String infoDescription;

    private long storeId;
    private int categoryId;

    private String relatedLinks;    // JSON

    private float originalPrice;
    private float finalPrice;
    private int discountType;
    private String discountTag;

    public Discount createDiscount(){
        Discount discount = new Discount();

        if(this.startTime != null){
            discount.setStartTime(Timestamp.from(Instant.parse(this.startTime)));
        }else{
            // if the user does not specify the starting time, we set the starting time as now
            discount.setStartTime(new Timestamp(DateTimeUtil.getUTCTime().getTime()));
        }

        if(this.endTime != null){
            discount.setEndTime(Timestamp.from(Instant.parse(this.endTime)));
        }else{
            // If the endTime is NULL, we set it expire within 2 weeks
            discount.setEndTime(new Timestamp(discount.getStartTime().getTime() + 14 * 86400));
        }

        discount.setTitle(this.title);
        discount.setInfoDescription(this.infoDescription);

        discount.setStoreId(this.storeId);
        discount.setCategoryId(this.categoryId);
        discount.setOriginalPrice(this.originalPrice);

        discount.setSavingAmount(this.originalPrice - this.finalPrice);

        discount.setDiscountType(DiscountTypeEm.valueOf(this.discountType));
        discount.setDiscountTag(this.discountTag);

        discount.setOnline(false);
        discount.setValid(true);

        Timestamp timeNowUTC = new Timestamp(DateTimeUtil.getUTCTime().getTime());
        discount.setPublishTime(timeNowUTC);

        return discount;
    }

}
