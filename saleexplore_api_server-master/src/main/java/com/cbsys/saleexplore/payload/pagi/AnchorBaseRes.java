package com.cbsys.saleexplore.payload.pagi;


import lombok.Data;

@Data
public class AnchorBaseRes {

    protected long sinceId;
    protected long maxId;

    // used in request when scrolling to bot, get earlier feeds
    protected AnchorBaseReq refresh;

    // used in request when polling at top, update to load latter feeds
    protected AnchorBaseReq loadMore;


}
