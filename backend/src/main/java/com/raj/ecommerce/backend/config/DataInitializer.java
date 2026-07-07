package com.raj.ecommerce.backend.config;

import com.raj.ecommerce.backend.enums.RoleType;
import com.raj.ecommerce.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.raj.ecommerce.backend.entity.Role;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        if (!roleRepository.existsByName(RoleType.ROLE_ADMIN)) {
            roleRepository.save(Role.builder()
                    .name(RoleType.ROLE_ADMIN)
                    .build());
        }

        if (!roleRepository.existsByName(RoleType.ROLE_CUSTOMER)) {
            roleRepository.save(Role.builder()
                    .name(RoleType.ROLE_CUSTOMER)
                    .build());
        }
    }
}