package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.request.RegisterRequest;
import com.raj.ecommerce.backend.dto.response.UserResponse;
import com.raj.ecommerce.backend.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegisterRequest request) {

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());

        return user;
    }

    public static UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }
}