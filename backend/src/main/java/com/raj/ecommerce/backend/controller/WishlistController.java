
package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.response.WishlistResponse;
import com.raj.ecommerce.backend.service.interfaces.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{productId}")
    public ResponseEntity<WishlistResponse> addToWishlist(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                wishlistService.addToWishlist(productId));
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponse>> getMyWishlist() {

        return ResponseEntity.ok(
                wishlistService.getMyWishlist());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromWishlist(
            @PathVariable Long productId) {

        wishlistService.removeFromWishlist(productId);

        return ResponseEntity.ok("Product removed from wishlist");
    }

    @PostMapping("/{productId}/move-to-cart")
    public ResponseEntity<String> moveToCart(
            @PathVariable Long productId) {

        wishlistService.moveToCart(productId);

        return ResponseEntity.ok("Product moved to cart successfully");
    }
}