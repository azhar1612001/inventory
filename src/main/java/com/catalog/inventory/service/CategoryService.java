package com.catalog.inventory.service;

import com.catalog.inventory.model.Category;
import com.catalog.inventory.pojo.CategoryRequest;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest request);
    Category updateCategory(CategoryRequest request);
    void deleteCategory(CategoryRequest request);
    List<Category> getCategory(CategoryRequest request);
    Category getCategoryByName(String name);
}
