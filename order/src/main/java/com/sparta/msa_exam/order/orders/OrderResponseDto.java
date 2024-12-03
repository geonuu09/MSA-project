package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.Order;
import com.sparta.msa_exam.order.core.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private List<Long> product_ids = new ArrayList<>();

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrder_id();
        for(OrderProduct orderProduct : order.getProduct_ids()){
            product_ids.add(orderProduct.getProduct_id());
        }
    }
}
