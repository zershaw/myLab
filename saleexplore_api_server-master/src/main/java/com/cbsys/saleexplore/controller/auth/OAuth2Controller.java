package com.cbsys.saleexplore.controller.auth;


import com.cbsys.saleexplore.entity.User;
import com.cbsys.saleexplore.enums.AuthProviderEm;
import com.cbsys.saleexplore.iservice.IAuthService;
import com.cbsys.saleexplore.iservice.IFileService;
import com.cbsys.saleexplore.iservice.IUserService;
import com.cbsys.saleexplore.payload.ApiResponsePd;
import com.cbsys.saleexplore.payload.OAuthRequestPd;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.security.JWTTokenProvider;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URL;

@RestController
@RequestMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE + "/auth")
public class OAuth2Controller {

    @Autowired
    private IAuthService authService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;


    @PostMapping("/loginByWechat")
    public ResponseEntity<?> loginByWechat(@Valid @RequestBody OAuthRequestPd oauthRequest) {

        if (!authService.validateWechatToken(oauthRequest)) {
            // validation failed, not exists
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }

        return loginOAuth(oauthRequest, AuthProviderEm.wechat);

    }


    @PostMapping("/loginByGoogle")
    public ResponseEntity<?> loginByGoogle(@Valid @RequestBody OAuthRequestPd oauthRequest) {

        if (!authService.validateGoogleToken(oauthRequest)) {
            // validation failed, not exists
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }


        return loginOAuth(oauthRequest, AuthProviderEm.google);
    }


    @PostMapping("/loginByFacebook")
    @CrossOrigin
    public ResponseEntity<?> loginByFacebook(@Valid @RequestBody OAuthRequestPd oauthRequest) {

        if (!authService.validateFacebookToken(oauthRequest)) {
            // validation failed, not exists
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }

        return loginOAuth(oauthRequest, AuthProviderEm.facebook);

    }


    private ResponseEntity<?> loginOAuth(OAuthRequestPd oauthRequest, AuthProviderEm authProviderEm){

        User user = userService.getUserByProviderId(oauthRequest.getProviderId(), authProviderEm);

        try {
            if (user == null) {
                // if we don't have such user

                switch(authProviderEm){
                    case facebook:
                        authService.loadFacebookProfile(oauthRequest);
                        break;
                    case google:
                        authService.loadGoogleProfile(oauthRequest);
                        break;
                    case wechat:
                        authService.loadWechatProfile(oauthRequest);
                        break;
                }

                // set the user image
                try{
                    String imageName = fileService.uploadImage(IOUtils.toByteArray(new URL(oauthRequest.getImageUrl()).openStream()));
                    oauthRequest.setImageUrl(imageName);
                }catch (Exception e){
                    // image error
                    e.printStackTrace();
                }

                user = userService.createNewOAuthUser(authProviderEm, oauthRequest);

            } else {
                userService.updateOAuthLogin(user, oauthRequest);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        ApiResponsePd apiResponse = jwtTokenProvider.getAuthenticationResponse(user.getId());
        return ResponseEntity.ok(apiResponse);
    }

}
