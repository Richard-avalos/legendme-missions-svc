package com.legendme.missions_svc.adapters.in.rest.controller;

import com.legendme.missions_svc.application.mapper.CategoryMapper;
import com.legendme.missions_svc.application.port.in.ListCategoriesUseCase;
import com.legendme.missions_svc.domain.model.Category;
import com.legendme.missions_svc.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Este controlador maneja las solicitudes relacionadas con las categorías de misiones.
 * Proporciona un endpoint para listar todas las categorías disponibles en el sistema.
 *
 */
@RestController
@RequestMapping("/legendme/categories")
@RequiredArgsConstructor
public class CategoryController {

    /**
     * Caso de uso para listar categorías.
     */
    private final ListCategoriesUseCase listCategoriesUseCase;
    /**
     * Utilidad para manejar JWT.
     */
    private final JwtUtils jwtUtils;

    /**
     * Maneja las solicitudes GET para listar todas las categorías disponibles.
     *
     * @param authHeader El encabezado de autorización que contiene el token JWT.
     * @return Una respuesta HTTP con la lista de categorías o un error de autorización.
     */
    @GetMapping
    public ResponseEntity<List<?>> listCategories(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || authHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(List.of("Missing Authorization header"));
        }

        jwtUtils.extractUserIdFromAuthorizationHeader(authHeader);
        var response = listCategoriesUseCase.execute()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
