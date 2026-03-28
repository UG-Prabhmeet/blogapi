package com.learns.blogapi.service;

import com.learns.blogapi.dto.AuthResponse;
import com.learns.blogapi.dto.LoginRequest;
import com.learns.blogapi.dto.RegisterRequest;
import com.learns.blogapi.model.Role;
import com.learns.blogapi.model.User;
import com.learns.blogapi.repository.UserRepository;
import com.learns.blogapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        // always hash the password before saving — never store plain text
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        // generate and return a token immediately after registration
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        // authenticate throws an exception if credentials are wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // if we reach here, credentials were correct — generate token
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}