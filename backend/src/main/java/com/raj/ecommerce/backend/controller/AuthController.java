

package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.request.LoginRequest;
import com.raj.ecommerce.backend.dto.request.RegisterRequest;
import com.raj.ecommerce.backend.dto.response.AuthResponse;
import com.raj.ecommerce.backend.dto.response.UserResponse;
import com.raj.ecommerce.backend.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        return userService.login(request);
    }
}