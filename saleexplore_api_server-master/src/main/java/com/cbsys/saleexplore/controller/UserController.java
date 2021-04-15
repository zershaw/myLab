package com.cbsys.saleexplore.controller;

import com.cbsys.saleexplore.entity.City;
import com.cbsys.saleexplore.entity.User;
import com.cbsys.saleexplore.iservice.*;
import com.cbsys.saleexplore.iservice.*;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.payload.ApiResponsePd;
import com.cbsys.saleexplore.payload.UserUpdatePd;
import com.cbsys.saleexplore.security.CurrentUser;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = ConstantConfig.URL_PREFIX_API + ConstantConfig.API_VCODE_ONE + "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICityService cityService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IEmailAccountVeriService emailVService;

    @Autowired
    private IProfaneFilterService profaneFilterService;

    @GetMapping("/getUser")
    public User getCurrentUser(@AuthenticationPrincipal CurrentUser currentUser) {

        User user = userService.getById(currentUser.getId());

        if (user != null) {
            setUserExtra(user);
        }

        return user;
    }

    @GetMapping("/deleteUser")
    public ResponseEntity deleteUser(@AuthenticationPrincipal CurrentUser currentUser) {

        ApiResponsePd apiResponse = null;

        try {
            User user = userService.getById(currentUser.getId());

            // delete the image from our server
            if (user.getImageName() != null && user.getImageName().length() > 0) {
                fileService.deleteImage(user.getImageName());
            }

            // delete the user
            int affectedRows = userService.deleteUser(user);

            if (affectedRows == 1) {
                apiResponse = new ApiResponsePd(true, 0);
                return ResponseEntity.ok(apiResponse);
            }

            // send a farewell email to the user
            userService.sendDeleteEmail(user);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        apiResponse = new ApiResponsePd(false, 2);
        return ResponseEntity.ok(apiResponse);
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succeed"),
            @ApiResponse(code = 1, message = "Your username has illegal characters"),
            @ApiResponse(code = 2, message = "Server error")
    })
    @PostMapping(value = "/updateUser")
    public ResponseEntity updateUser(@AuthenticationPrincipal CurrentUser currentUser,
                                     @Valid @RequestBody UserUpdatePd userPd) {

        ApiResponsePd apiResponse = null;

        try {
            if(profaneFilterService.containsProfaneText(userPd.getUsername())){
                // the username has illegal characters
                apiResponse = new ApiResponsePd(false, 1);
                return ResponseEntity.ok(apiResponse);
            }

            int affectedRows = userService.updateUser(currentUser.getId(), userPd);

            User user = userService.getById(currentUser.getId());
            setUserExtra(user);

            if (affectedRows == 1) {
                apiResponse = new ApiResponsePd(true, user);
                return ResponseEntity.ok(apiResponse);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        apiResponse = new ApiResponsePd(false, 2);
        return ResponseEntity.ok(apiResponse);
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succeed"),
            @ApiResponse(code = 1, message = "No images attached")
    })
    @PostMapping(value = "/updateImage")
    public ResponseEntity updateUserImage(@AuthenticationPrincipal CurrentUser currentUser,
                                          @RequestParam("images") MultipartFile[] profileImages) {

        List<byte[]> imageContents = new ArrayList<>();

        // get image contents
        try {
            for (MultipartFile image : profileImages) {
                imageContents.add(image.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        ApiResponsePd apiResponse = null;

        if (profileImages.length < 1) {
            // no image uploaded
            apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }
        try {
            User user = userService.getById(currentUser.getId());
            String olderImageName = user.getImageName();

            /*
             * for now we only take care of one com.cbsys.saleexplore.profile image
             */
            String imageName = fileService.uploadImage(imageContents.get(0));

            int affectedRows = userService.updateProfileImage(imageName, currentUser.getId());
            if (affectedRows == 1) {
                // delete the image from our server
                if (olderImageName != null && olderImageName.length() > 0) {
                    fileService.deleteImage(olderImageName);
                }

                apiResponse = new ApiResponsePd(true, ConstantConfig.IMAGE_SERVER_RESIZE_URL
                        + imageName);
                return ResponseEntity.ok(apiResponse);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        apiResponse = new ApiResponsePd(false, 2);
        return ResponseEntity.ok(apiResponse);
    }


    private void setUserExtra(User user) {
        City city = cityService.getCityById(user.getCityId());

        user.setCity(city);
        if (user.getImageName() == null) {
            user.setImageName(ConstantConfig.IMAGE_DEFAULT_USER_PROFILE);
        } else {
            user.setImageName(ConstantConfig.IMAGE_SERVER_RESIZE_URL + user.getImageName());
        }
    }


    /**
     * receive the verification link
     */
    @GetMapping("/verifyEmail")
    public void verifyEmail(@AuthenticationPrincipal CurrentUser currentUser) {

        User user = userService.getById(currentUser.getId());
        if (user != null) {
            // send a new verification link
            emailVService.sendEmailVeryLink(user);
        }
    }
}