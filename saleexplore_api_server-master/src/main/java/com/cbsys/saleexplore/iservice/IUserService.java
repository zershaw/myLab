package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.entity.User;
import com.cbsys.saleexplore.enums.AuthProviderEm;
import com.cbsys.saleexplore.payload.LoginRequestPd;
import com.cbsys.saleexplore.payload.OAuthRequestPd;
import com.cbsys.saleexplore.payload.SignUpRequestPd;
import com.cbsys.saleexplore.payload.UserUpdatePd;
import org.springframework.scheduling.annotation.Async;

public interface IUserService {

    int getTotalNumber();

    User getById(long userId);

    User getUserByProviderId(String providerId, AuthProviderEm providerEm);

    User getUserByEmail(String email);


    int updateProfileImage(String imageName, long userId);

    /**
     * upload user com.cbsys.saleexplore.profile details
     */
    int updateUser(long userId, UserUpdatePd userUpdatePd);

    void updateEmailLogin(User user, LoginRequestPd loginRequestPd);

    void updateOAuthLogin(User user, OAuthRequestPd loginRequestPd);

    int updateEmailPwd(String email, String password);

    /**
     * set the user's email as veried
     */
    int updateEmailVeried(String email);


    int updateLastActive(long id, float latitude, float longitude);


    User createNewEmailUser(SignUpRequestPd signUpRequestPd);

    /**
     * based on oauth information, create a new user
     */
    User createNewOAuthUser(AuthProviderEm authProvider, OAuthRequestPd oauthRequest);


    int deleteUser(User user);

    @Async
    void sendDeleteEmail(User user);
}
