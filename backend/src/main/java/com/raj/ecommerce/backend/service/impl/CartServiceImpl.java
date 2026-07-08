

package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.request.AddToCartRequest;
import com.raj.ecommerce.backend.dto.response.CartResponse;
import com.raj.ecommerce.backend.entity.Cart;
import com.raj.ecommerce.backend.entity.CartItem;
import com.raj.ecommerce.backend.entity.Product;
import com.raj.ecommerce.backend.entity.User;
import com.raj.ecommerce.backend.exception.ResourceNotFoundException;
import com.raj.ecommerce.backend.mapper.CartMapper;
import com.raj.ecommerce.backend.repository.CartItemRepository;
import com.raj.ecommerce.backend.repository.CartRepository;
import com.raj.ecommerce.backend.repository.ProductRepository;
import com.raj.ecommerce.backend.repository.UserRepository;
import com.raj.ecommerce.backend.service.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private Cart getCart(User user) {

        return cartRepository.findByUser(user)
                .orElseGet(() -> {

                    Cart cart = Cart.builder()
                            .user(user)
                            .build();

                    return cartRepository.save(cart);
                });
    }

    private void calculateTotals(Cart cart) {

        BigDecimal total = BigDecimal.ZERO;

        int items = 0;

        for (CartItem item : cart.getCartItems()) {

            total = total.add(
                    item.getPrice().multiply(
                            BigDecimal.valueOf(item.getQuantity())
                    )
            );

            items += item.getQuantity();
        }

        cart.setTotalItems(items);

        cart.setTotalPrice(total);
    }

    @Override
    public CartResponse addToCart(AddToCartRequest request) {

        User user = getCurrentUser();

        Cart cart = getCart(user);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        CartItem item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (item == null) {

            item = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .price(product.getDiscountPrice())
                    .build();

            cart.getCartItems().add(item);

        } else {

            item.setQuantity(
                    item.getQuantity() + request.getQuantity()
            );
        }

        calculateTotals(cart);

        cartRepository.save(cart);

        return CartMapper.toResponse(cart);
    }

    @Override
    public CartResponse getMyCart() {

        User user = getCurrentUser();

        Cart cart = getCart(user);

        calculateTotals(cart);

        cartRepository.save(cart);

        return CartMapper.toResponse(cart);
    }

    @Override
    public CartResponse updateQuantity(Long productId,
                                       Integer quantity) {

        User user = getCurrentUser();

        Cart cart = getCart(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        CartItem item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        item.setQuantity(quantity);

        calculateTotals(cart);

        cartRepository.save(cart);

        return CartMapper.toResponse(cart);
    }

    @Override
    public void removeProduct(Long productId) {

        User user = getCurrentUser();

        Cart cart = getCart(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        CartItem item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        cart.getCartItems().remove(item);

        cartItemRepository.delete(item);

        calculateTotals(cart);

        cartRepository.save(cart);
    }

    @Override
    public void clearCart() {

        User user = getCurrentUser();

        Cart cart = getCart(user);

        cart.getCartItems().clear();

        cart.setTotalItems(0);

        cart.setTotalPrice(BigDecimal.ZERO);

        cartRepository.save(cart);
    }

}