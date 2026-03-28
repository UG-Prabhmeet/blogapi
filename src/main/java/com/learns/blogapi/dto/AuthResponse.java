package com.learns.blogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

// AuthResponse = Data Transfer Object (DTO) for returning JWT tokens after successful login
public class AuthResponse {
    private String token;
}
