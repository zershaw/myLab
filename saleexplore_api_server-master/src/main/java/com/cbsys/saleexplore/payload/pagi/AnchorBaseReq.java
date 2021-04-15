package com.cbsys.saleexplore.payload.pagi;


import lombok.Data;

@Data
public class AnchorBaseReq {

    protected long sinceId; // the min index of the user's already requested elements
    protected long maxId; // the max index of the user's already requested elements
    protected int limit; // number of requested elements in this request

    public AnchorBaseReq(){
        this.sinceId = 0;
        this.maxId= 0;
        this.limit = 0;
    }

    public AnchorBaseReq(AnchorBaseReq ob){
        this.sinceId = ob.getSinceId();
        this.maxId = ob.getMaxId();
        this.limit = ob.getLimit();
    }

}
