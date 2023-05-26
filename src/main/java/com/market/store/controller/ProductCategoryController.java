package com.market.store.controller;

import com.market.store.helper.GlobalCommonService;
import com.market.store.model.document.ProductCategory;
import com.market.store.repository.crud.ProductCategoryRepository;
import com.market.store.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SuppressWarnings("All")
@RestController
@RequestMapping(value = "/productCategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCategoryController {
    @Autowired
    public GlobalCommonService globalCommonService;

    @Autowired
    public ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryRepository productCategoryRepository;

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return globalCommonService.getResponseOfObject(Optional.of(productCategoryRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        String message = "Invalid id";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProductCategory category = productCategoryService.getOneById(id).orElse(null);
        if (category == null) {
            return globalCommonService.getResponseEntityByMessageAndStatus(message, status);
        }
        return globalCommonService.getResponseOfObject(Optional.of(category), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> getCategoryById(@RequestBody ProductCategory productCategory) {
        String message = "new category created";
        HttpStatus status = HttpStatus.OK;
        productCategoryService.save(productCategory);
        return globalCommonService.getResponseEntityByMessageAndStatus(message, status);
    }

    @PutMapping
    public ResponseEntity<?> updateCategoryById(@RequestBody ProductCategory productCategory) {
        String message = "Invalid id";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        if (productCategory.getId() == null) {
            return globalCommonService.getResponseEntityByMessageAndStatus(message, status);
        }
        message = "Category updated";
        status = HttpStatus.OK;
        productCategoryService.save(productCategory);
        return globalCommonService.getResponseEntityByMessageAndStatus(message, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        String message = "Invalid id";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        if (productCategoryRepository.findOneById(id).isEmpty()) {
            return globalCommonService.getResponseEntityByMessageAndStatus(message, status);
        }
        message = "Category deleted";
        status = HttpStatus.OK;
        productCategoryService.deleteById(id);
        return globalCommonService.getResponseEntityByMessageAndStatus(message, status);
    }
}
