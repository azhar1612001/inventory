package com.catalog.inventory.controller.impl;

import com.catalog.inventory.controller.CatalogController;
import com.catalog.inventory.pojo.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.catalog.inventory.enums.HttpCodes.OK;


@RestController
@RequestMapping("/catalog")
public class CatalogControllerImpl implements CatalogController {

    @Override
    public ResponseEntity<BaseResponse> healthCheck() {
        BaseResponse baseResponse = new BaseResponse(HttpStatus.OK, OK.getCode(), "Request processed", false);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
