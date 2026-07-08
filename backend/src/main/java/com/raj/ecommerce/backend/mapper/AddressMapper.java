
package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.request.CreateAddressRequest;
import com.raj.ecommerce.backend.dto.response.AddressResponse;
import com.raj.ecommerce.backend.entity.Address;

public class AddressMapper {

    private AddressMapper() {
    }

    public static Address toEntity(CreateAddressRequest request) {

        return Address.builder()
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .build();
    }

    public static AddressResponse toResponse(Address address) {

        return AddressResponse.builder()
                .id(address.getId())
                .fullName(address.getFullName())
                .phoneNumber(address.getPhoneNumber())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .defaultAddress(address.getDefaultAddress())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }
}