package com.cbsys.saleexplore.payload;


import lombok.Data;

@Data
public class CityDetectReqPd {

    // geo information when user send the request
    protected Float latitude;
    protected Float longitude;

}
