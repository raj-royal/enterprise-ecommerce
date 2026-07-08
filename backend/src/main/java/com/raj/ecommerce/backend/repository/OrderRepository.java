
package com.raj.ecommerce.backend.repository;

import com.raj.ecommerce.backend.entity.Order;
import com.raj.ecommerce.backend.entity.User;
import com.raj.ecommerce.backend.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);

}