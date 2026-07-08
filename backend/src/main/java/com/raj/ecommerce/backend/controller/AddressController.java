
package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.request.CreateAddressRequest;
import com.raj.ecommerce.backend.dto.request.UpdateAddressRequest;
import com.raj.ecommerce.backend.dto.response.AddressResponse;
import com.raj.ecommerce.backend.service.interfaces.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @Valid @RequestBody CreateAddressRequest request) {

        return new ResponseEntity<>(
                addressService.createAddress(request),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getMyAddresses() {

        return ResponseEntity.ok(addressService.getMyAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(
            @PathVariable Long id) {

        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAddressRequest request) {

        return ResponseEntity.ok(
                addressService.updateAddress(id, request));
    }

    @PutMapping("/{id}/default")
    public ResponseEntity<AddressResponse> setDefaultAddress(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                addressService.setDefaultAddress(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long id) {

        addressService.deleteAddress(id);

        return ResponseEntity.ok("Address deleted successfully");
    }
}