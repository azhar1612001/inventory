package com.catalog.inventory.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "catalog_products")
public class Product {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String name;
    private String description;
    private double price;
    private long quantity;

    @DBRef
    private Category category;
}
