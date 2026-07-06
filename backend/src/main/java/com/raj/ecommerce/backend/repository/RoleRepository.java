

package com.raj.ecommerce.backend.repository;

import com.raj.ecommerce.backend.entity.Role;
import com.raj.ecommerce.backend.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);
}