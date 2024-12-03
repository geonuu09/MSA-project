package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}