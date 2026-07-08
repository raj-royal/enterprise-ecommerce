

package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.request.PlaceOrderRequest;
import com.raj.ecommerce.backend.dto.response.OrderResponse;
import com.raj.ecommerce.backend.entity.*;
import com.raj.ecommerce.backend.enums.OrderStatus;
import com.raj.ecommerce.backend.exception.ResourceNotFoundException;
import com.raj.ecommerce.backend.mapper.OrderMapper;
import com.raj.ecommerce.backend.repository.*;
import com.raj.ecommerce.backend.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final AddressRepository addressRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;


    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private Cart getCurrentCart(User user) {

        return cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));
    }


    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        User user = getCurrentUser();

        Cart cart = getCurrentCart(user);

        if (cart.getCartItems().isEmpty()) {

            throw new RuntimeException("Cart is empty");
        }

        Address address = addressRepository
                .findById(request.getAddressId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        Order order = Order.builder()
                .user(user)
                .address(address)
                .status(OrderStatus.PENDING)
                .build();

        createOrderItems(cart, order);

        calculateTotals(order);

        Order savedOrder = orderRepository.save(order);

        clearCart(cart);

        return OrderMapper.toResponse(savedOrder);
    }

    private void createOrderItems(Cart cart,
                                  Order order) {

        for (CartItem cartItem : cart.getCartItems()) {

            Product product = cartItem.getProduct();

            if (product.getQuantity() < cartItem.getQuantity()) {

                throw new RuntimeException(
                        product.getName() + " is out of stock");
            }

            product.setQuantity(
                    product.getQuantity()
                            - cartItem.getQuantity());

            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getPrice())
                    .totalPrice(
                            cartItem.getPrice().multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity())))
                    .build();

            order.getOrderItems().add(orderItem);
        }
    }

    private void calculateTotals(Order order) {

        BigDecimal total = BigDecimal.ZERO;

        int items = 0;

        for (OrderItem item : order.getOrderItems()) {

            total = total.add(item.getTotalPrice());

            items += item.getQuantity();
        }

        order.setTotalAmount(total);

        order.setTotalItems(items);
    }

    private void clearCart(Cart cart) {

        cartItemRepository.deleteAll(cart.getCartItems());

        cart.getCartItems().clear();

        cart.setTotalItems(0);

        cart.setTotalPrice(BigDecimal.ZERO);

        cartRepository.save(cart);
    }

    @Override
    public List<OrderResponse> getMyOrders() {

        User user = getCurrentUser();

        return orderRepository.findByUser(user)
                .stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {

        User user = getCurrentUser();

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to view this order");
        }

        return OrderMapper.toResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(Long id) {

        User user = getCurrentUser();

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to cancel this order");
        }

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be cancelled");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }

        // Restore Stock

        for (OrderItem item : order.getOrderItems()) {

            Product product = item.getProduct();

            product.setQuantity(
                    product.getQuantity() + item.getQuantity()
            );

            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);

        Order updatedOrder = orderRepository.save(order);

        return OrderMapper.toResponse(updatedOrder);
    }
}