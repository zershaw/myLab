package com.cbsys.saleexplore.iservice;

import java.util.List;
import java.util.Set;

public interface IUserInteretsService {

    /**
     * update a user's category interests
     */
    void update(long userId, int categoryId);

    /**
     * update a user's category interests
     */
    void update(long userId, List<Integer> categoryId);



}
