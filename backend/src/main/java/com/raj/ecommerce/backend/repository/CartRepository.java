

package com.raj.ecommerce.backend.repository;

import com.raj.ecommerce.backend.entity.Cart;
import com.raj.ecommerce.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUser(User user);

}