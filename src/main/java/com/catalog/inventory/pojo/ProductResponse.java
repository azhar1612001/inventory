package com.catalog.inventory.pojo;

import com.catalog.inventory.model.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductResponse extends BaseResponse{

    List<Product> products;

    public ProductResponse(HttpStatus status, int code, String message, boolean isError, List<Product> products) {
        super(status, code, message, isError);
        this.products = products;
    }
}
