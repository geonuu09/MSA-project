package com.sparta.msa_exam.order.core;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    private String name;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> product_ids = new ArrayList<>();

    public static Order createOrder(String name){
        return Order.builder()
                .name(name)
                .product_ids(new ArrayList<>())
                .build();
    }

    public void addProduct(OrderProduct orderProduct) {
        product_ids.add(orderProduct);
    }
}