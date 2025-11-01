package com.legendme.missions_svc.domain.model;

import com.legendme.missions_svc.domain.model.enums.Difficulty;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Esta clase representa una misión asignada a un usuario en el sistema.
 * Puede ser utilizada para gestionar y rastrear el progreso de las misiones.
 *
 * @param uuid        Unique identifier for the mission.
 * @param userId      Identifier of the user to whom the mission is assigned.
 * @param category    Category of the mission.
 * @param title       Title of the mission.
 * @param description Description of the mission.
 * @param baseXp      Base experience points awarded for completing the mission.
 * @param difficulty  Difficulty level of the mission.
 * @param streakGroup Grouping identifier for missions that contribute to a streak.
 * @param status      Current status of the mission (e.g., PENDING, COMPLETED).
 * @param startedAt   Timestamp when the mission was started.
 * @param dueAt       Timestamp when the mission is due.
 * @param completedAt Timestamp when the mission was completed.
 * @param createdAt   Timestamp when the mission was created.
 * @param updatedAt   Timestamp when the mission was last updated.
 */
public record Mission(
        UUID uuid,
        UUID userId,
        Category category,
        String title,
        String description,
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
    public Mission withStatus(MissionStatus newStatus) {
        return new Mission(
                this.uuid(),
                this.userId(),
                this.category(),
                this.title(),
                this.description(),
                this.baseXp(),
                this.difficulty(),
                this.streakGroup(),
                newStatus,
                this.startedAt(),
                this.dueAt(),
                this.completedAt(),
                this.createdAt(),
                LocalDateTime.now()
        );
    }
}
