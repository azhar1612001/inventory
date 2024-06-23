package com.catalog.inventory.controller;

import com.catalog.inventory.pojo.BaseResponse;
import com.catalog.inventory.pojo.CategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


public interface CategoryController {

    @PostMapping("/createCategory")
    ResponseEntity<BaseResponse> createCategory(@RequestBody CategoryRequest request);

    @PostMapping("/updateCategory")
    ResponseEntity<BaseResponse> updateCategory(@RequestBody CategoryRequest request);

    @PostMapping("/deleteCategory")
    ResponseEntity<BaseResponse> deleteCategory(@RequestBody CategoryRequest request);

    @PostMapping("/getCategory")
    ResponseEntity<BaseResponse> getCategory(@RequestBody CategoryRequest request);

    @GetMapping("/getCategoryById/{id}")
    ResponseEntity<BaseResponse> getCategoryById(@PathVariable("id") String id,
                                                 @RequestHeader(required = false, value = "source") String source);

    @GetMapping("getCategoryByName")
    ResponseEntity<BaseResponse> getCategoryByName(@RequestParam(required = false, value = "name") String name);
}
