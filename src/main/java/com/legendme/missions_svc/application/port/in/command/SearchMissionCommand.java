package com.legendme.missions_svc.application.port.in.command;

import com.legendme.missions_svc.domain.model.enums.MissionStatus;

import java.time.LocalDateTime;
/**
 * Comando para buscar misiones basadas en varios criterios.
 * Este comando encapsula los parámetros necesarios para filtrar las misiones,
 * incluyendo el estado de la misión, el código de categoría y un rango de fechas.
 */
public record SearchMissionCommand(
        MissionStatus status,
        String categoryCode,
        LocalDateTime from,
        LocalDateTime to
)
{
}
