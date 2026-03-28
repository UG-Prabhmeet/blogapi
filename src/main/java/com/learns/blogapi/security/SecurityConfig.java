package com.learns.blogapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor

// Enables annotation-based access control like @PreAuthorize
@EnableMethodSecurity

// SecurityConfig = configures Spring Security for JWT-based authentication
// like defining which routes are public, which need authentication, and which
// need specific roles
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean // SecurityFilterChain = defines the security rules for HTTP requests
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // JWT-based APIs do not rely on browser sessions
                .csrf(csrf -> csrf.disable())

                // stateless = no sessions, every request must carry a JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // these endpoints are public — no token needed
                        .requestMatchers("/auth/**").permitAll()

                        // only ADMIN can delete users
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        // All remaining endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Add our JWT filter before Spring's default login filter
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Hashes passwords securely before storing them
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Exposes AuthenticationManager for manual authentication during login
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}