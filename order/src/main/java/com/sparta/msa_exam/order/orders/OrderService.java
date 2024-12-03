package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.core.Order;
import com.sparta.msa_exam.order.core.OrderProduct;
import com.sparta.msa_exam.product.products.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = Order.createOrder(requestDto.getName());

        for (Long productId : requestDto.getOrderItemIds()) {
            OrderProduct orderProduct = OrderProduct.createOrderProduct(productId, order);
            order.addProduct(orderProduct);
        }

        return OrderResponseDto.fromEntity(orderRepository.save(order));
    }

    @Cacheable(cacheNames = "orderCache", key = "args[0]")
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponseDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));
    }

    @Transactional
    public OrderResponseDto updateOrder(Long orderId, ProductRequestDto requestDto) {
        try{
            Long productId = requestDto.getProduct_id();
            //상품목록조회
            productClient.getProduct(productId);

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));
            List<OrderProduct> productList = order.getProduct_ids();

            productList.add(OrderProduct.createOrderProduct(productId, order));

            return OrderResponseDto.fromEntity(orderRepository.save(order));
        } catch(ResponseStatusException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다.");
            } else {
                throw e;
            }
        }
    }

}