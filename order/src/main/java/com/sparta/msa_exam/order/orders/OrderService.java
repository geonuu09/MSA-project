package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.core.Order;
import com.sparta.msa_exam.order.core.OrderProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = Order.createOrder(requestDto.getName());

        for (Long productId : requestDto.getOrderItemIds()) {
            OrderProduct orderProduct = OrderProduct.createOrderProduct(productId, order);
            order.addProduct(orderProduct);
        }
        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));

        return new OrderResponseDto(order);
    }


}
