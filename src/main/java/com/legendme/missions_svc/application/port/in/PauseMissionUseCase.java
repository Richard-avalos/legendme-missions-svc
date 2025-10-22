package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.domain.model.Mission;

import java.util.UUID;

/**
 * Esta interfaz define el caso de uso para pausar una misión.
 * Proporciona un metodo para ejecutar la lógica de negocio asociada a la pausa de misiones.
 * Puede ser implementada por diferentes clases que manejen la pausa de misiones en el sistema
 *
 */
public interface PauseMissionUseCase {
    /**
     * Ejecuta la lógica de negocio para pausar una misión.
     *
     * @param missionId Identificador de la misión a pausar.
     * @param userId    Identificador del usuario que pausa la misión.
     * @param note      Nota o comentario asociado a la pausa de la misión.
     * @return La misión pausada.
     */
    Mission execute(UUID missionId,UUID userId,String note);
}
