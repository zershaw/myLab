package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.payload.DiscountRatingCountPd;
import com.cbsys.saleexplore.payload.DiscountUpdatePd;
import com.cbsys.saleexplore.payload.pagi.QueryDiscountPd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IDiscountDAO {

    /**
     * only return the ids of the queried discounts
     *
     * @param ids, the discounts ids must within ids
     * @param qPd, the query parameter payload
     */
    List<Long> getIds(@Param("ids") List<Long> ids,
                      @Param("qPd") QueryDiscountPd qPd,
                      @Param("notInIds") List<Long> notInIds);


    /**
     * @param ids, the discount's id must within ids
     * @param qPd, filtering parameters to query the discounts
     */
    List<Discount> get(@Param("ids") List<Long> ids,
                       @Param("qPd") QueryDiscountPd qPd,
                       @Param("notInIds") List<Long> notInIds);

    /**
     * return value is the number of rows affected
     */
    int insert(@Param("discount") Discount discount);


    /**
     * return value is the number of rows affected
     */
    int update(@Param("id") Long id,
               @Param("updatePd") DiscountUpdatePd updatePd);


    /**
     * return value is the number of rows affected
     */
    int delete(@Param("id") long id);


    /**
     * increase the view counts of this discount by 1
     */
    void increaseViewCount(@Param("id") long id);


    /**
     * @param id,     the id of the discount
     * @param countPd which contains the number of likes and dislikes
     */
    void updateRatingCount(@Param("id") long id, @Param("countPd") DiscountRatingCountPd countPd);

    /**
     * get the number of dislikes and likes count of this discount
     *
     * @param id the discountid
     */
    DiscountRatingCountPd getDiscountRatingCount(@Param("id") long id);

}
