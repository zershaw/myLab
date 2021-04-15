package com.cbsys.saleexplore.config;

/**
 * @author Hector
 * <p>
 * this class contains the constants that needed by the
 * configurations of Discount Server
 */
public interface ConstantConfig {

    /*
     * Image Config constants
     */
    String IMAGE_SERVER_BASE_URL = "https://sale-explore.s3-ap-southeast-1.amazonaws.com/";
    String IMAGE_SERVER_RESIZE_FOLDER = "resize/";
    String IMAGE_SERVER_RAW_FOLDER = "raw/";
    String IMAGE_SERVER_THUMB_FOLDER = "thumb/";

    String IMAGE_SERVER_RESIZE_URL = IMAGE_SERVER_BASE_URL + IMAGE_SERVER_RESIZE_FOLDER;
    String IMAGE_SERVER_RAW_URL = IMAGE_SERVER_BASE_URL + IMAGE_SERVER_RAW_FOLDER;
    String IMAGE_SERVER_THUMB_URL = IMAGE_SERVER_BASE_URL + IMAGE_SERVER_THUMB_FOLDER;
    String IMAGE_DEFAULT_USER_PROFILE = IMAGE_SERVER_RESIZE_URL + "userDefaultProfile.jpeg";
    String IMAGE_DEFAULT_STORE_PROFILE = IMAGE_SERVER_RESIZE_URL + "storeDefaultProfile.jpeg";


    int RESIZE_IMAGE_WIDTH = 550;
    int RESIZE_IMAGE_HEIGHT = 550;

    int THUMB_IMAGE_WIDTH = 196;
    int THUMB_IMAGE_HEIGHT = 196;

    /*
     * API Configs
     */
    String API_VCODE_ONE = "v1";
    String URL_PREFIX_API = "/api/";
    String URL_PREFIX_API_PUBLIC = "/api/public/";

    /*
     * Email Config constants
     */
    String EMAIL_DELETE_ACCOUNT_SUBJECT = "SaleExplore Sorry to See You Go";
    String EMAIL_RESET_SUBJECT = "SaleExplore Verification Code For Resetting Password";
    String EMAIL_ACCOUNT_VERIFI_SUBJECT = "SaleExplore Email Account Verification";
    String EMAIL_CLAIM_BUSINESS_INFO_SUBJECT = "Claim Your Business";


    int PWD_FREQUENT_EMAIL_LIMIT = 10000; // 10 seconds
    int PWD_VERI_EMAIL_EXPIRE = 300000; // 10 seconds

    /*
     * scheduler constants
     */
    int SCHEDULER_UPDATE_STORE_NUMBER_DISCOUNTS = 60 * 60 * 1000; // 2 hour

}
