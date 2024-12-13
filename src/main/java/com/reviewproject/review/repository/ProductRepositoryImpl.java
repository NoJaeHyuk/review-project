package com.reviewproject.review.repository;

import com.reviewproject.review.repository.entity.Product;
import com.reviewproject.review.service.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository jpaRepository;

    @Override
    public Product findById(Long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품이 존재하지 않습니다."));
    }

    @Override
    public Product findByIdWithLock(Long productId) {
        return jpaRepository.findByIdWithLock(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 제품이 존재하지 않습니다."));
    }

    @Override
    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public void flush() {
        jpaRepository.flush();
    }
}
