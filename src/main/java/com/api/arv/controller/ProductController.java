package com.api.arv.controller;

import com.api.arv.helper.GlobalCommonService;
import com.api.arv.model.dto.request.product.ProductItemDTO;
import com.api.arv.service.ProductService;
import com.api.arv.service.WishlistService;
import com.api.arv.service.utils.MultipartFileToXLSXConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("All")
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private GlobalCommonService globalCommonService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private MultipartFileToXLSXConverter multipartFileToXLSXConverter;

    @PostMapping
    public ResponseEntity<?> newProduct(@RequestBody ProductItemDTO productItemDto) {
        productService.save(productItemDto);
        return globalCommonService.getResponseEntityByMessageAndStatus("New product added", HttpStatus.CREATED);
    }

    @PostMapping("/bulkUpload")
    public ResponseEntity<?> bulkUpload(@RequestBody MultipartFile multipartFile) {
        try {
            List<ProductItemDTO> productItemDTOList = multipartFileToXLSXConverter.convert(multipartFile);

            try {
                productService.saveAll(productItemDTOList);
            } catch (Exception e) {
                return globalCommonService.getResponseEntityByMessageAndStatus("Exception while save : " + e.toString(), HttpStatus.NOT_ACCEPTABLE);
            }

            return globalCommonService.getResponseEntityByMessageAndStatus("Products Uploaded successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return globalCommonService.getResponseEntityByMessageAndStatus("Unable to upload " + e.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductItemDTO productItemDto) {
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
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
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
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
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

    @GetMapping("/wishlist")
    public ResponseEntity<?> getWishlistProducts() {
        return globalCommonService.getResponseOfObject(Optional.of(wishlistService.findAllByCustomerId()), HttpStatus.OK);
    }

    @PutMapping("/wishlist/{id}")
    public ResponseEntity<?> wishlistToggle(@PathVariable("id") String id) {
        return globalCommonService.getResponseEntityByMessageAndStatus(wishlistService.toggleWishlist(id), HttpStatus.CREATED);
    }
}
