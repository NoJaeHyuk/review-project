package com.reviewproject.review.controller.response;

import com.reviewproject.review.repository.entity.Review;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReviewResponse(
        Long totalCount,
        Float score,
        Long cursor,
        List<ReviewDto> reviews
) {
    public static ReviewResponse from(List<Review> reviews, Long totalCount, Float averageScore, Long nextCursor) {
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(ReviewDto::from) // Review -> ReviewDto 변환
                .toList();

        return ReviewResponse.builder()
                .totalCount(totalCount)
                .score(averageScore)
                .cursor(nextCursor)
                .reviews(reviewDtos)
                .build();
    }

    @Builder
    public record ReviewDto(
            Long id,
            Long userId,
            Float score,
            String content,
            String imageUrl,
            LocalDateTime createdAt
    ) {
        public static ReviewDto from(Review review) {
            return ReviewDto.builder()
                    .id(review.getId())
                    .userId(review.getUserId())
                    .score(review.getScore())
                    .content(review.getContent())
                    .imageUrl(review.getImageUrl())
                    .createdAt(review.getCreatedAt())
                    .build();
        }
    }
}
