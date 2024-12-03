package com.sparta.msa_exam.order.core;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long product_id;

    public static OrderProduct createOrderProduct(Long product_id, Order order) {
        return OrderProduct.builder()
                .product_id(product_id)
                .order(order)
                .build();
    }
}
