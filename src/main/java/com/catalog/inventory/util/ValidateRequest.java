package com.catalog.inventory.util;

import com.catalog.inventory.pojo.CategoryRequest;
import com.catalog.inventory.pojo.ProductRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

public class ValidateRequest {

    public static boolean createCategoryValidation(CategoryRequest category) {
        return !ObjectUtils.isEmpty(category) &&
                ObjectUtils.isEmpty(category.getId()) &&
                !StringUtils.isBlank(category.getName()) &&
                !StringUtils.isBlank(category.getDescription());
    }

    public static boolean updateCategoryValidation(CategoryRequest category) {
        return !ObjectUtils.isEmpty(category) &&
                !ObjectUtils.isEmpty(category.getId()) &&
                (!StringUtils.isBlank(category.getName()) || !StringUtils.isBlank(category.getDescription()));
    }

    public static boolean deleteCategoryValidation(CategoryRequest category) {
        return !ObjectUtils.isEmpty(category) &&
                !ObjectUtils.isEmpty(category.getId());
    }

    public static boolean getCategoryValidation(CategoryRequest category) {
        return !ObjectUtils.isEmpty(category) &&
                (!ObjectUtils.isEmpty(category.getId()) ||
                        !StringUtils.isBlank(category.getName()) ||
                        !StringUtils.isBlank(category.getDescription()));
    }

    public static boolean createProductValidation(ProductRequest product) {
        return !ObjectUtils.isEmpty(product) &&
                ObjectUtils.isEmpty(product.getId()) &&
                !StringUtils.isBlank(product.getName()) &&
                !StringUtils.isBlank(product.getDescription()) &&
                product.getQuantity() >= 0L &&
                product.getPrice() > 0d &&
                createCategoryValidation(product.getCategory());
    }

    public static boolean updateProductValidation(ProductRequest product) {
        return !ObjectUtils.isEmpty(product) &&
                !ObjectUtils.isEmpty(product.getId()) &&
                product.getQuantity() >= 0L &&
                product.getPrice() >= 0d &&
                (!StringUtils.isBlank(product.getName()) ||
                        !StringUtils.isBlank(product.getDescription()) ||
                        updateCategoryValidation(product.getCategory()));
    }

    public static boolean deleteProductValidation(ProductRequest product) {
        return !ObjectUtils.isEmpty(product) &&
                !ObjectUtils.isEmpty(product.getId());
    }

    public static boolean getProductValidation(ProductRequest product) {
        return !ObjectUtils.isEmpty(product) &&
                (!ObjectUtils.isEmpty(product.getId()) ||
                        !StringUtils.isBlank(product.getName()) ||
                        !StringUtils.isBlank(product.getDescription()) ||
                        getCategoryValidation(product.getCategory()));
    }

}
