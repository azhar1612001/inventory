package com.catalog.inventory.pojo;

import com.catalog.inventory.model.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryResponse extends BaseResponse {

    List<Category> categories;

    public CategoryResponse(HttpStatus status, int code, String message, boolean isError, List<Category> categories) {
        super(status, code, message, isError);
        this.categories = categories;
    }

}
