package com.reviewproject.review.service;

import com.reviewproject.common.uploader.ImageUploader;
import com.reviewproject.review.controller.request.ReviewRequest;
import com.reviewproject.review.repository.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

@SpringBootTest
class ReviewServiceConcurrencyTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ImageUploader imageUploader;

    @Autowired
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 등록에 대한 동시성 테스트입니다.")
    void updateScoreConcurrencyTest() throws InterruptedException {
        // Given
        Long productId = createSampleProduct(); // 샘플 Product 생성
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        MultipartFile dummyImage = createDummyImage(); // 테스트용 MultipartFile 생성
        int[] sumScore = {0}; // 총 점수 저장용 배열 (스레드 안전하게 접근 가능)

        // When
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    int randomScore = getRandomScore(1, 5); // 1~5 범위의 랜덤 점수
                    synchronized (sumScore) {
                        sumScore[0] += randomScore; // 총 점수 합산
                    }
                    ReviewRequest reviewRequest = new ReviewRequest(1L, randomScore, "testReview");
                    reviewService.addReview(productId, dummyImage, reviewRequest);
                } catch (Exception e) {
                    System.err.println("Exception occurred: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 작업이 끝날 때까지 대기
        executorService.shutdown();

        // Then
        Product product = productRepository.findById(productId);
        float expectedScore = (float) sumScore[0] / threadCount; // 직접 계산된 평균 점수

        // Product의 최종 평점이 올바른지 검증
        assertThat(product.getScore()).isCloseTo(expectedScore, within(0.001f));
        assertThat(product.getReviewCount()).isEqualTo(threadCount);
    }

    private Long createSampleProduct() {
        Product product = Product.builder()
                .reviewCount(0L)
                .score(0F)
                .build();
        productRepository.save(product);
        return product.getId();
    }

    private MultipartFile createDummyImage() {
        return new MockMultipartFile(
                "image",
                "dummy.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Dummy Image Content".getBytes()
        );
    }

    private int getRandomScore(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
