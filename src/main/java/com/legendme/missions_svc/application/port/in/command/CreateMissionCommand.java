package com.legendme.missions_svc.application.port.in.command;

import com.legendme.missions_svc.domain.model.enums.Difficulty;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Este comando representa los datos necesarios para crear una misión.
 * Se usa dentro del caso de uso CreateMissionUseCase.
 */
public record CreateMissionCommand(
        UUID userId,
        Integer categoryId,
        String title,
        String description,
        LocalDateTime dueAt,
        Difficulty difficulty,
        String streakGroup
) {}
