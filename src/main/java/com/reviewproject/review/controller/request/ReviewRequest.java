package com.reviewproject.review.controller.request;

public record ReviewRequest(
    Long userId,
    float score,
    String content
) {

}
