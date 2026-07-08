
package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.request.CreateAddressRequest;
import com.raj.ecommerce.backend.dto.request.UpdateAddressRequest;
import com.raj.ecommerce.backend.dto.response.AddressResponse;
import com.raj.ecommerce.backend.entity.Address;
import com.raj.ecommerce.backend.entity.User;
import com.raj.ecommerce.backend.exception.ResourceNotFoundException;
import com.raj.ecommerce.backend.mapper.AddressMapper;
import com.raj.ecommerce.backend.repository.AddressRepository;
import com.raj.ecommerce.backend.repository.UserRepository;
import com.raj.ecommerce.backend.service.interfaces.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    @Override
    public AddressResponse createAddress(CreateAddressRequest request) {

        User user = getCurrentUser();

        Address address = AddressMapper.toEntity(request);

        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        return AddressMapper.toResponse(savedAddress);
    }

    @Override
    public List<AddressResponse> getMyAddresses() {

        User user = getCurrentUser();

        return addressRepository.findByUser(user)
                .stream()
                .map(AddressMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse getAddressById(Long id) {

        User user = getCurrentUser();

        Address address = addressRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        return AddressMapper.toResponse(address);
    }

    @Override
    public AddressResponse updateAddress(Long id,
                                         UpdateAddressRequest request) {

        User user = getCurrentUser();

        Address address = addressRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        address.setFullName(request.getFullName());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        Address updatedAddress = addressRepository.save(address);

        return AddressMapper.toResponse(updatedAddress);
    }

    @Override
    public AddressResponse setDefaultAddress(Long id) {

        User user = getCurrentUser();

        List<Address> addresses = addressRepository.findByUser(user);

        for (Address address : addresses) {
            address.setDefaultAddress(false);
        }

        Address address = addressRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        address.setDefaultAddress(true);

        addressRepository.saveAll(addresses);

        return AddressMapper.toResponse(address);
    }

    @Override
    public void deleteAddress(Long id) {

        User user = getCurrentUser();

        Address address = addressRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        addressRepository.delete(address);
    }
}