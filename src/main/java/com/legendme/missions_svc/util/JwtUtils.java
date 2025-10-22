package com.legendme.missions_svc.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtUtils {

    private final String secret;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String validateTokenAndGetSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public UUID extractUserIdFromAuthorizationHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header missing or invalid");
        }
        String token = authHeader.substring(7);
        String subj = validateTokenAndGetSubject(token);
        return UUID.fromString(subj);
    }
}

