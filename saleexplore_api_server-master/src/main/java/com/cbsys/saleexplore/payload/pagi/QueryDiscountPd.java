package com.cbsys.saleexplore.payload.pagi;


import com.cbsys.saleexplore.enums.DiscountOrderByEm;
import lombok.Data;

@Data
public class QueryDiscountPd extends AnchorBaseReq{

    private long storeId;    // storeId
    private int cityId;     // the user requested city
    private int countryId;
    private float latitude; // latitude of user when user send the request
    private float longitude; // longitude of user when user send the request
    private float nearby; // the distance the distance must be in (killo meters)
    private String kwQuery; // keyword query


    private boolean onlyOnline; // only online discount
    private boolean onlyOffline; // only offline discount
    private boolean onlyOnGoing; // only ongoing discount
    private boolean onlyUpComing; // only upcoming discount
    private boolean onlyExpired; // only expired discount

    private boolean onlyValid; // only valid discount

    private DiscountOrderByEm orderBy;

    public QueryDiscountPd(){
        // // premitive types are set to 0 and false by default
        this.onlyValid = true;
        this.orderBy = DiscountOrderByEm.DEFAULT;
    }

    public QueryDiscountPd(QueryDiscountPd ob){
        super(ob);

        onlyValid = true;

        storeId = ob.getStoreId();
        cityId = ob.getCityId();
        countryId = ob.getCountryId();
        latitude = ob.getLatitude();
        longitude = ob.getLongitude();
        onlyOnline = ob.isOnlyOnline();
        onlyOffline = ob.isOnlyOffline();
        onlyOnGoing = ob.isOnlyOnGoing();
        onlyUpComing = ob.isOnlyUpComing();
        nearby = ob.getNearby();
        kwQuery = ob.getKwQuery();
        orderBy = ob.getOrderBy();

    }

}
