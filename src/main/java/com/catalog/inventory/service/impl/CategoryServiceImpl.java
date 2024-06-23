package com.catalog.inventory.service.impl;

import com.catalog.inventory.exception.EntryNotFoundException;
import com.catalog.inventory.exception.DuplicateEntryException;
import com.catalog.inventory.exception.InvalidRequestException;
import com.catalog.inventory.model.Category;
import com.catalog.inventory.model.QCategory;
import com.catalog.inventory.pojo.CategoryRequest;
import com.catalog.inventory.repository.CategoryRepository;
import com.catalog.inventory.service.CategoryService;
import com.catalog.inventory.util.ValidateRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.catalog.inventory.constant.ConstantValue.DUPLICATE_ENTRY;
import static com.catalog.inventory.constant.ConstantValue.ENTRY_NOT_FOUND;
import static com.catalog.inventory.constant.ConstantValue.INVALID_REQUEST;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryRequest request) {
        if (!ValidateRequest.createCategoryValidation(request)) {
            throw new InvalidRequestException(INVALID_REQUEST);
        }
        Category category = categoryRepository.findByName(request.getName());
        if(ObjectUtils.isNotEmpty(category)) {
            throw new DuplicateEntryException(DUPLICATE_ENTRY);
        }
        category = populateValuesInCategory(request);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(CategoryRequest request) {
        if (!ValidateRequest.updateCategoryValidation(request)) {
            throw new InvalidRequestException(INVALID_REQUEST);
        }
        Optional<Category> categoryDocument = categoryRepository.findById(request.getId());
        if (categoryDocument.isEmpty()) {
            throw new EntryNotFoundException(ENTRY_NOT_FOUND);
        }
        populateValuesInRequest(request, categoryDocument.get());
        Category category = populateValuesInCategory((request));
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(CategoryRequest request) {
        if (!ValidateRequest.deleteCategoryValidation(request)) {
            throw new InvalidRequestException(INVALID_REQUEST);
        }
        Optional<Category> categoryDocument = categoryRepository.findById(request.getId());
        if (categoryDocument.isEmpty()) {
            throw new EntryNotFoundException(ENTRY_NOT_FOUND);
        }
        categoryRepository.deleteById(request.getId());
    }

    @Override
    public List<Category> getCategory(CategoryRequest request) {
        if (!ValidateRequest.getCategoryValidation(request)) {
            throw new InvalidRequestException(INVALID_REQUEST);
        }
        BooleanExpression finalExpression = constructBooleanExpression(request);
        Iterable<Category> iterable = categoryRepository.findAll(finalExpression);
        List<Category> categories = StreamSupport.stream(iterable.spliterator(), false).toList();
        if(CollectionUtils.isEmpty(categories)) {
            throw new EntryNotFoundException(ENTRY_NOT_FOUND);
        }
        return categories;
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    private void populateValuesInRequest(@NonNull CategoryRequest request, @NonNull Category document) {
        if (StringUtils.isBlank(request.getName())) {
            request.setName(document.getName());
        }
        if (StringUtils.isBlank(request.getDescription())) {
            request.setDescription(document.getDescription());
        }
    }

    private Category populateValuesInCategory(@NonNull CategoryRequest request) {
        Category category = new Category();
        if(ObjectUtils.isNotEmpty(request.getId())) {
            category.setId(new ObjectId(request.getId()));
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    private BooleanExpression constructBooleanExpression(@NonNull CategoryRequest request) {
        QCategory qCategory = QCategory.category;
        BooleanExpression finalExpression = null;
        if (ObjectUtils.isNotEmpty(request.getId())) {
            ObjectId objectId = new ObjectId(request.getId());
            finalExpression = addBooleanExpression(finalExpression, qCategory.id.eq(objectId));
        }
        if (!StringUtils.isBlank(request.getName())) {
            finalExpression = addBooleanExpression(finalExpression, qCategory.name.eq(request.getName()));
        }
        if (!StringUtils.isBlank(request.getDescription())) {
            finalExpression = addBooleanExpression(finalExpression, qCategory.description.eq(request.getDescription()));
        }
        return finalExpression;
    }

    private BooleanExpression addBooleanExpression(BooleanExpression finalExpression, BooleanExpression additional) {
        return ObjectUtils.isEmpty(finalExpression) ? additional : finalExpression.and(additional);
    }
}
