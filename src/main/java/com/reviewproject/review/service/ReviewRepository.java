package com.reviewproject.review.service;

import com.reviewproject.review.repository.entity.Review;

import java.util.List;

public interface ReviewRepository {
    void save(Review review);
    List<Review> findByProductIdWithCursor(Long productId, Long cursor, int size);
}
