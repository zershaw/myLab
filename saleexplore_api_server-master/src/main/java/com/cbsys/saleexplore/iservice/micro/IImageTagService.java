package com.cbsys.saleexplore.iservice.micro;

import java.util.List;

public interface IImageTagService {

    List<String> getImageTags(byte[] imageContent);

    /**
     * generate the tags for the discount images of this discount
     */
    void tagDiscountImage(long discountId, List<byte[]> imageContents);


}
