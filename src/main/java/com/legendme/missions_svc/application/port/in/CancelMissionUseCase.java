package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.domain.model.Mission;

import java.util.UUID;

/**
 * Esta interfaz define el caso de uso para cancelar una misión.
 * Proporciona un metodo para ejecutar la lógica de negocio asociada a la cancelación de misiones.
 * Puede ser implementada por diferentes clases que manejen la cancelación de misiones
 */
public interface CancelMissionUseCase {

    /**
     * Ejecuta la lógica de negocio para cancelar una misión.
     *
     * @param missionId Identificador de la misión a cancelar.
     * @param userId    Identificador del usuario que cancela la misión.
     * @param reason    Razón de la cancelación.
     * @return La misión cancelada.
     */
    Mission execute(UUID missionId, UUID userId,String reason);
}
