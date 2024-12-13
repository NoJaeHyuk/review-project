package com.reviewproject.common.uploader;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    String upload(MultipartFile image);
}
