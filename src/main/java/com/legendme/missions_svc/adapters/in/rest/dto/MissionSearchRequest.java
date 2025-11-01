package com.legendme.missions_svc.adapters.in.rest.dto;

import com.legendme.missions_svc.domain.model.enums.MissionStatus;

import java.time.LocalDateTime;
/**
 * Este registro representa la solicitud para buscar misiones según ciertos criterios.
 *
 * @param status       Estado de la misión para filtrar los resultados
 * @param categoryCode Código de la categoría para filtrar las misiones
 * @param from         Fecha y hora de inicio del rango de búsqueda
 * @param to           Fecha y hora de fin del rango de búsqueda
 */
public record MissionSearchRequest(
        MissionStatus status,
        String categoryCode,
        LocalDateTime from,
        LocalDateTime to
){
}
