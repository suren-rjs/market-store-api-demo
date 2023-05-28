package com.api.arv.model.dto.request.product;

import com.api.arv.model.enums.ProductDiscountType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductItemDTO {
    private String id;
    @NotNull
    private String vendorId;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String categoryId;
    private int quantity;
    private int stock;
    private ProductDiscountType discountType;
    private int discountInPercentage;
    private int discountInPrice;
}
