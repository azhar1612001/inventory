package com.catalog.inventory.service;

import com.catalog.inventory.model.Product;
import com.catalog.inventory.pojo.ProductRequest;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequest request);
    Product updateProduct(ProductRequest request);
    void deleteProduct(ProductRequest request);
    List<Product> getProduct(ProductRequest request);
}
