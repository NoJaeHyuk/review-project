package com.reviewproject.review.repository.entity;

import com.reviewproject.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reviewCount;

    private float score;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

    @Builder
    private Product(Long reviewCount, float score, List<Review> reviews) {
        this.reviewCount = reviewCount;
        this.score = score;
        this.reviews = reviews;
    }

    public void updateScore(float newScore) {
        this.score = ((this.score * this.reviewCount) + newScore) / (this.reviewCount + 1);
        this.reviewCount++;
    }
}
