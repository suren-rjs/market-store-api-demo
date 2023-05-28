package com.api.arv.model.dto.request.product;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductItemDTO {
    private String id;
    @NotNull
    private String productName;
    private String description;
    @NotNull
    private String category;
    private String subCategory;
    private String subSubCategory;
    private String productVariation;
    private Integer mrpPrice;
    private Integer sellingPrice;
    private Integer quantity;
    private Integer stock;
    private Integer discount;
}
