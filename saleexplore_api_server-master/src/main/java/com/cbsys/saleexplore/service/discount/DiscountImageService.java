package com.cbsys.saleexplore.service.discount;

import com.cbsys.saleexplore.iservice.discount.IDiscountImageService;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.entity.DiscountImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountImageService implements IDiscountImageService {

    public void addBaseImageUrl(List<Discount> discounts, boolean isThumb) {
        if(discounts == null){
            return;
        }
        for (Discount discount : discounts) {
            addBaseImageUrl(discount, isThumb);
        }
    }

    @Override
    public void addBaseImageUrl(Discount discount, boolean isThumb) {
        if(discount == null){
            return;
        }
        for (DiscountImage image : discount.getDiscountImages()) {
            if (!isThumb) {
                // a large resized image
                image.setImageName(ConstantConfig.IMAGE_SERVER_RESIZE_URL + image.getImageName());
            } else {
                // the thumbnail
                image.setImageName(ConstantConfig.IMAGE_SERVER_THUMB_URL + image.getImageName());
            }

        }
    }


}
