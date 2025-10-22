package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.application.port.in.command.SearchMissionCommand;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso para buscar misiones basadas en varios criterios.
 * Permite filtrar misiones por usuario, estado, categoría y rango de fechas.
 * Este caso de uso es parte de la capa de aplicación y define la lógica de negocio
 * para la búsqueda de misiones.
 */
public interface SearchMissionsUseCase {

    /**
     * Ejecuta la búsqueda de misiones según los criterios proporcionados.
     *
     * @param userId       Identificador del usuario propietario de las misiones.Fecha y hora de fin del rango para filtrar las misiones (opcional).
     * @return Lista de misiones que coinciden con los criterios de búsqueda.
     */
    List<Mission> execute(SearchMissionCommand command, UUID userId);
}
