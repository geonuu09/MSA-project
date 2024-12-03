package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductClient productClient;
    public String getProductInfo(String productId) {
        return productClient.getProduct(productId);
    }
    public String getOrder(String orderId) {
        String productInfo = getProductInfo(orderId);
        return "Your order is " + productInfo;
    }
}