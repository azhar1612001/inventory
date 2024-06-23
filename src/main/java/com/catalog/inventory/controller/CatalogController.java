package com.catalog.inventory.controller;

import com.catalog.inventory.pojo.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface CatalogController {

    @GetMapping(value = "/healthCheck", produces = "application/json")
    ResponseEntity<BaseResponse> healthCheck();
}
