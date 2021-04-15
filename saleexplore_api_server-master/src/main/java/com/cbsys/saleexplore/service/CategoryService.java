package com.cbsys.saleexplore.service;


import com.cbsys.saleexplore.iservice.ICategoryService;
import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.idao.ICategoryDAO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService implements ICategoryService, InitializingBean {
    /**
     * We load all the category data to the memory when we start the server
     * If you updated the category data, it can only be picked up by reboot server
     */

    @Autowired
    private ICategoryDAO categoryDAO;


    private List<Category> allCategories;
    private Map<Integer, Category> categoryMap;

    @Override
    public void afterPropertiesSet() {
        allCategories = categoryDAO.getAll();
        categoryMap = new HashMap<>();

        for (Category category : allCategories) {
            categoryMap.put(category.getId(), category);
        }
    }


    @Override
    public Category getCategoryById(int id) {
        return categoryMap.get(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return allCategories;
    }

    @Override
    public void setDiscountCategoryDetail(Discount discount) {
        Category category = categoryMap.get(discount.getCategoryId());
        if (category != null) {
            discount.setCategoryName(category.getName());
        }
    }

    @Override
    public void setDiscountCategoryDetail(List<Discount> discounts) {
        if (discounts == null) {
            return;
        }
        for (Discount discount : discounts) {
            try {
                Category category = categoryMap.get(discount.getCategoryId());
                if (category != null) {
                    discount.setCategoryName(category.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
