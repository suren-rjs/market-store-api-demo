package com.market.store.model.dto.request.product;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductCategoryDto {
    private String id;
    @NotNull
    private String name;
    private String image;
}
