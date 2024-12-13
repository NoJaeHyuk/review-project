package com.reviewproject.common.uploader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DummyImageUploader implements ImageUploader {

    @Override
    public String upload(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            // null이거나 비어있을 경우 기본 이미지를 반환
            return "https://dummy-s3-url.com/default-image.png";
        }

        // 실제 업로드 없이 더미 URL 반환
        String fileName = image.getOriginalFilename();
        return "https://dummy-s3-url.com/" + fileName;
    }
}
