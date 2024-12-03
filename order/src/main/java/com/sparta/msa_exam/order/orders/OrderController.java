package com.sparta.msa_exam.order.orders;


import com.sparta.msa_exam.order.client.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public OrderResponseDto updateOrder(@PathVariable("orderId") Long orderId, @RequestBody ProductRequestDto requestDto) {
        return orderService.updateOrder(orderId, requestDto);
    }
}
