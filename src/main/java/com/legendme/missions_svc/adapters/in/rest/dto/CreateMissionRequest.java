package com.legendme.missions_svc.adapters.in.rest.dto;

import com.legendme.missions_svc.domain.model.enums.Difficulty;

import java.time.LocalDateTime;

/**
 * Este registro representa la solicitud para crear una nueva misión.
 * Contiene los detalles necesarios para definir la misión
 *
 * @param categoryId
 * @param title
 * @param description
 * @param dueAt
 * @param difficulty
 * @param streakGroup
 */
public record CreateMissionRequest(
        Integer categoryId,
        String title,
        String description,
        LocalDateTime dueAt,
        Difficulty difficulty,
        String streakGroup
) {
}
