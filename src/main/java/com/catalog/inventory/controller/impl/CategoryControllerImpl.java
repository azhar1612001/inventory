package com.catalog.inventory.controller.impl;

import com.catalog.inventory.controller.CategoryController;
import com.catalog.inventory.exception.DuplicateEntryException;
import com.catalog.inventory.exception.EntryNotFoundException;
import com.catalog.inventory.exception.InvalidRequestException;
import com.catalog.inventory.model.Category;
import com.catalog.inventory.pojo.BaseResponse;
import com.catalog.inventory.pojo.CategoryRequest;
import com.catalog.inventory.pojo.CategoryResponse;
import com.catalog.inventory.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static com.catalog.inventory.constant.ConstantValue.SUCCESSFULLY_CREATED;
import static com.catalog.inventory.constant.ConstantValue.SUCCESSFULLY_DELETED;
import static com.catalog.inventory.constant.ConstantValue.SUCCESSFULLY_FETCHED;
import static com.catalog.inventory.constant.ConstantValue.SUCCESSFULLY_UPDATED;
import static com.catalog.inventory.enums.HttpCodes.BAD_REQUEST;
import static com.catalog.inventory.enums.HttpCodes.CONFLICT;
import static com.catalog.inventory.enums.HttpCodes.CREATED;
import static com.catalog.inventory.enums.HttpCodes.NOT_FOUND;
import static com.catalog.inventory.enums.HttpCodes.OK;

@RestController
@RequestMapping("/category")
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    CategoryControllerImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<BaseResponse> createCategory(CategoryRequest request) {
        BaseResponse baseResponse;
        try {
            Category category = categoryService.createCategory(request);
            baseResponse = new CategoryResponse(HttpStatus.CREATED, CREATED.getCode(), SUCCESSFULLY_CREATED, false, Collections.singletonList(category));
        } catch (InvalidRequestException ex) {
            baseResponse = new BaseResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        } catch (DuplicateEntryException ex) {
            baseResponse = new BaseResponse(HttpStatus.CONFLICT, CONFLICT.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseResponse> updateCategory(CategoryRequest request) {
        BaseResponse baseResponse;
        try {
            Category category = categoryService.updateCategory(request);
            baseResponse = new CategoryResponse(HttpStatus.OK, OK.getCode(), SUCCESSFULLY_UPDATED, false, Collections.singletonList(category));
        } catch (InvalidRequestException ex) {
            baseResponse = new BaseResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        } catch(EntryNotFoundException ex) {
            baseResponse = new BaseResponse(HttpStatus.NOT_FOUND, NOT_FOUND.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> deleteCategory(CategoryRequest request) {
        BaseResponse baseResponse;
        try {
            categoryService.deleteCategory(request);
            baseResponse = new BaseResponse(HttpStatus.OK, OK.getCode(), SUCCESSFULLY_DELETED, false);
        } catch (InvalidRequestException ex) {
            baseResponse = new BaseResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        } catch (EntryNotFoundException ex) {
            baseResponse = new BaseResponse(HttpStatus.NOT_FOUND, NOT_FOUND.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> getCategory(CategoryRequest request) {
        BaseResponse baseResponse;
        try {
            List<Category> categoryList = categoryService.getCategory(request);
            baseResponse = new CategoryResponse(HttpStatus.OK, OK.getCode(), SUCCESSFULLY_FETCHED, false, categoryList);
        } catch (InvalidRequestException ex) {
            baseResponse = new BaseResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        } catch (EntryNotFoundException ex) {
            baseResponse = new BaseResponse(HttpStatus.NOT_FOUND, NOT_FOUND.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> getCategoryById(String id, String source) {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(id);
        return getCategory(categoryRequest);
    }

    @Override
    public ResponseEntity<BaseResponse> getCategoryByName(String name) {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(name);
        return getCategory(categoryRequest);
    }

}
