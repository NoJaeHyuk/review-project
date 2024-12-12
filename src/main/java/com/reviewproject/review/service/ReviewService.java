package com.reviewproject.review.service;

import com.reviewproject.review.controller.request.ReviewRequest;
import com.reviewproject.review.controller.response.ReviewResponse;
import com.reviewproject.review.repository.entity.Product;
import com.reviewproject.review.repository.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void addReview(Long productId, MultipartFile image, ReviewRequest reviewRequest) {
        // 1. 제품 존재 확인
        Product product = productRepository.findById(productId);

        // 2. 리뷰 저장
        Review review = Review.create(
                product,
                reviewRequest.userId(),
                reviewRequest.score(),
                reviewRequest.content(),
                saveImage(image)
        );

        reviewRepository.save(review);

        // 3. 제품의 평점 업데이트
        product.updateScore(reviewRequest.score());
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReviews(Long productId, Long cursor, int size) {
        List<Review> reviews = reviewRepository.findByProductIdWithCursor(productId, cursor, size);
        Long nextCursor = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();
        Float averageScore = calculateAverageScore(reviews);

        return ReviewResponse.from(reviews, (long) reviews.size(), averageScore, nextCursor);
    }

    private String saveImage(MultipartFile image) {
        // TODO: 이미지 저장 로직 구현
        return "";
    }

    private Float calculateAverageScore(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return null;
        }
        double totalScore = reviews.stream()
                .mapToDouble(Review::getScore)
                .sum();
        return (float) (totalScore / reviews.size());
    }
}
