package com.catalog.inventory.repository;

import com.catalog.inventory.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CategoryRepository extends MongoRepository<Category, String>, QuerydslPredicateExecutor<Category> {

    Category findByName(String name);

}
