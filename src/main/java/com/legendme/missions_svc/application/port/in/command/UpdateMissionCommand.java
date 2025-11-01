package com.legendme.missions_svc.application.port.in.command;

import com.legendme.missions_svc.domain.model.enums.Difficulty;

import java.util.UUID;

/**
 * Este registro representa el comando para actualizar una misión existente.
 * Contiene los detalles necesarios para modificar la misión identificada por su ID.
 * @param missionID    Identificador único de la misión a actualizar
 * @param title        Nuevo título de la misión
 * @param description  Nueva descripción de la misión
 * @param categoryId   Nuevo ID de la categoría asociada a la misión
 * @param difficulty   Nueva dificultad de la misión
 * @param streakGroup  Nuevo grupo de rachas asociado a la misión
 */
public record UpdateMissionCommand(
        UUID missionID,
        String title,
        String description,
        Integer categoryId,
        Difficulty difficulty,
        String streakGroup
) {
}
