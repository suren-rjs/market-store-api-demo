package com.market.store.repository.data;

import com.market.store.model.document.ProductCategory;

import java.util.Optional;

@SuppressWarnings("unused")
public interface ProductCategoryDtoRepository {
    void save(ProductCategory productCategory);

    void deleteById(String id);

    <S extends ProductCategory> Optional<ProductCategory> getOneById(String id);

    <S extends ProductCategory> ProductCategory update(ProductCategory productCategory);
}
