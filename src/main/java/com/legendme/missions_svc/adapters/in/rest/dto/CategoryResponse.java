package com.legendme.missions_svc.adapters.in.rest.dto;

import java.util.UUID;

/**
 * Este es el DTO de respuesta para la entidad Category
 *
 * @param id       Identificador único de la categoría
 * @param code Código único de la categoría
 * @param name     Nombre de la categoría
 * @param baseXP   Experiencia base otorgada por la categoría
 * @param isActive Indica si la categoría está activa
 */
public record CategoryResponse(
        Integer id,
        String code,
        String name,
        int baseXP,
        boolean isActive) {
}
