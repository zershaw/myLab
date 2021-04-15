package com.cbsys.saleexplore.controller.auth;

import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.EmailPwdVCode;
import com.cbsys.saleexplore.entity.User;
import com.cbsys.saleexplore.iservice.IEmailAccountVeriService;
import com.cbsys.saleexplore.iservice.IProfaneFilterService;
import com.cbsys.saleexplore.iservice.IUserService;
import com.cbsys.saleexplore.payload.*;
import com.cbsys.saleexplore.payload.*;
import com.cbsys.saleexplore.security.JWTTokenProvider;
import com.cbsys.saleexplore.util.DateTimeUtil;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE + "/auth")
public class AuthController {


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmailAccountVeriService emailVService;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private IProfaneFilterService profaneFilterService;


    @PostMapping("/verifyJWTToken")
    public ResponseEntity<?> verifyJWTToken(@RequestBody ApiRequestPd oauthRequest) {

        String jwtToken = oauthRequest.getAuthToken();

        // extract the userId from the jwt token, remove the first seven characters
        if (StringUtils.hasText(jwtToken) && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }

        if (tokenProvider.validateToken(jwtToken)) {
            // get the userId from the jwtToken
            long userId = tokenProvider.getUserIdFromToken(jwtToken);

            // generate a new jwt-token
            ApiResponsePd apiResponse = tokenProvider.getAuthenticationResponse(userId);
            return ResponseEntity.ok(apiResponse);
        }

        // jwtToken verified failed
        ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
        return ResponseEntity.ok(apiResponse);

    }

    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "The user does not exist or deleted"),
            @ApiResponse(code = 1, message = "The user's account has been disabled"),
            @ApiResponse(code = 2, message = "The password is not correct")
    })
    @PostMapping("/userEmailLogin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestPd loginRequest) {

        User user = userService.getUserByEmail(loginRequest.getEmail());

        if (user == null || user.isDeleted()) {
            // user account has already been deleted
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }

        if (!user.isEnabled()) {
            // user account has been disabled
            ApiResponsePd apiResponse = new ApiResponsePd(false, 2);
            return ResponseEntity.ok(apiResponse);
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // password not connect
            ApiResponsePd apiResponse = new ApiResponsePd(false, 3);
            return ResponseEntity.ok(apiResponse);
        }

        // update the login information
        userService.updateEmailLogin(user, loginRequest);

        // seems must use the plain password in the spring context not encoded one
        ApiResponsePd apiResponse = tokenProvider.getAuthenticationResponse(user.getId());

        return ResponseEntity.ok(apiResponse);

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user registered"),
            @ApiResponse(code = 1, message = "The user already exists"),
            @ApiResponse(code = 2, message = "username has illegal characters")
    })
    @PostMapping("/userEmailRegister")
    @Transactional
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestPd signUpRequest) {

        // check illegal characters
        if(profaneFilterService.containsProfaneText(signUpRequest.getUsername())){
            // the username has illegal characters
            ApiResponsePd apiResponse = new ApiResponsePd(false, 2);
            return ResponseEntity.ok(apiResponse);
        }

        // user lowercase email always
        signUpRequest.setEmail(signUpRequest.getEmail().trim().toLowerCase());

        User existUser = userService.getUserByEmail(signUpRequest.getEmail());

        if (existUser != null) {
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }

        // register a user account
        User user = userService.createNewEmailUser(signUpRequest);

        // send a new verification link
        emailVService.sendEmailVeryLink(user);

        ApiResponsePd apiResponse = new ApiResponsePd(true, user.getId());
        return ResponseEntity.ok(apiResponse);
    }


    /**
     * receive the user's email reset password
     */
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successfully send the email to user"),
            @ApiResponse(code = 1, message = "This email does not exist"),
            @ApiResponse(code = 2, message = "The user request too often")
    })
    @GetMapping("/sendPwdVerification")
    public ResponseEntity<?> resetPwdReq(@RequestParam("email") String email) {

        User user = userService.getUserByEmail(email);

        if (user == null || user.isDeleted()) {
            // if the corresponding account does not exist
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }

        EmailPwdVCode vCode = emailVService.getVCodeByEmail(email);
        long timeNowInMil = DateTimeUtil.getUTCTime().getTime();

        if (vCode != null && (timeNowInMil - vCode.getCreatedTime().getTime()) <= ConstantConfig.PWD_FREQUENT_EMAIL_LIMIT) {
            // the user request too often, he should use his last one
            // only allows 1 in each minutes
            ApiResponsePd apiResponse = new ApiResponsePd(false, 2);
            return ResponseEntity.ok(apiResponse);
        }

        // send the verification code to the user's email
        emailVService.sendVeriCodeEmailPwd(user);

        ApiResponsePd apiResponse = new ApiResponsePd(true, 0);
        return ResponseEntity.ok(apiResponse);

    }


    /**
     * receive the verification link
     * this confirmEmailVeri has been used in EmailAccountVeriService. makesure they are consistent
     */
    @GetMapping("/confirmEmailVeri")
    public void confirmEmailVeri(@RequestParam("email") String email,
                            @RequestParam("veriCode") String veriCode,
                            HttpServletResponse response) {
        try {
            // the verification code is not case sensitive
            EmailPwdVCode vCode = emailVService.getVCodeByEmail(email.trim().toLowerCase());

            if (vCode != null && vCode.getVeriCode().equals(veriCode)) {

                // we will delete the verification code after using it
                emailVService.deleteEmailVCode(email);

                userService.updateEmailVeried(email);

                response.sendRedirect(appProperties.getPromotionBaseUrl() + "confirmSuccess");
            }

            response.sendRedirect(appProperties.getPromotionBaseUrl() + "confirmFailed");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * receive and confirm the verification code with new chosen password.
     * update the user's password by email
     */
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successfully updated the user's password"),
            @ApiResponse(code = 1, message = "This email never asked to reset the password"),
            @ApiResponse(code = 2, message = "The verification code is not correct"),
            @ApiResponse(code = 3, message = "The verification code has already expired")
    })
    @PostMapping("/confirmPwdVerification")
    public ResponseEntity<?> confirmPwdByVeri(@Valid @RequestBody UpdatePwdPd reqPd) {

        // the verification code is not case sensitive
        EmailPwdVCode vCode = emailVService.getVCodeByEmail(reqPd.getEmail().trim().toLowerCase());

        if (vCode == null) {
            // no such request found
            // if the corresponding account does not exist
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        } else if (!vCode.getVeriCode().equals(reqPd.getVeriCode())) {
            // the verification code is not correct
            ApiResponsePd apiResponse = new ApiResponsePd(false, 2);
            return ResponseEntity.ok(apiResponse);
        }

        long timeNowInMil = DateTimeUtil.getUTCTime().getTime();

        // the verification code only have 5 minutes validity
        if ((timeNowInMil - vCode.getCreatedTime().getTime()) >= ConstantConfig.PWD_VERI_EMAIL_EXPIRE) {
            // the verification code has expired
            ApiResponsePd apiResponse = new ApiResponsePd(false, 3);
            return ResponseEntity.ok(apiResponse);
        }

        userService.updateEmailPwd(reqPd.getEmail(), passwordEncoder.encode(reqPd.getPassword()));

        // we will delete the verification code after using it
        emailVService.deleteEmailVCode(reqPd.getEmail());

        ApiResponsePd apiResponse = new ApiResponsePd(true, 0);
        return ResponseEntity.ok(apiResponse);
    }


}
