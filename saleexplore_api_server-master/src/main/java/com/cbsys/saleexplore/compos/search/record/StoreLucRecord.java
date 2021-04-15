package com.cbsys.saleexplore.compos.search.record;

import lombok.Data;

/**
 * @author Xiangnan Ren
 * @project discountserver
 */

@Data
public class StoreLucRecord {

    /*
     * static label fields
     */
    public static String ID = "STORE_ID";
    public static String NAME = "STORE_NAME";
    public static String INFO = "STORE_INFO_DESCRIPTION";
    public static String MALL_NAME = "MALL_NAME";
    public static String CATEGORY = "STORE_CATEGORY";
    public static String BRAND = "STORE_BRAND";
    public static String CITY_ID = "CITY_ID";

    /*
     * instance data fields
     */
    private long id;
    private String storeName;
    private String infoDescription;
    private String mallName;
    private String category;
    private String brand;
    private int cityId;


    /**
     * lucene record doesn't allow null values
     * here we sanitise it
     */
    public void sanitise() {
        if (infoDescription == null) {
            infoDescription = "";
        }
        if (storeName == null) {
            storeName = "";
        }
        if (mallName == null) {
            mallName = "";
        }
        if (category == null) {
            category = "";
        }
        if (brand == null) {
            brand = "";
        }
    }
}
