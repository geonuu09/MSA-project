package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @CachePut(cacheNames = "productCache", key = "#result.product_id")
    @CacheEvict(cacheNames = "productAllCache", allEntries = true)
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.createProduct(requestDto);
        return ProductResponseDto.fromEntity(productRepository.save(product));
    }

    @Cacheable(cacheNames = "productAllCache", key = "methodName")
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponseDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."));
    }
}
