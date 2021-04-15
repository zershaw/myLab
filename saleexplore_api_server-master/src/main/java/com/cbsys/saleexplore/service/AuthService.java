package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IAuthService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.cbsys.saleexplore.config.AppProperties;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.cbsys.saleexplore.idao.IUserDAO;
import com.cbsys.saleexplore.payload.OAuthRequestPd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private IUserDAO userDAO;


    @Override
    public boolean validateFacebookToken(OAuthRequestPd oauthRequest) {
        /**
         * Use the debug_token to varify whether this token is valid or not
         */
        HttpClient client = HttpClientBuilder.create().build();
        String url = "https://graph.facebook.com/v4.0/debug_token?access_token="
                + appProperties.getAuth().getFacebookAppID()
                + "%7C" + appProperties.getAuth().getFacebookAppSecrete()
                + "&input_token=" + oauthRequest.getAuthCode();
        HttpGet request = new HttpGet(url);

        try {
            /*
             * need to verify this token is from our own app
             */
            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){
                HttpEntity entity = response.getEntity();
                String facebookData = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                JsonObject convertedObject = new Gson().fromJson(facebookData, JsonObject.class);
                convertedObject= convertedObject.getAsJsonObject("sql_insert_dev");

                // check whether token valid
                if(!convertedObject.get("is_valid").getAsBoolean()){
                    return false;
                }
                // check whether userId consistent
                if(!oauthRequest.getProviderId().equals(convertedObject.get("user_id").getAsString())){
                    return false;
                }

            }else{
                // facebook validation return error
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    @Override
    public void loadFacebookProfile(OAuthRequestPd oauthRequest){
        /*
         * We need an extra request to get facebook com.cbsys.saleexplore.profile
         */
        HttpClient client = HttpClientBuilder.create().build();
        String profileUrl = "https://graph.facebook.com/v4.0/me?fields=name,picture&access_token=" + oauthRequest.getAuthCode();
        HttpGet profileRequest = new HttpGet(profileUrl);
        try {
            HttpResponse profileresponse = client.execute(profileRequest);
            int code = profileresponse.getStatusLine().getStatusCode();
            if(code == 200){
                HttpEntity entity = profileresponse.getEntity();
                String facebookData = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                JsonObject convertedObject = new Gson().fromJson(facebookData, JsonObject.class);

                oauthRequest.setUsername(convertedObject.get("name").getAsString());
                oauthRequest.setImageUrl(convertedObject.getAsJsonObject("picture").getAsJsonObject("sql_insert_dev").get("url")
                        .getAsString());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean validateGoogleToken(OAuthRequestPd oauthRequest) {
        /**
         * Google is different, the response body of the tokeninfo api already give us the user profiles
         * We don't need to send a new request to get user profiles
         */
        HttpClient client = HttpClientBuilder.create().build();
        String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + oauthRequest.getAuthCode();
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){
                HttpEntity entity = response.getEntity();
                String googleData = EntityUtils.toString(entity, StandardCharsets.UTF_8);

                JsonObject convertedObject = new Gson().fromJson(googleData, JsonObject.class);

                // check whether userId consistent
                if(!oauthRequest.getProviderId().equals(convertedObject.get("sub").getAsString())){
                    return false;
                }

                // get the information from the wechat user
                oauthRequest.setUsername(convertedObject.get("name").getAsString());
                oauthRequest.setImageUrl(convertedObject.get("picture").getAsString());
                // TODO, city and gender

            } else {
                // google validation return error
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void loadGoogleProfile(OAuthRequestPd oauthRequest){
        /*
         * TODO, if need more infor from google, reference this link
         * We need an extra request to get google com.cbsys.saleexplore.profile
         */
        //String profileUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

    }



    @Override
    public boolean validateWechatToken(OAuthRequestPd oauthRequest) {
        /**
         * * https://www.cnblogs.com/0201zcr/p/5131602.html
         * 1. First use the authentication code of access_token api to get the access_token
         * 2. Then we use the access_token to get the user's com.cbsys.saleexplore.profile data
         */
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appProperties.getAuth().getWechatAppID()
                + "&secret=" + appProperties.getAuth().getWechatAppSecrete()
                + "&code=" + oauthRequest.getAuthCode() + "&grant_type=authorization_code";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();

            if (code == 200) {
                HttpEntity entity = response.getEntity();
                String googleData = EntityUtils.toString(entity, StandardCharsets.UTF_8);

                JsonObject jsonObject = new Gson().fromJson(googleData, JsonObject.class);

                String openid = jsonObject.get("openid").getAsString();
                String token = jsonObject.get("access_token").getAsString();

                // damn wechat not giving us the userId with the authentication code. (not like google and facebook)
                // we set the providerId here.
                oauthRequest.setProviderId(openid);

                // override the auth_code by access token
                oauthRequest.setAuthCode(token);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void loadWechatProfile(OAuthRequestPd oauthRequest){
        /*
         * https://www.cnblogs.com/0201zcr/p/5131602.html
         * We need an extra request to get facebook com.cbsys.saleexplore.profile
         */
        HttpClient client = HttpClientBuilder.create().build();
        String profileUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + oauthRequest.getAuthCode()
                + "&openid=" + oauthRequest.getProviderId()
                + "&lang=zh_CN";
        HttpGet profileRequest = new HttpGet(profileUrl);

        try {
            HttpResponse profileresponse = client.execute(profileRequest);
            int code = profileresponse.getStatusLine().getStatusCode();

            if(code == 200){
                HttpEntity entity = profileresponse.getEntity();
                // must use utf_8 to accept chinese
                String wechatData = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                JsonObject convertedObject = new Gson().fromJson(wechatData, JsonObject.class);


                // get the information from the wechat user
                oauthRequest.setUsername(convertedObject.get("nickname").getAsString());
                oauthRequest.setImageUrl(convertedObject.get("headimgurl").getAsString());

                // wechat gives us those infor by default
                oauthRequest.setCity(convertedObject.get("city").getAsString());
                oauthRequest.setGender(convertedObject.get("sex").getAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
