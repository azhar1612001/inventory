package com.catalog.inventory.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CategoryRequest {

    @JsonProperty("categoryId")
    private String id;

    @JsonProperty("categoryName")
    private String name;

    private String description;
}
