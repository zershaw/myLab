package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IProfaneFilterService;
import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.profane_text.ProfanityFilter;
import com.cbsys.saleexplore.profane_text.enums.Language;
import com.cbsys.saleexplore.util.RandomStringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProfaneFilterService implements IProfaneFilterService {

    @Autowired
    private AppProperties appProperties;

    private List<ProfanityFilter> profanityFilters;

    private RandomStringUtil randomStringUtil;

    public ProfaneFilterService() {

        // the veri-code has 5 characters
        randomStringUtil = new RandomStringUtil(5);

        profanityFilters = new ArrayList<>();
        /*
         * initialize the profane filters. add the supported languages here
         */
        ProfanityFilter profanityFilter_En = new ProfanityFilter.Builder()
                .withWordReplacement("***")
                .withLanguage(Language.ENGLISH)
                .build();

        ProfanityFilter profanityFilter_zh = new ProfanityFilter.Builder()
                .withWordReplacement("***")
                .withLanguage(Language.CHINESE)
                .build();

        ProfanityFilter profanityFilter_ar = new ProfanityFilter.Builder()
                .withWordReplacement("***")
                .withLanguage(Language.ARABIC)
                .build();

        profanityFilters.add(profanityFilter_En);
        profanityFilters.add(profanityFilter_zh);
        profanityFilters.add(profanityFilter_ar);

    }

    @Override
    public boolean containsProfaneImage(byte[] imageContent) {

        try{
            HttpClient httpclient = HttpClientBuilder.create().build();

            File file = File.createTempFile(randomStringUtil.nextString(), null);
            FileUtils.writeByteArrayToFile(file, imageContent);

            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("image", file)
                    .build();

            HttpPost httpPost = new HttpPost(appProperties.getProfaneImageFilterBaseUrl() + "upload");
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);

            file.delete();

            HttpEntity result = response.getEntity();

            JSONObject myObject = new JSONObject(EntityUtils.toString(result));
            if(myObject.getBoolean("success")){
                if(myObject.getString("isPorn").equals("1")){
                    return true;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean containsProfaneText(String text) {
        if(text == null){
            return false;
        }
        for(ProfanityFilter filter : profanityFilters){
            String[] words = text.split(" ");
            for(String word : words){
                if(filter.isProfane(word)){
                    return true;
                }
            }
        }
        return false;
    }


}
