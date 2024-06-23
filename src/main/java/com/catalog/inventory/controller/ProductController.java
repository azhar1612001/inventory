package com.catalog.inventory.controller;

import com.catalog.inventory.pojo.BaseResponse;
import com.catalog.inventory.pojo.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductController {
    @PostMapping("/createProduct")
    ResponseEntity<BaseResponse> createProduct(@RequestBody ProductRequest request);

    @PostMapping("/updateProduct")
    ResponseEntity<BaseResponse> updateProduct(@RequestBody ProductRequest request);

    @PostMapping("/deleteProduct")
    ResponseEntity<BaseResponse> deleteProduct(@RequestBody ProductRequest request);

    @PostMapping("/getProduct")
    ResponseEntity<BaseResponse> getProduct(@RequestBody ProductRequest request);

    @GetMapping("/getProductById/{id}")
    ResponseEntity<BaseResponse> getProductById(@PathVariable("id") String id,
                                                 @RequestHeader(required = false, value = "source") String source);

    @GetMapping("getProductByName")
    ResponseEntity<BaseResponse> getProductByName(@RequestParam(required = false, value = "name") String name);
}
