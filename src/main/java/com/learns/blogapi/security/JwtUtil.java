package com.learns.blogapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component

// A JWT typically stores:

// header → algorithm + token type
// payload → subject (email/username), issued time, expiry time
// signature → proves token is genuine

// JwtUtil = creates + validates JWT token
public class JwtUtil {

    // Secret key used to sign and verify JWTs
    @Value("${jwt.secret}")
    private String secret;

    // JWT expiration time in milliseconds
    @Value("${jwt.expiration}")
    private long expiration;

    // Returns a cryptographic key used to sign and verify JWTs
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Creates a signed JWT with email as the subject
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    // Extracts the user identity stored inside the token
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload() 
                .getSubject();
    }

    // Verifies signature and expiry to ensure token is usable
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            // token is invalid, expired, or tampered
            return false;
        }
    }
}