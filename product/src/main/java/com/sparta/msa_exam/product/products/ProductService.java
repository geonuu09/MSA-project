package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."));
        return new ProductResponseDto(product);
    }

    @Transactional
    public void reduceProductQuantity(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough quantity for product ID: " + productId);
        }

        product.reduceQuantity(quantity);
        productRepository.save(product);
    }
}
