
package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.response.WishlistResponse;
import com.raj.ecommerce.backend.entity.Wishlist;

public class WishlistMapper {

    private WishlistMapper() {
    }

    public static WishlistResponse toResponse(Wishlist wishlist) {

        return WishlistResponse.builder()
                .id(wishlist.getId())
                .productId(wishlist.getProduct().getId())
                .productName(wishlist.getProduct().getName())
                .imageUrl(wishlist.getProduct().getImageUrl())
                .categoryName(
                        wishlist.getProduct()
                                .getCategory()
                                .getName())
                .brandName(
                        wishlist.getProduct()
                                .getBrand()
                                .getName())
                .price(
                        wishlist.getProduct()
                                .getPrice()
                                .toString())
                .discountPrice(
                        wishlist.getProduct()
                                .getDiscountPrice()
                                .toString())
                .createdAt(wishlist.getCreatedAt())
                .build();
    }
}