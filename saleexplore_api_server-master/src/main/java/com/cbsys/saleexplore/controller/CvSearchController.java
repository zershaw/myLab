package com.cbsys.saleexplore.controller;
/**
 * The controller to handle the searching based on computer vision(images)
 */

import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.iservice.micro.IImageTagService;
import com.cbsys.saleexplore.payload.ApiResponsePd;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CvSearchController {

    @Autowired
    private IImageTagService imageTagService;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succeed"),
            @ApiResponse(code = 1, message = "No images uploaded")
    })
    @PostMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            + "/cvSearch/stores")
    public ResponseEntity cvSearchStores(@RequestParam("images") MultipartFile[] uploadingFiles) {

        ApiResponsePd apiResponse = null;
        // TODO
        apiResponse = new ApiResponsePd(true, "Not yet implemented");
        return ResponseEntity.ok(apiResponse);
    }



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succeed"),
            @ApiResponse(code = 1, message = "No images uploaded")
    })
    @PostMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE
            + "/cvSearch/discounts")
    public ResponseEntity cvSearchDiscounts(@RequestParam("images") MultipartFile[] uploadingFiles) {

        List<byte[]> imageContents = new ArrayList<>();

        // get image contents
        try {
            for (MultipartFile image : uploadingFiles) {
                imageContents.add(image.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ApiResponsePd apiResponse = null;

        if (uploadingFiles.length < 1) {
            // no image uploaded
            apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }

        /*
         * get the discount keywords from the uploaded images
         */
        List<String> discountKeywords = new ArrayList<>();
        for(byte[] imageContent : imageContents){
            discountKeywords.addAll(imageTagService.getImageTags(imageContent));
        }

        // return the keywords
        apiResponse = new ApiResponsePd(true, discountKeywords.get(0));
        return ResponseEntity.ok(apiResponse);

    }

}
