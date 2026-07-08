

package com.raj.ecommerce.backend.repository;

import com.raj.ecommerce.backend.entity.Cart;
import com.raj.ecommerce.backend.entity.CartItem;
import com.raj.ecommerce.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    Optional<CartItem> findByCartAndProduct(
            Cart cart,
            Product product
    );

}