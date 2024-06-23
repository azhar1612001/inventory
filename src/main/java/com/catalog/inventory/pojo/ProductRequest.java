package com.catalog.inventory.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductRequest {
    @JsonProperty("productId")
    private String id;
    @JsonProperty("productName")
    private String name;
    private String description;
    private double price;
    private long quantity;
    private CategoryRequest category;
}
