package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.request.ChangePasswordRequest;
import com.raj.ecommerce.backend.dto.request.UpdateUserRequest;
import com.raj.ecommerce.backend.dto.response.UserResponse;
import com.raj.ecommerce.backend.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(
            @Valid @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                userService.updateCurrentUser(request)
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<UserResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        return ResponseEntity.ok(
                userService.changePassword(request)
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteCurrentUser() {

        userService.deleteCurrentUser();

        return ResponseEntity.ok("User account deleted successfully.");
    }
}