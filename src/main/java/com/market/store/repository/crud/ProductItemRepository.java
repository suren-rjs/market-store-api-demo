package com.market.store.repository.crud;

import com.market.store.model.document.ProductItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@SuppressWarnings("unused")
public interface ProductItemRepository extends MongoRepository<ProductItem, String> {
    <S extends ProductItem> Optional<S> findOneById(String id);
}
