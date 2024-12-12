package com.reviewproject.review.service;

import com.reviewproject.review.repository.entity.Product;

public interface ProductRepository {
    Product findById(Long productId);
}
