package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.application.port.in.command.CreateMissionCommand;
import com.legendme.missions_svc.domain.model.Mission;

import java.util.UUID;

/**
 * Esta interfaz define el caso de uso para crear una misión.
 * Proporciona un metodo para ejecutar la lógica de negocio asociada a la creación de misiones.
 * Puede ser implementada por diferentes clases que manejen la creación de misiones en el
 */
public interface CreateMissionUseCase {

    /**
     * Ejecuta la lógica de negocio para crear una misión.
     *
     * @param command Objeto que contiene los datos necesarios para crear la misión.
     * @return La misión creada.
     */
    Mission execute(CreateMissionCommand command);
}
