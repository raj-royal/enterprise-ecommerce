
package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.response.AdminUserResponse;
import com.raj.ecommerce.backend.dto.response.OrderResponse;
import com.raj.ecommerce.backend.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String dashboard() {
        return "Welcome Admin!";
    }



    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserResponse> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PutMapping("/users/{id}/enable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserResponse> enableUser(@PathVariable Long id) {

        return ResponseEntity.ok(adminService.enableUser(id));
    }


    @PutMapping("/users/{id}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserResponse> disableUser(@PathVariable Long id) {

        return ResponseEntity.ok(adminService.disableUser(id));
    }

    @PutMapping("/users/{id}/make-admin")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserResponse> makeAdmin(@PathVariable Long id) {

        return ResponseEntity.ok(adminService.makeAdmin(id));
    }

    @PutMapping("/users/{id}/remove-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserResponse> removeAdmin(@PathVariable Long id) {

        return ResponseEntity.ok(adminService.removeAdmin(id));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        adminService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");
    }

//     for admin
//    get all orders
    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        return ResponseEntity.ok(adminService.getAllOrders());
    }

//    get order by id

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getOrderById(id));
    }

//    confirm order
    @PutMapping("/orders/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponse> confirmOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.confirmOrder(id));
    }

//    ship order
@PutMapping("/orders/{id}/ship")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<OrderResponse> shipOrder(
        @PathVariable Long id) {

    return ResponseEntity.ok(
            adminService.shipOrder(id));
}

//deliver order
@PutMapping("/orders/{id}/deliver")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<OrderResponse> deliverOrder(
        @PathVariable Long id) {

    return ResponseEntity.ok(
            adminService.deliverOrder(id));
}

//  cancel order
@PutMapping("/orders/{id}/cancel")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<OrderResponse> cancelOrder(
        @PathVariable Long id) {

    return ResponseEntity.ok(
            adminService.cancelOrder(id));
}
}