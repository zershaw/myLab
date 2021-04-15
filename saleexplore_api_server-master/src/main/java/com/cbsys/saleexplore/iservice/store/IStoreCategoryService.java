package com.cbsys.saleexplore.iservice.store;

import java.util.List;

public interface IStoreCategoryService {
    List<Integer> getStoreCategoryIds(long storeId);
}
