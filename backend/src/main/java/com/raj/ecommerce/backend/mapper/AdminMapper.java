

package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.response.AdminUserResponse;
import com.raj.ecommerce.backend.entity.User;

import java.util.stream.Collectors;

public class AdminMapper {

    private AdminMapper() {
    }

    public static AdminUserResponse toResponse(User user) {

        return AdminUserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toSet())
                )
                .build();
    }
}