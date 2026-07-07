

package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.request.LoginRequest;
import com.raj.ecommerce.backend.dto.request.RegisterRequest;
import com.raj.ecommerce.backend.dto.response.AuthResponse;
import com.raj.ecommerce.backend.dto.response.UserResponse;
import com.raj.ecommerce.backend.entity.Role;
import com.raj.ecommerce.backend.entity.User;
import com.raj.ecommerce.backend.enums.RoleType;
import com.raj.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.raj.ecommerce.backend.exception.ResourceNotFoundException;
import com.raj.ecommerce.backend.mapper.UserMapper;
import com.raj.ecommerce.backend.repository.RoleRepository;
import com.raj.ecommerce.backend.repository.UserRepository;
import com.raj.ecommerce.backend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResourceAlreadyExistsException("Phone number already exists");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role customerRole = roleRepository.findByName(RoleType.ROLE_CUSTOMER)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ROLE_CUSTOMER not found"));

        user.getRoles().add(customerRole);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        return null;
    }
}