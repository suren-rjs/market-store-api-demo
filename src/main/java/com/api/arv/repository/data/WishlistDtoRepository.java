package com.api.arv.repository.data;

import com.api.arv.model.document.WishlistProducts;

import java.util.List;

public interface WishlistDtoRepository {
    <S extends WishlistProducts> List<S> findAllByCustomerId();

    String addToWishlist(String productId, String customerId);

    String removeFromWishlist(String id);
}
