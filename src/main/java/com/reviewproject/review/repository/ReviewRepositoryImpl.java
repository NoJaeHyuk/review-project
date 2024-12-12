package com.reviewproject.review.repository;

import com.reviewproject.review.repository.entity.Review;
import com.reviewproject.review.service.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewJpaRepository repository;

    @Override
    public void save(Review review) {
        repository.save(review);
    }
}
