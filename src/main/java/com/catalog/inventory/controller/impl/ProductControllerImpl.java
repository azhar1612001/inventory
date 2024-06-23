package com.catalog.inventory.controller.impl;

import com.catalog.inventory.controller.ProductController;
import com.catalog.inventory.exception.DuplicateEntryException;
import com.catalog.inventory.exception.EntryNotFoundException;
import com.catalog.inventory.exception.InvalidRequestException;
import com.catalog.inventory.model.Product;
import com.catalog.inventory.pojo.BaseResponse;
import com.catalog.inventory.pojo.ProductRequest;
import com.catalog.inventory.pojo.ProductResponse;
import com.catalog.inventory.service.ProductService;
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
@RequestMapping("/product")
public class ProductControllerImpl implements ProductController {

    private ProductService productService;

    ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<BaseResponse> createProduct(ProductRequest request) {
        BaseResponse baseResponse;
        try {
            Product product = productService.createProduct(request);
            baseResponse = new ProductResponse(HttpStatus.CREATED, CREATED.getCode(), SUCCESSFULLY_CREATED, false, Collections.singletonList(product));
        } catch(InvalidRequestException ex) {
            baseResponse = new BaseResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        } catch (DuplicateEntryException ex) {
            baseResponse = new BaseResponse(HttpStatus.CONFLICT, CONFLICT.getCode(), ex.getMessage(), true);
            return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> updateProduct(ProductRequest request) {
        BaseResponse baseResponse;
        try {
            Product product = productService.updateProduct(request);
            baseResponse = new ProductResponse(HttpStatus.OK, OK.getCode(), SUCCESSFULLY_UPDATED, false, Collections.singletonList(product));
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
    public ResponseEntity<BaseResponse> deleteProduct(ProductRequest request) {
        BaseResponse baseResponse;
        try {
            productService.deleteProduct(request);
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
    public ResponseEntity<BaseResponse> getProduct(ProductRequest request) {
        BaseResponse baseResponse;
        try {
            List<Product> productList = productService.getProduct(request);
            baseResponse = new ProductResponse(HttpStatus.OK, OK.getCode(), SUCCESSFULLY_FETCHED, false, productList);
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
    public ResponseEntity<BaseResponse> getProductById( String id, String source) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(id);
        return getProduct(productRequest);
    }

    @Override
    public ResponseEntity<BaseResponse> getProductByName(String name) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(name);
        return getProduct(productRequest);
    }
}
