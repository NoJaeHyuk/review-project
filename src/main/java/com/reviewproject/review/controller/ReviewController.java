package com.reviewproject.review.controller;

import com.reviewproject.review.controller.request.ReviewRequest;
import com.reviewproject.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PatchMapping("/{productId}/reviews")
    public ResponseEntity<Void> addReview(
            @PathVariable Long productId,
            @RequestPart MultipartFile image,
            @RequestPart ReviewRequest reviewRequest
    ) {
        reviewService.addReview(productId, image, reviewRequest);
        return ResponseEntity.ok().build();
    }
}
