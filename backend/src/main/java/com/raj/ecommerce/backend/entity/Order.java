
package com.raj.ecommerce.backend.entity;

import com.raj.ecommerce.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private Integer totalItems;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Builder.Default
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OrderItem> orderItems = new HashSet<>();
}