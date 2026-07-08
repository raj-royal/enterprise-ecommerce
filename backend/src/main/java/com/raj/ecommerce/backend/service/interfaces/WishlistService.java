
package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.response.WishlistResponse;

import java.util.List;

public interface WishlistService {

    WishlistResponse addToWishlist(Long productId);

    List<WishlistResponse> getMyWishlist();

    void removeFromWishlist(Long productId);

    void moveToCart(Long productId);

}