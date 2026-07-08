
package com.raj.ecommerce.backend.repository;

import com.raj.ecommerce.backend.entity.Address;
import com.raj.ecommerce.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

    Optional<Address> findByIdAndUser(Long id, User user);
}