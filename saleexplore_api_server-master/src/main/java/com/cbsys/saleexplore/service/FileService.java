package com.cbsys.saleexplore.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.cbsys.saleexplore.iservice.IFileService;
import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.util.ImageUtil;
import com.cbsys.saleexplore.util.RandomStringUtil;
import org.apache.commons.io.FileUtils;
import com.cbsys.saleexplore.config.ConstantConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileService implements IFileService, InitializingBean {

    @Autowired
    private AppProperties appProperties;

    private AWSCredentials credentials;

    private AmazonS3 s3client = null;

    private RandomStringUtil randomStringUtil;


    @Override
    public void deleteImage(String imageName) {

        // delete the raw image
        try{
            s3client.deleteObject(appProperties.getAuth().getAwsS3BucketName(), ConstantConfig.IMAGE_SERVER_RAW_FOLDER + imageName);
        }catch (Exception e){
            e.printStackTrace();
        }

        // delete the resize image
        try{
            s3client.deleteObject(appProperties.getAuth().getAwsS3BucketName(), ConstantConfig.IMAGE_SERVER_RESIZE_FOLDER + imageName);
        }catch (Exception e){
            e.printStackTrace();
        }

        // delete the thumb image
        try{
            s3client.deleteObject(appProperties.getAuth().getAwsS3BucketName(), ConstantConfig.IMAGE_SERVER_THUMB_FOLDER + imageName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public String uploadImage(byte[] imageContent) {

        String imageName = randomStringUtil.nextString() + ".jpeg";

        try {

            File tempRawFile = File.createTempFile("cluelez-raw", ".jpeg");
            File tempResizeFile = File.createTempFile("cluelez-resize", ".jpeg");
            File tempThumbFile = File.createTempFile("cluelez-thumb", ".jpeg");

            // Temparily save the raw file and create a resize one and thumb one
            FileUtils.writeByteArrayToFile(tempRawFile, imageContent);

            ImageUtil.cropResize(tempRawFile.getAbsolutePath(), tempResizeFile.getAbsolutePath(),
                    ConstantConfig.RESIZE_IMAGE_WIDTH,
                    ConstantConfig.RESIZE_IMAGE_HEIGHT);

            ImageUtil.cropResize(tempRawFile.getAbsolutePath(), tempThumbFile.getAbsolutePath(),
                    ConstantConfig.THUMB_IMAGE_WIDTH,
                    ConstantConfig.THUMB_IMAGE_HEIGHT);

            // upload the raw image
            s3client.putObject(appProperties.getAuth().getAwsS3BucketName(),
                    ConstantConfig.IMAGE_SERVER_RAW_FOLDER + imageName, tempRawFile);
            s3client.putObject(appProperties.getAuth().getAwsS3BucketName(),
                    ConstantConfig.IMAGE_SERVER_RESIZE_FOLDER + imageName, tempResizeFile);
            s3client.putObject(appProperties.getAuth().getAwsS3BucketName(),
                    ConstantConfig.IMAGE_SERVER_THUMB_FOLDER + imageName, tempThumbFile);


            tempRawFile.delete();
            tempResizeFile.delete();
            tempThumbFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return imageName;

    }

    @Override
    public void afterPropertiesSet() {
        // the image name has 64 characters
        randomStringUtil = new RandomStringUtil(64);

        credentials = new BasicAWSCredentials(
                appProperties.getAuth().getAwsAccessKeyId(),
                appProperties.getAuth().getAwsAccessKeySecrete()
        );

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.ME_SOUTH_1)
                .build();
    }
}
