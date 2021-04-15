package com.cbsys.saleexplore.service.store;

import com.cbsys.saleexplore.iservice.store.IStoreRatingService;
import com.cbsys.saleexplore.entity.StoreRating;
import com.cbsys.saleexplore.idao.IStoreDAO;
import com.cbsys.saleexplore.idao.IStoreRatingDAO;
import com.cbsys.saleexplore.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StoreRatingService implements IStoreRatingService {

    @Autowired
    private IStoreRatingDAO storeRatingDAO;

    @Autowired
    private IStoreDAO storeDAO;


    @Override
    public boolean userRate(StoreRating rating) {
        try {
            rating.setCreatedTime(new Timestamp(DateTimeUtil.getUTCTime().getTime()));
            storeRatingDAO.insert(rating);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public StoreRating getRating(Long userId, Long storeId) {
        List<StoreRating> ratings = storeRatingDAO.get(userId, storeId);
        if(ratings.size() == 1){
            return ratings.get(0);
        }else{
            return null;
        }
    }


    @Override
    public void deleteRating(Long userId, Long storeId) {
        storeRatingDAO.delete(userId, storeId);
    }


    @Override
    public void updateStorePopularity(long id) {
        storeDAO.updatePopularity(id);
    }

}
