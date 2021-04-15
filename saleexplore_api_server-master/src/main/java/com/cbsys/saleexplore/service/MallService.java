package com.cbsys.saleexplore.service;


import com.cbsys.saleexplore.iservice.IMallService;
import com.cbsys.saleexplore.entity.Mall;
import com.cbsys.saleexplore.idao.IMallDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MallService implements IMallService {

    @Autowired
    private IMallDAO mallDAO;



    @Override
    public List<Mall> getMallWithStoreByCity(long cityId) {
        return mallDAO.getWithStore(cityId);
    }


    @Override
    public Mall get(long mallId) {
        List<Mall> malls = mallDAO.get(mallId);
        if(malls.size() == 1){
            return malls.get(0);
        }
        return null;
    }
}
