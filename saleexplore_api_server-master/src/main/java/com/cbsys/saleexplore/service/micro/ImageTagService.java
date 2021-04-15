package com.cbsys.saleexplore.service.micro;

import com.cbsys.saleexplore.iservice.micro.IImageTagService;
import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.idao.IDiscountDAO;
import com.cbsys.saleexplore.payload.DiscountUpdatePd;
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
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageTagService implements IImageTagService {

    @Autowired
    private IDiscountDAO discountDAO;


    @Autowired
    private AppProperties appProperties;

    private RandomStringUtil randomStringUtil;


    public ImageTagService() {
        // the veri-code has 5 characters
        randomStringUtil = new RandomStringUtil(5);
    }


    /**
     * generate the tags for the discount images of this discount
     */
    @Async
    public void tagDiscountImage(long discountId, List<byte[]> imageContents) {

        StringBuilder discountTags = new StringBuilder();

        for(byte[] imageContent : imageContents){
            List<String> imageTags = getImageTags(imageContent);
            for(String imageTag : imageTags){
                discountTags.append(imageTag + ";");
            }
        }

        DiscountUpdatePd updatePd = new DiscountUpdatePd();
        updatePd.setDiscountTags(discountTags.toString());

        discountDAO.update(discountId, updatePd);
    }



    @Override
    public List<String> getImageTags(byte[] imageContent) {

        List<String> resultTags = new ArrayList<>();
        try{
            HttpClient httpclient = HttpClientBuilder.create().build();

            File file = File.createTempFile(randomStringUtil.nextString(), null);
            FileUtils.writeByteArrayToFile(file, imageContent);

            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("image", file)
                    .build();

            HttpPost httpPost = new HttpPost(appProperties.getImageTaggerBaseUrl() + "upload");
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);

            file.delete();

            HttpEntity result = response.getEntity();

            JSONObject myObject = new JSONObject(EntityUtils.toString(result));


            if(myObject.getBoolean("success")){
                JSONArray jsonTags = myObject.getJSONArray("tags");
                for(int i = 0; i < jsonTags.length(); i++){
                    resultTags.add(jsonTags.getString(i));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultTags;
    }
}
