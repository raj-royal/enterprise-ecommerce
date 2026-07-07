


package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.request.ChangePasswordRequest;
import com.raj.ecommerce.backend.dto.request.LoginRequest;
import com.raj.ecommerce.backend.dto.request.RegisterRequest;
import com.raj.ecommerce.backend.dto.request.UpdateUserRequest;
import com.raj.ecommerce.backend.dto.response.AuthResponse;
import com.raj.ecommerce.backend.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse getCurrentUser();

    UserResponse updateCurrentUser(UpdateUserRequest request);

    UserResponse changePassword(ChangePasswordRequest request);




}