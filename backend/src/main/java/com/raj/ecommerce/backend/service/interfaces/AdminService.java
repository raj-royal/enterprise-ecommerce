package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.response.AdminUserResponse;
import com.raj.ecommerce.backend.dto.response.OrderResponse;

import java.util.List;

public interface AdminService {

    List<AdminUserResponse> getAllUsers();

    AdminUserResponse getUserById(Long id);

    AdminUserResponse enableUser(Long id);

    AdminUserResponse disableUser(Long id);

    AdminUserResponse makeAdmin(Long id);

    AdminUserResponse removeAdmin(Long id);

    void deleteUser(Long id);


    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse confirmOrder(Long id);

    OrderResponse shipOrder(Long id);

    OrderResponse deliverOrder(Long id);

    OrderResponse cancelOrder(Long id);
}