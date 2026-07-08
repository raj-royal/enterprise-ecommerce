package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.request.AddToCartRequest;
import com.raj.ecommerce.backend.dto.response.WishlistResponse;
import com.raj.ecommerce.backend.entity.Product;
import com.raj.ecommerce.backend.entity.User;
import com.raj.ecommerce.backend.entity.Wishlist;
import com.raj.ecommerce.backend.exception.ResourceNotFoundException;
import com.raj.ecommerce.backend.mapper.WishlistMapper;
import com.raj.ecommerce.backend.repository.ProductRepository;
import com.raj.ecommerce.backend.repository.UserRepository;
import com.raj.ecommerce.backend.repository.WishlistRepository;
import com.raj.ecommerce.backend.service.interfaces.CartService;
import com.raj.ecommerce.backend.service.interfaces.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    @Override
    public WishlistResponse addToWishlist(Long productId) {

        User user = getCurrentUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        wishlistRepository.findByUserAndProduct(user, product)
                .ifPresent(wishlist -> {
                    throw new RuntimeException("Product already exists in wishlist");
                });

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return WishlistMapper.toResponse(savedWishlist);
    }

    @Override
    public List<WishlistResponse> getMyWishlist() {

        User user = getCurrentUser();

        return wishlistRepository.findByUser(user)
                .stream()
                .map(WishlistMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void removeFromWishlist(Long productId) {

        User user = getCurrentUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        Wishlist wishlist = wishlistRepository.findByUserAndProduct(user, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wishlist item not found"));

        wishlistRepository.delete(wishlist);
    }

    @Override
    public void moveToCart(Long productId) {

        addProductToCart(productId);

        removeFromWishlist(productId);
    }

    private void addProductToCart(Long productId) {

        AddToCartRequest request = AddToCartRequest.builder()
                .productId(productId)
                .quantity(1)
                .build();

        cartService.addToCart(request);
    }
}
