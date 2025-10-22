package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.domain.model.Mission;

import java.util.UUID;

/**
 * Caso de uso para iniciar una misión.
 * Proporciona un metodo para ejecutar la lógica de negocio asociada al inicio de misiones
 * Puede ser implementada por diferentes clases que manejen el inicio de misiones en el sistema.
 *
 */
public interface StartMissionUseCase {
    /**
     * Ejecuta la lógica de negocio para iniciar una misión.
     *
     * @param missionId Identificador de la misión a iniciar.
     * @param userId    Identificador del usuario que inicia la misión.
     * @return La misión iniciada.
     */
    Mission  execute(UUID missionId, UUID userId);
}
