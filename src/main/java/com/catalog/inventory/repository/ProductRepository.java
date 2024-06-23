package com.catalog.inventory.repository;

import com.catalog.inventory.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends MongoRepository<Product, String>, QuerydslPredicateExecutor<Product> {

    Product findByName(String name);

}
