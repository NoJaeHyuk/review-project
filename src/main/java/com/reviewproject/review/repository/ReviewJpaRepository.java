package com.reviewproject.review.repository;

import com.reviewproject.review.repository.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
