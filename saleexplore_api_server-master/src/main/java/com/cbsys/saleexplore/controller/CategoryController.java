package com.cbsys.saleexplore.controller;

import com.cbsys.saleexplore.entity.Category;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.iservice.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {



    @Autowired
    private ICategoryService categoryService;

    /**
     * Get the details of a specific discount
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC
            + ConstantConfig.API_VCODE_ONE + "/category")
    public List<Category> getAllCategories() {

        List<Category> categories = categoryService.getAllCategories();

        return categories;
    }
}
