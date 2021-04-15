package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IUserService;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.User;
import com.cbsys.saleexplore.entity.UserLoginDevice;
import com.cbsys.saleexplore.enums.AuthProviderEm;
import com.cbsys.saleexplore.idao.IUserDAO;
import com.cbsys.saleexplore.payload.*;
import com.cbsys.saleexplore.payload.*;
import com.cbsys.saleexplore.util.DateTimeUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements IUserService {


    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private IUserDAO userDAO;


    @Autowired
    private PasswordEncoder passwordEncoder;





    @Override
    public int getTotalNumber() {
        return userDAO.getTotalNumber();
    }


    @Override
    public int updateProfileImage(String imageName, long userId) {
        return userDAO.updateProfileImage(imageName, userId);
    }

    @Override
    public int updateUser(long userId, UserUpdatePd userUpdatePd) {
        return userDAO.updateUser(userId, userUpdatePd);
    }

    @Override
    public User getById(long userId) {
        return userDAO.getUser(userId, null, null, null);
    }


    @Override
    public User getUserByProviderId(String providerId, AuthProviderEm providerEm) {

        User user = userDAO.getUser(null, null, providerId, providerEm.getCode());

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userDAO.getUser(null, email, null, null);

        return user;
    }


    @Override
    public void updateEmailLogin(User user, LoginRequestPd loginRequestPd) {
        LoginDevicePd devicePd = loginRequestPd.getLoginDevicePayload();
        if (devicePd != null) {
            updateUserLogin(user, devicePd);
        }
    }

    @Override
    public void updateOAuthLogin(User user, OAuthRequestPd loginRequestPd) {
        LoginDevicePd devicePd = loginRequestPd.getLoginDevicePayload();
        if (devicePd != null) {
            updateUserLogin(user, devicePd);
        }
    }

    @Override
    public int updateEmailPwd(String email, String password) {
        return userDAO.updateUserEmailPwd(email, password);
    }

    @Override
    public int updateEmailVeried(String email) {
        return userDAO.updateUserEmailVeried(email);
    }

    @Override
    public int updateLastActive(long id, float latitude, float longitude) {
        return userDAO.updateLastActive(id, latitude, longitude);
    }


    /**
     * @param user,          the user logging in
     * @param loginDevicePd, the loginDevicePayload
     */
    private void updateUserLogin(User user, LoginDevicePd loginDevicePd) {
        UserLoginDevice loginDevice = new UserLoginDevice(user.getId());
        loginDevicePd.setLoginDevice(loginDevice);
        loginDevicePd.setUserCodeToken(user);

        Timestamp timeNowUTC = new Timestamp(DateTimeUtil.getUTCTime().getTime());
        user.setLastTimeLogin(timeNowUTC);

        // update the app related infor
        userDAO.updateUserAppInfo(user.getId(),
                user.getDeviceToken(),
                user.getAppVersionCode(),
                user.getOsType(),
                timeNowUTC);

        // record device info
        userDAO.updateLoginDeviceInfo(loginDevice);
    }


    @Override
    @Transactional
    public User createNewEmailUser(SignUpRequestPd signUpRequestPd) {
        // register a user account
        User user = new User();
        user.setUsername(signUpRequestPd.getUsername());
        user.setEmail(signUpRequestPd.getEmail());
        user.setPassword(signUpRequestPd.getPassword());
        user.setAuthProvider(AuthProviderEm.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        createNewUser(user, null);

        return user;
    }

    /**
     * @return create a new user based on OAuth info
     */
    @Override
    @Transactional
    public User createNewOAuthUser(AuthProviderEm authProvider, OAuthRequestPd oauthRequest) {
        // register a user account
        User user = new User();
        user.setUsername(oauthRequest.getUsername());
        user.setProviderId(oauthRequest.getProviderId());
        user.setAuthProvider(authProvider);

        user.setImageName(oauthRequest.getImageUrl());

        createNewUser(user, oauthRequest.getLoginDevicePayload());
        return user;
    }

    @Override
    public int deleteUser(User user) {

        // We support the user to delete their accounts.
        // In the database, we remove their personal information and mark the account as deleted
        int affectedRows = userDAO.markAsDelete(user.getId());

        return affectedRows;
    }


    private void createNewUser(User user, LoginDevicePd devicePd) {

        UserLoginDevice loginDevice = new UserLoginDevice(-1);
        if (devicePd != null) {
            devicePd.setLoginDevice(loginDevice);
            devicePd.setUserCodeToken(user);
        }

        // record the time
        Timestamp timeNowUTC = new Timestamp(DateTimeUtil.getUTCTime().getTime());
        user.setLastTimeLogin(timeNowUTC);
        user.setDateTimeSignUp(timeNowUTC);

        // add a place holder of the device infor for this user
        userDAO.insert(user);

        // User login device on the extent table
        loginDevice.setId(user.getId());
        userDAO.insertLoginDevice(loginDevice);
    }


    @Async
    @Override
    public void sendDeleteEmail(User user) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            Map<String, Object> model = new HashMap();
            model.put("username", user.getUsername());

            // set loading location to src/main/resources
            // You may want to use a subfolder such as /templates here
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

            Template t = freemarkerConfig.getTemplate("deleteAccount.html");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(user.getEmail());
            helper.setText(text, true); // set to html
            // config the subject of this email
            helper.setSubject(ConstantConfig.EMAIL_DELETE_ACCOUNT_SUBJECT);

            sender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
