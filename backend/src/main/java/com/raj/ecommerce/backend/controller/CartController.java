

package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.request.AddToCartRequest;
import com.raj.ecommerce.backend.dto.response.CartResponse;
import com.raj.ecommerce.backend.service.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            @RequestBody AddToCartRequest request) {

        return ResponseEntity.ok(
                cartService.addToCart(request));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getMyCart() {

        return ResponseEntity.ok(
                cartService.getMyCart());
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<CartResponse> updateQuantity(

            @PathVariable Long productId,

            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                cartService.updateQuantity(
                        productId,
                        quantity));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProduct(
            @PathVariable Long productId) {

        cartService.removeProduct(productId);

        return ResponseEntity.ok("Product removed from cart");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {

        cartService.clearCart();

        return ResponseEntity.ok("Cart cleared successfully");
    }
}