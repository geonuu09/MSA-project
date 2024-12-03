package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.createProduct(requestDto);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}