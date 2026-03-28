package com.learns.blogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication

// DTO = Data Transfer Object
// used to transfer only the required data between layers or in API responses
// while hiding unnecessary or sensitive fields from entities

// Ensures paginated responses are serialized in a cleaner DTO-based format
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)

public class BlogapiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogapiApplication.class, args);
    }
}
