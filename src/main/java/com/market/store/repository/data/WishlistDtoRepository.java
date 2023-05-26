package com.market.store.repository.data;

import com.market.store.model.document.WishlistProducts;

import java.util.List;

public interface WishlistDtoRepository {
    <S extends WishlistProducts> List<S> findAllByCustomerId();

    String addToWishlist(String productId, String customerId);

    String removeFromWishlist(String id);
}
