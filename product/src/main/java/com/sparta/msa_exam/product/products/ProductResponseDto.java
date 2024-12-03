package com.sparta.msa_exam.product.products;


import com.sparta.msa_exam.product.core.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements Serializable {
    private Long product_id;
    private String name;
    private int supply_price;
    private int quantity;

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .product_id(product.getProduct_id())
                .name(product.getName())
                .supply_price(product.getSupply_price())
                .quantity(product.getQuantity())
                .build();
    }
}