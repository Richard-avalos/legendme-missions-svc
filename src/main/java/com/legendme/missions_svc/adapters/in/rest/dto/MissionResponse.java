package com.legendme.missions_svc.adapters.in.rest.dto;

import com.legendme.missions_svc.domain.model.enums.Difficulty;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *  Este registro representa la respuesta que se envía al cliente con los detalles de una misión.
 *  Contiene información relevante sobre la misión, incluyendo su estado actual y detalles asociados.
 * @param id
 * @param userId
 * @param title
 * @param description
 * @param categoryCode
 * @param categotyName
 * @param baseXp
 * @param difficulty
 * @param streakGroup
 * @param status
 * @param startedAt
 * @param dueAt
 * @param completedAt
 * @param createdAt
 * @param updatedAt
 */
public record MissionResponse(
        UUID id,
        UUID userId,
        String title,
        String description,
        String categoryCode,
        String categotyName,
        int baseXp,
        Difficulty difficulty,
        String streakGroup,
        MissionStatus status,
        LocalDateTime startedAt,
        LocalDateTime dueAt,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
