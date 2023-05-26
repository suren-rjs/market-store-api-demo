package com.market.store.service;

import com.market.store.helper.GlobalCommonService;
import com.market.store.model.document.User;
import com.market.store.model.document.WishlistProducts;
import com.market.store.repository.crud.WishlistRepository;
import com.market.store.repository.data.WishlistDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("All")
@Service
public class WishlistService implements WishlistDtoRepository {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private GlobalCommonService globalCommonService;

    @Override
    public <S extends WishlistProducts> List<S> findAllByCustomerId() {
        User currentUser = globalCommonService.getCurrentUser();
        return wishlistRepository.findAllByCustomerId(currentUser.getId());
    }

    public <S extends String> String toggleWishlist(String productId) {
        User currentUser = globalCommonService.getCurrentUser();

        if (isAlreadyAdded(productId, currentUser.getId())) {
            return addToWishlist(productId, currentUser.getId());
        }

        return removeFromWishlist(productId);
    }

    @Override
    public String addToWishlist(String productId, String customerId) {
        WishlistProducts wishlistProducts = new WishlistProducts();
        wishlistProducts.setProductId(productId);
        wishlistProducts.setCustomerId(customerId);
        wishlistRepository.save(wishlistProducts);
        return "Product added to wishlist";
    }

    @Override
    public String removeFromWishlist(String id) {
        wishlistRepository.deleteById(id);
        return "Product removed from wishlist";
    }

    private boolean isAlreadyAdded(String productId, String customerId) {
        return wishlistRepository.findOneByProductIdAndCustomerId(productId, customerId).isPresent();
    }
}
