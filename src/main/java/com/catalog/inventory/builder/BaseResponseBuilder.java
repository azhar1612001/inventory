package com.catalog.inventory.builder;

import lombok.Data;

@Data
public class BaseResponseBuilder {
    private String status;
    private int code;
    private String message;
    private boolean isError;
}
