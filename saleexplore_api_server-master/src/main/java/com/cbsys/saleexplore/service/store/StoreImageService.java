package com.cbsys.saleexplore.service.store;

import com.cbsys.saleexplore.iservice.store.IStoreImageService;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.entity.StoreImage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreImageService implements IStoreImageService {

    private  List<StoreImage> defaultStoreImages;

    public StoreImageService(){
        // create a default store image holder
        StoreImage defaultImage = new StoreImage();
        defaultImage.setImageName(ConstantConfig.IMAGE_DEFAULT_STORE_PROFILE);

        defaultStoreImages = new ArrayList<>();
        defaultStoreImages.add(defaultImage);
    }

    /**
     * @param stores a list of stores contains no image information
     *               we only save the image name in our file system
     *               here we add the image urls. handle a list of stores
     */
    @Override
    public void addBaseImageUrl(List<Store> stores, boolean isThumb) {

        if (stores == null) {
            return;
        }

        for (Store store : stores) {
            addBaseImageUrl(store, isThumb);
        }
    }

    @Override
    public void addBaseImageUrl(Store store, boolean isThumb) {

        // in case this store does not have images
        if (store.getStoreImages().size() == 0) {
            defaultStoreImages.get(0).setStoreId(store.getId());
            store.setStoreImages(defaultStoreImages);

        } else {
            // this store has images
            for (StoreImage image : store.getStoreImages()) {
                if (image.getImageName() != null) {
                    // a large resized image
                    if (!isThumb) {
                        image.setImageName(ConstantConfig.IMAGE_SERVER_RESIZE_URL + image.getImageName());
                    } else {
                        // thumb
                        image.setImageName(ConstantConfig.IMAGE_SERVER_THUMB_URL + image.getImageName());
                    }

                } else {
                    image.setImageName(ConstantConfig.IMAGE_DEFAULT_STORE_PROFILE);
                }
            }
        }
    }


}
