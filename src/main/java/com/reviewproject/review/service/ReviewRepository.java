package com.reviewproject.review.service;

import com.reviewproject.review.repository.entity.Review;

public interface ReviewRepository {
    void save(Review review);
}
