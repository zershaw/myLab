package com.cbsys.saleexplore.iservice;

import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.entity.Discount;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(int id);

    List<Category> getAllCategories();

    /*
     * set the category details for specific discount
     */
    void setDiscountCategoryDetail(Discount discount);

    void setDiscountCategoryDetail(List<Discount> discounts);

}
