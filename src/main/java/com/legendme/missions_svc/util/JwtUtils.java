package com.legendme.missions_svc.util;

import com.legendme.missions_svc.shared.exceptions.ErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
/**
 * Utilidad para manejar JSON Web Tokens (JWT).
 */
@Component
public class JwtUtils {

    /** Secreto utilizado para firmar y validar los tokens JWT */
    private final String secret;

    /**
     * Constructor que inyecta el secreto JWT desde las propiedades de la aplicación.
     *
     * @param secret el secreto utilizado para firmar y validar los tokens JWT
     */
    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }
    /**
     * Valida el token JWT y extrae el sujeto (subject).
     *
     * @param token el token JWT a validar
     * @return el sujeto extraído del token
     */
    public String validateTokenAndGetSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Extrae el ID de usuario del encabezado de autorización.
     *
     * @param authHeader el encabezado de autorización que contiene el token JWT
     * @return el ID de usuario extraído del token
     */
    public UUID extractUserIdFromAuthorizationHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ErrorException("Authorization header missing or invalid");
        }
        String token = authHeader.substring(7);
        String subj = validateTokenAndGetSubject(token);
        return UUID.fromString(subj);
    }
}

