package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.domain.model.Mission;

import java.util.UUID;

/**
 * Esta interfaz define el caso de uso para completar una misión.
 * Proporciona un metodo para ejecutar la lógica de negocio asociada a la finalización de misiones.
 * Puede ser implementada por diferentes clases que manejen la finalización de misiones
 */
public interface CompleteMissionUseCase {
    /**
     * Ejecuta la lógica de negocio para completar una misión.
     *
     * @param missionId      Identificador de la misión a completar.
     * @param userId         Identificador del usuario que completa la misión.
     * @param idempotencyKey Clave de idempotencia para evitar operaciones duplicadas.
     * @param note           Nota opcional asociada a la finalización de la misión.
     * @return La misión completada.
     */
    Mission execute(UUID missionId, UUID userId,String idempotencyKey,String note);

}
