package com.cbsys.saleexplore.service.discount;

import com.cbsys.saleexplore.iservice.discount.IDiscountRatingService;
import com.cbsys.saleexplore.entity.DiscountRating;
import com.cbsys.saleexplore.idao.IDiscountDAO;
import com.cbsys.saleexplore.idao.IDiscountRatingDAO;
import com.cbsys.saleexplore.payload.DiscountRatingCountPd;
import com.cbsys.saleexplore.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DiscountRatingService implements IDiscountRatingService {

    @Autowired
    private IDiscountRatingDAO discountRatingDAO;

    @Autowired
    private IDiscountDAO discountDAO;

    @Override
    public boolean userRate(DiscountRating rating) {
        try {
            rating.setCreatedTime(new Timestamp(DateTimeUtil.getUTCTime().getTime()));
            discountRatingDAO.insert(rating);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public DiscountRating getRating(Long userId, Long discountId) {
        List<DiscountRating> ratings = discountRatingDAO.get(userId, discountId);
        if(ratings.size() == 1){
            return ratings.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void deleteRating(Long userId, Long discountId) {
        discountRatingDAO.delete(userId, discountId);
    }

    @Override
    public void updateDiscountRatingCount(long id, DiscountRatingCountPd countPd) {
        discountDAO.updateRatingCount(id, countPd);
    }

    @Override
    public DiscountRatingCountPd getDiscountRatingCount(long discountId) {
        return discountDAO.getDiscountRatingCount(discountId);
    }
}
