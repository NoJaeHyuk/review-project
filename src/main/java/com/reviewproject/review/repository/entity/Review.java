package com.reviewproject.review.repository.entity;

import com.reviewproject.common.BaseTimeEntity;
import jakarta.persistence.*;

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

    private String imageUrl;
}
