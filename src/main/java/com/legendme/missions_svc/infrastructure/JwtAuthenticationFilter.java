package com.legendme.missions_svc.infrastructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.function.Function;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Function<String,String> validateFn;

    public JwtAuthenticationFilter(Function<String, String> validateFn) {
        this.validateFn = validateFn;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                String subject = validateFn.apply(token); // lanza RuntimeException si inválido
                var authToken = new UsernamePasswordAuthenticationToken(subject, token, null);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (RuntimeException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
