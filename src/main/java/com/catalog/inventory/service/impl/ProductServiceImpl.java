package com.catalog.inventory.service.impl;

import com.catalog.inventory.exception.DuplicateEntryException;
import com.catalog.inventory.exception.EntryNotFoundException;
import com.catalog.inventory.exception.InvalidRequestException;
import com.catalog.inventory.model.Category;
import com.catalog.inventory.model.Product;
import com.catalog.inventory.model.QProduct;
import com.catalog.inventory.pojo.CategoryRequest;
import com.catalog.inventory.pojo.ProductRequest;
import com.catalog.inventory.repository.ProductRepository;
import com.catalog.inventory.service.CategoryService;
import com.catalog.inventory.service.ProductService;
import com.catalog.inventory.util.ValidateRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.catalog.inventory.constant.ConstantValue.DUPLICATE_ENTRY;
import static com.catalog.inventory.constant.ConstantValue.ENTRY_NOT_FOUND;
import static com.catalog.inventory.constant.ConstantValue.INVALID_REQUEST;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product createProduct(ProductRequest request) {
        if (!ValidateRequest.createProductValidation(request)) {
            throw new InvalidRequestException(INVALID_REQUEST);
        }
        Product product = productRepository.findByName(request.getName());
        if (ObjectUtils.isNotEmpty(product)) {
            throw new DuplicateEntryException(DUPLICATE_ENTRY);
        }
        Category category = categoryService.getCategoryByName(request.getCategory().getName());
        if(ObjectUtils.isEmpty(category)) {
            categoryService.createCategory(request.getCategory());
            category = categoryService.getCategoryByName(request.getCategory().getName());
        }
        populateValuesInRequestForCategory(request.getCategory(), category);
        product = populateValuesInProduct(request);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductRequest request) {
        if (!ValidateRequest.updateProductValidation(request)) {
            throw new InvalidRequestException(INVALID_REQUEST);
        }
        Optional<Product> productDocument = productRepository.findById(request.getId());
        if (productDocument.isEmpty()) {
            throw new EntryNotFoundException(ENTRY_NOT_FOUND);
        }
        populateValuesInRequest(request, productDocument.get());
        Product product = populateValuesInProduct(request);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(ProductRequest request) {
        if (!ValidateRequest.deleteProductValidation(request)) {
            throw new InvalidRequestException(INVALID_REQUEST);
        }
        Optional<Product> productDocument = productRepository.findById(request.getId());
        if (productDocument.isEmpty()) {
            throw new EntryNotFoundException(ENTRY_NOT_FOUND);
        }
        productRepository.deleteById(request.getId());
    }

    @Override
    public List<Product> getProduct(ProductRequest request) {
        if (!ValidateRequest.getProductValidation(request)) {
            throw new InvalidRequestException(INVALID_REQUEST);
        }
        BooleanExpression finalExpression = constructBooleanExpression(request);
        Iterable<Product> iterable = productRepository.findAll(finalExpression);
        List<Product> products = StreamSupport.stream(iterable.spliterator(), false).toList();
        if (CollectionUtils.isEmpty(products)) {
            throw new EntryNotFoundException(ENTRY_NOT_FOUND);
        }
        return products;
    }

    private void populateValuesInRequest(ProductRequest request, Product product) {
        if (request.getPrice() == 0d) {
            request.setPrice(product.getPrice());
        }
        if (request.getQuantity() == 0L) {
            request.setQuantity(product.getQuantity());
        }
        if (ObjectUtils.isEmpty(request.getCategory())) {
            request.setCategory(populateValuesInCategoryRequest(product.getCategory()));
        } else {
            populateValuesInRequestForCategory(request.getCategory(), product.getCategory());
        }

    }

    private void populateValuesInRequestForCategory(CategoryRequest categoryRequest, Category category) {
        if (ObjectUtils.isEmpty(categoryRequest.getId())) {
            categoryRequest.setId(String.valueOf(category.getId()));
        }
        if (StringUtils.isEmpty(categoryRequest.getName())) {
            categoryRequest.setName(category.getName());
        }
        if (StringUtils.isEmpty(categoryRequest.getDescription())) {
            categoryRequest.setDescription(category.getDescription());
        }
    }

    private Product populateValuesInProduct(@NonNull ProductRequest request) {
        Product product = new Product();
        if (ObjectUtils.isNotEmpty(request.getId())) {
            product.setId(new ObjectId(request.getId()));
        }
        product.setCategory(populateValuesInCategory(request.getCategory()));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        return product;
    }

    private Category populateValuesInCategory(@NonNull CategoryRequest categoryRequest) {
        Category category = new Category();
        if (ObjectUtils.isNotEmpty(categoryRequest.getId())) {
            category.setId(new ObjectId(categoryRequest.getId()));
        }
        category.setDescription(categoryRequest.getDescription());
        category.setName(categoryRequest.getName());
        return category;
    }

    private CategoryRequest populateValuesInCategoryRequest(@NonNull Category category) {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(String.valueOf(category.getId()));
        categoryRequest.setDescription(category.getDescription());
        categoryRequest.setName(category.getName());
        return categoryRequest;
    }

    private BooleanExpression constructBooleanExpression(@NonNull ProductRequest request) {
        QProduct qProduct = QProduct.product;
        BooleanExpression finalExpression = null;
        if (ObjectUtils.isNotEmpty(request.getId())) {
            ObjectId objectId = new ObjectId(request.getId());
            finalExpression = addBooleanExpression(finalExpression, qProduct.id.eq(objectId));
        }
        if (!StringUtils.isBlank(request.getName())) {
            finalExpression = addBooleanExpression(finalExpression, qProduct.name.eq(request.getName()));
        }
        if (!StringUtils.isBlank(request.getDescription())) {
            finalExpression = addBooleanExpression(finalExpression, qProduct.description.eq(request.getDescription()));
        }
        if (ObjectUtils.isNotEmpty(request.getCategory())) {
            finalExpression = addBooleanExpression(finalExpression, qProduct.category.eq(populateValuesInCategory(request.getCategory())));
        }
        return finalExpression;
    }

    private BooleanExpression addBooleanExpression(BooleanExpression finalExpression, BooleanExpression additional) {
        return ObjectUtils.isEmpty(finalExpression) ? additional : finalExpression.and(additional);
    }

}
