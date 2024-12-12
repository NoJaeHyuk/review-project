package com.reviewproject.review.repository;

import com.reviewproject.review.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
