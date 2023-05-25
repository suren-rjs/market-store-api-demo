package com.market.store.repository.crud;

import com.market.store.model.document.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String> {
    <S extends ProductCategory> Optional<S> findOneById(String id);
}
