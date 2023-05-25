package com.market.store.controller;

import com.market.store.helper.GlobalCommonService;
import com.market.store.model.dto.request.product.ProductItemDto;
import com.market.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SuppressWarnings("All")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private GlobalCommonService globalCommonService;

    @Autowired
    private ProductService productService;

    public ProductController() {
    }

    @PostMapping
    public ResponseEntity<?> newProduct(@RequestBody ProductItemDto productItemDto) {
        productService.save(productItemDto);
        return globalCommonService.getResponseEntityByMessageAndStatus("New product added", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductItemDto productItemDto) {
        String message = "Product updated";
        HttpStatus response = HttpStatus.OK;
        try {
            productService.updateById(productItemDto);
        } catch (Exception e) {
            message = "unable to update details";
            response = HttpStatus.NOT_ACCEPTABLE;
        }

        return globalCommonService.getResponseEntityByMessageAndStatus(message, response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@RequestParam("id") String id) {
        String message = "Product deleted";
        HttpStatus response = HttpStatus.OK;
        try {
            productService.deleteById(id);
        } catch (Exception e) {
            message = "invalid id";
            response = HttpStatus.NOT_ACCEPTABLE;
        }

        return globalCommonService.getResponseEntityByMessageAndStatus(message, response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@RequestParam("id") String id) {
        String message;
        HttpStatus response = HttpStatus.OK;
        try {
            return globalCommonService.getResponseOfObject(Optional.of(productService.getById(id)), response);
        } catch (Exception e) {
            message = "invalid id";
            response = HttpStatus.NOT_ACCEPTABLE;
        }

        return globalCommonService.getResponseEntityByMessageAndStatus(message, response);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return globalCommonService.getResponseOfObject(Optional.of(productService.getAllProducts()), HttpStatus.OK);
    }

}
