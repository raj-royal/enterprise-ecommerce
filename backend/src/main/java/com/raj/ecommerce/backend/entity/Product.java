
package com.raj.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @Column(precision = 10,scale = 2)
    private BigDecimal discountPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false,unique = true)
    private String sku;

    private String imageUrl;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean featured = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id",nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private Set<Wishlist> wishlistItems = new HashSet<>();
}