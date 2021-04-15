package com.cbsys.saleexplore.iservice.discount;

import com.cbsys.saleexplore.entity.Discount;

import java.util.List;

public interface IDiscountImageService {
    /**
     * add the base URL to the baseImage
     * isThumb to indicate to load thumbnail of resize image
     */
    void addBaseImageUrl(List<Discount> discounts, boolean isThumb);

    /**
     * add the base URL to the baseImage
     * isThumb to indicate to load thumbnail of resize image
     */
    void addBaseImageUrl(Discount discount, boolean isThumb);
}
