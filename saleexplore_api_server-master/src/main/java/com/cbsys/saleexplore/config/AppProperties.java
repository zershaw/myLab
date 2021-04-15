package com.cbsys.saleexplore.config;
/**
 * this class will be initized by reading the application.yml
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {

    private boolean debug;
    private Auth auth;
    private IOS ios;
    private Async async;
    private String serverBaseUrl;
    private String promotionBaseUrl;
    private String imageTaggerBaseUrl;
    private String profaneImageFilterBaseUrl;

    private String discountLuceneIndexDir;
    private String storeLuceneIndexDir;

    @Data
    public static class Auth {

        private int apiVersionCode;
        private String tokenSecret;
        private long tokenExpirationMsec;

        private String wechatAppID;
        private String wechatAppSecrete;

        private String facebookAppID;
        private String facebookAppSecrete;

        private String awsS3BucketName;
        private String awsAccessKeyId;
        private String awsAccessKeySecrete;

    }

    @Data
    public static class IOS {
        private String pushCertificatePd;
        private String pushCertificatePath;
    }

    @Data
    public static class Async{
        private int asyncCorePoolSize;
        private int asyncMaxPoolSize;
        private int asyncQueueCapacity;
        private int asyncKeepAliveSecs;
    }


}
