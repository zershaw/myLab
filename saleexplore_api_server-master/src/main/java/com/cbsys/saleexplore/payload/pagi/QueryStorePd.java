package com.cbsys.saleexplore.payload.pagi;


import com.cbsys.saleexplore.enums.StoreOrderByEm;
import lombok.Data;

@Data
public class QueryStorePd extends AnchorBaseReq{

    private long mallId;
    private int cityId;     // the user requested city
    private float latitude; // latitude of user when user send the request
    private float longitude; // longitude of user when user send the request
    private float nearby; // kms, the results should be within this distance
    private String kwQuery; // keyword query

    private StoreOrderByEm orderBy;

    public QueryStorePd(){
        // premitive types are set to 0 by default
        orderBy = StoreOrderByEm.DEFAULT;

    }

    public QueryStorePd(QueryStorePd ob){
        super(ob);

        this.cityId = ob.getCityId();
        this.latitude = ob.getLatitude();
        this.longitude = ob.getLongitude();
        this.orderBy = ob.getOrderBy();
        this.kwQuery = ob.getKwQuery();
    }
}
