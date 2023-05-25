package com.market.store.model.document;

import com.market.store.model.enums.ProductDiscountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("product-items")
public class ProductItem {
    @Id
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
