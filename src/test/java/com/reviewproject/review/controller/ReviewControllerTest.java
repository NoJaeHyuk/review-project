package com.reviewproject.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reviewproject.review.controller.request.ReviewRequest;
import com.reviewproject.review.controller.response.ReviewResponse;
import com.reviewproject.review.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰정보를 받아 리뷰등록을 수행한다.")
    void addReview() throws Exception {
        // Given
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test-image.png",
                MediaType.IMAGE_PNG_VALUE,
                "test image content".getBytes()
        );

        ReviewRequest reviewRequest = new ReviewRequest(1L, 3.0f, "testReview");

        MockMultipartFile reviewRequestPart = new MockMultipartFile(
                "reviewRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(reviewRequest)
        );

        // When
        doNothing().when(reviewService).addReview(any(Long.class), any(MockMultipartFile.class), any(ReviewRequest.class));

        // Then
        mockMvc.perform(multipart("/products/{productId}/reviews", 1L)
                        .file(image)
                        .file(reviewRequestPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 목록을 조회한다.")
    void getReviews() throws Exception {
        // Given
        ReviewResponse reviewResponse = ReviewResponse.builder()
                .totalCount(2L)
                .score(4.0f)
                .cursor(3L)
                .reviews(List.of(
                        ReviewResponse.ReviewDto.builder()
                                .id(1L)
                                .userId(101L)
                                .score(4.5f)
                                .content("Great product")
                                .imageUrl("image-url-1")
                                .createdAt(LocalDateTime.now())
                                .build(),
                        ReviewResponse.ReviewDto.builder()
                                .id(2L)
                                .userId(102L)
                                .score(3.5f)
                                .content("Not bad")
                                .imageUrl("image-url-2")
                                .createdAt(LocalDateTime.now())
                                .build()
                ))
                .build();

        when(reviewService.getReviews(eq(1L), eq(null), eq(10)))
                .thenReturn(reviewResponse);

        // When & Then
        mockMvc.perform(get("/products/{productId}/reviews", 1L)
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(2))
                .andExpect(jsonPath("$.score").value(4.0))
                .andExpect(jsonPath("$.cursor").value(3))
                .andExpect(jsonPath("$.reviews").isArray())
                .andExpect(jsonPath("$.reviews[0].id").value(1))
                .andExpect(jsonPath("$.reviews[0].userId").value(101))
                .andExpect(jsonPath("$.reviews[0].score").value(4.5))
                .andExpect(jsonPath("$.reviews[0].content").value("Great product"))
                .andExpect(jsonPath("$.reviews[0].imageUrl").value("image-url-1"))
                .andExpect(jsonPath("$.reviews[1].id").value(2))
                .andExpect(jsonPath("$.reviews[1].userId").value(102))
                .andExpect(jsonPath("$.reviews[1].score").value(3.5))
                .andExpect(jsonPath("$.reviews[1].content").value("Not bad"))
                .andExpect(jsonPath("$.reviews[1].imageUrl").value("image-url-2"));
    }
}
