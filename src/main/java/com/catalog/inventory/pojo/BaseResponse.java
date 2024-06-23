package com.catalog.inventory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BaseResponse {
    private HttpStatus status;
    private int code;
    private String message;
    private boolean isError;
}
