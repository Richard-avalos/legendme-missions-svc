package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.domain.model.Mission;

import java.util.UUID;

/**
 * Caso de uso para obtener los detalles de una misión específica.
 * Proporciona un metodo para ejecutar la lógica de negocio asociada a la obtención de
 * detalles de misiones.
 * Puede ser implementada por diferentes clases que manejen la obtención de detalles de misiones
 * en el sistema.
 */
public interface GetMissionDetailUseCase {
    /**
     * Ejecuta la lógica de negocio para obtener los detalles de una misión.
     *
     * @param missionId Identificador de la misión cuyos detalles se desean obtener.
     * @param userId    Identificador del usuario que solicita los detalles de la misión.
     * @return La misión con sus detalles.
     */
    Mission execute(UUID missionId, UUID userId);
}
