

package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.response.AdminUserResponse;
import com.raj.ecommerce.backend.entity.User;
import com.raj.ecommerce.backend.enums.RoleType;
import com.raj.ecommerce.backend.mapper.AdminMapper;
import com.raj.ecommerce.backend.repository.RoleRepository;
import com.raj.ecommerce.backend.repository.UserRepository;
import com.raj.ecommerce.backend.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.raj.ecommerce.backend.entity.Role;
import com.raj.ecommerce.backend.dto.response.OrderResponse;
import com.raj.ecommerce.backend.entity.Order;
import com.raj.ecommerce.backend.entity.OrderItem;
import com.raj.ecommerce.backend.entity.Product;
import com.raj.ecommerce.backend.enums.OrderStatus;
import com.raj.ecommerce.backend.mapper.OrderMapper;
import com.raj.ecommerce.backend.repository.OrderRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<AdminUserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(AdminMapper::toResponse)
                .collect(Collectors.toList());
    }

//     get by user id
@Override
public AdminUserResponse getUserById(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    return AdminMapper.toResponse(user);
}

//   this is for enable user

    @Override
    public AdminUserResponse enableUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setEnabled(true);

        userRepository.save(user);

        return AdminMapper.toResponse(user);
    }

//        this is for disable user

    @Override
    public AdminUserResponse disableUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setEnabled(false);

        userRepository.save(user);

        return AdminMapper.toResponse(user);
    }

//    this is for  to make admin

    @Override
    public AdminUserResponse makeAdmin(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() ->
                        new RuntimeException("Admin role not found"));

        user.getRoles().add(adminRole);

        userRepository.save(user);

        return AdminMapper.toResponse(user);
    }

//     this is for to remove the admin
@Override
public AdminUserResponse removeAdmin(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    user.getRoles().removeIf(role ->
            role.getName() == RoleType.ROLE_ADMIN);

    userRepository.save(user);

    return AdminMapper.toResponse(user);
}

//  this is for to delete the user

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        return OrderMapper.toResponse(order);
    }

    @Override
    public OrderResponse confirmOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be confirmed");
        }

        order.setStatus(OrderStatus.CONFIRMED);

        return OrderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse deliverOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.SHIPPED) {
            throw new RuntimeException("Order must be shipped first");
        }

        order.setStatus(OrderStatus.DELIVERED);

        return OrderMapper.toResponse(orderRepository.save(order));
    }


    @Override
    public OrderResponse cancelOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be cancelled");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }

        for (OrderItem item : order.getOrderItems()) {

            Product product = item.getProduct();

            product.setQuantity(
                    product.getQuantity() + item.getQuantity());

        }

        order.setStatus(OrderStatus.CANCELLED);

        return OrderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse shipOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Order must be confirmed first");
        }

        order.setStatus(OrderStatus.SHIPPED);

        return OrderMapper.toResponse(orderRepository.save(order));
    }


}