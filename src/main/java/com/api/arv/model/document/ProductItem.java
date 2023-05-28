package com.api.arv.model.document;

import com.api.arv.model.enums.ProductDiscountType;
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
    private Integer quantity;
    private Integer stock;
    private ProductDiscountType discountType;
    private Integer discountInPercentage;
    private Integer discountInPrice;
}
