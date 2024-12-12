package com.reviewproject.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reviewproject.review.repository.entity.Review;
import com.reviewproject.review.service.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.reviewproject.review.repository.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewJpaRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public void save(Review review) {
        repository.save(review);
    }

    @Override
    public List<Review> findByProductIdWithCursor(Long productId, Long cursor, int size) {
        return queryFactory
                .selectFrom(review)
                .where(
                        review.product.id.eq(productId),
                        cursor != null ? review.id.lt(cursor) : null
                )
                .orderBy(review.id.desc())
                .limit(size)
                .fetch();
    }
}
