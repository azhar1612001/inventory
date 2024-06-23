package com.catalog.inventory.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "catalog_categories")
public class Category {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String name;
    private String description;
}
