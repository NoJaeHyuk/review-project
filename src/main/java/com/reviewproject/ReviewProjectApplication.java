package com.reviewproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ReviewProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewProjectApplication.class, args);
    }

}
