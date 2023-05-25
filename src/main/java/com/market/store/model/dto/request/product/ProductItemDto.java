package com.market.store.model.dto.request.product;

import com.market.store.model.enums.ProductDiscountType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductItemDto {
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
