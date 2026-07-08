
package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.request.AddToCartRequest;
import com.raj.ecommerce.backend.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(AddToCartRequest request);

    CartResponse getMyCart();

    CartResponse updateQuantity(
            Long productId,
            Integer quantity);

    void removeProduct(Long productId);

    void clearCart();

}

