package com.sparta.msa_exam.order.orders;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/order/{orderId}")
    public String order(@PathVariable("orderId") String orderId) {
        return orderService.getOrder(orderId);
    }
}