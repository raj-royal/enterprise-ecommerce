
package com.raj.ecommerce.backend.repository;

import com.raj.ecommerce.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}