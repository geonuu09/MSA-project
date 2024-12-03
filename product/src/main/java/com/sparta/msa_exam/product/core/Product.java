package com.sparta.msa_exam.product.core;


import com.sparta.msa_exam.product.products.ProductRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int supply_price;

    public static Product createProduct(ProductRequestDto requestDto) {
        return Product.builder()
                .name(requestDto.getName())
                .supply_price(requestDto.getSupply_price())
                .build();
    }

}
