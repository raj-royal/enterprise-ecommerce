
package com.raj.ecommerce.backend.repository;

import com.raj.ecommerce.backend.entity.Product;
import com.raj.ecommerce.backend.entity.User;
import com.raj.ecommerce.backend.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    List<Wishlist> findByUser(User user);

    Optional<Wishlist> findByUserAndProduct(
            User user,
            Product product
    );

}