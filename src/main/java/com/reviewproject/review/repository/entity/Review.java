package com.reviewproject.review.repository.entity;

import com.reviewproject.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long userId;

    private float score;

    private String content;

    private String imageUrl;

    @Builder
    private Review(Product product, Long userId, float score, String content, String imageUrl) {
        this.product = product;
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public static Review create(Product product, Long userId, float score, String content, String imageUrl) {
        return Review.builder()
                .product(product)
                .userId(userId)
                .score(score)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}
