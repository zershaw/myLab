package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.entity.Mall;

import java.util.List;

public interface IMallService {

    /*
     * get all malls located in a city
     */
    List<Mall> getMallWithStoreByCity(long city);

    Mall get(long mallId);

}
