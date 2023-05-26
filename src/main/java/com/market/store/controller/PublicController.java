package com.market.store.controller;

import com.market.store.helper.GlobalCommonService;
import com.market.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@SuppressWarnings("All")
@RestController
@RequestMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicController {

    @Autowired
    private GlobalCommonService globalCommonService;

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        return globalCommonService.getResponseOfObject(Optional.of(productService.getAllProducts()), HttpStatus.OK);
    }
}
