package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.Store;
import com.cbsys.saleexplore.payload.DiscountDetailStorePd;
import com.cbsys.saleexplore.payload.DiscountListStorePd;
import com.cbsys.saleexplore.payload.pagi.QueryStorePd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IStoreDAO {

    /**
     * Get discount lists with store payloads
     */
    List<DiscountListStorePd> getDiscountListStorePd(@Param("ids") List<Long> ids,
                                                     @Param("latitude") float latitude,
                                                     @Param("longitude") float longitude);

    /**
     * Get discount details with store payloads
     */
    DiscountDetailStorePd getDiscountDetailStorePd(@Param("id") long storeId);

    /**
     * only return the ids of the queried stores
     *
     * @param ids, the store's ids must within ids
     * @param qPd, the query parameter payload
     */
    List<Long> getIds(@Param("ids") List<Long> ids,
                      @Param("qPd") QueryStorePd qPd,
                      @Param("notInIds") List<Long> notInIds);

    /**
     * @param ids, the store's id must within ids
     * @param qPd, the query parameter payload
     */
    List<Store> get(@Param("ids") List<Long> ids,
                    @Param("qPd") QueryStorePd qPd,
                    @Param("notInIds") List<Long> notInIds);


    /**
     * @return the number of rows affected
     */
    int insert(@Param("store") Store store);

    /**
     * @return the number of rows affected
     */
    int delete(@Param("id") Long id);


    /**
     * @param id the storeId
     */
    void updatePopularity(@Param("id") long id);

    /**
     * @param id storeId
     */
    void updateDiscountNumber(@Param("id") long id);

}
