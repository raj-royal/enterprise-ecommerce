
package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.request.CreateAddressRequest;
import com.raj.ecommerce.backend.dto.request.UpdateAddressRequest;
import com.raj.ecommerce.backend.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse createAddress(CreateAddressRequest request);

    List<AddressResponse> getMyAddresses();

    AddressResponse getAddressById(Long id);

    AddressResponse updateAddress(Long id, UpdateAddressRequest request);

    AddressResponse setDefaultAddress(Long id);

    void deleteAddress(Long id);

}