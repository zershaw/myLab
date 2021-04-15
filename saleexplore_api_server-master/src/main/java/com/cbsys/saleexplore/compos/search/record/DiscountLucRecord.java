package com.cbsys.saleexplore.compos.search.record;
import lombok.Data;

/**
 * @author Xiangnan Ren
 * @project discountserver
 */

@Data
public class DiscountLucRecord {

    /*
     * static label fields
     */
    public static String ID = "DISCOUNT_ID";
    public static String TITLE = "DISCOUNT_TITLE";
    public static String INFO = "DISCOUNT_INFO_DESCRIPTION";
    public static String MALL_NAME = "MALL_NAME";
    public static String STORE_NAME = "STORE_NAME";
    public static String CITY_ID = "CITY_ID";
    public static String CATEGORY = "CATEGORY";

    /*
     * instance data fields
     */
    private long id;
    private String title;
    private String infoDescription;
    private String mallName;
    private String storeName;
    private int cityId;
    private String category;

    /**
     * lucene record doesn't allow null values
     * here we sanitise it
     */
    public void sanitise(){
        if(title == null){
            title = "";
        }
        if(infoDescription == null){
            infoDescription = "";
        }
        if(mallName == null){
            mallName = "";
        }
        if(storeName == null){
            storeName = "";
        }
        if (category == null) {
            category = "";
        }
    }



}
