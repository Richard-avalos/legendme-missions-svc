package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.application.port.in.command.CreateMissionCommand;
import com.legendme.missions_svc.application.port.in.command.SearchMissionCommand;
import com.legendme.missions_svc.domain.model.Mission;

import java.util.List;
import java.util.UUID;

/**
 * Caso de uso principal para el dominio Mission.
 * Unifica todas las operaciones relacionadas con misiones en una sola interfaz.
 */
public interface MissionUseCase {

    /**
     * Crea una nueva misión en el sistema.
     *
     * @param command Comando que contiene los detalles necesarios para crear la misión.
     * @return La misión creada.
     */
    Mission createMission(CreateMissionCommand command);

    /**
     * Actualiza una misión existente en el sistema.
     *
     * @param mission La misión con los detalles actualizados.
     * @return La misión actualizada.
     */
    Mission updateMission(Mission mission);

    /**
     * Obtiene los detalles de una misión específica.
     *
     * @param missionId El ID de la misión a obtener.
     * @param userId    El ID del usuario que realiza la solicitud.
     * @return La misión solicitada.
     */
    Mission getMissionDetail(UUID missionId, UUID userId);

    /**
     * Cancela una misión específica.
     *
     * @param missionId El ID de la misión a cancelar.
     * @param userId    El ID del usuario que realiza la solicitud.
     * @param reason    La razón para cancelar la misión.
     * @return La misión cancelada.
     */
    Mission cancelMission(UUID missionId, UUID userId, String reason);

    /**
     * Marca una misión como completada.
     *
     * @param missionId      El ID de la misión a completar.
     * @param userId         El ID del usuario que realiza la solicitud.
     * @param idempotencyKey La clave de idempotencia para evitar operaciones duplicadas.
     * @param note           Una nota opcional sobre la finalización de la misión.
     * @return La misión completada.
     */
    Mission completeMission(UUID missionId, UUID userId, String idempotencyKey, String note);

    Mission pauseMission(UUID missionId, UUID userId, String note);

    Mission startMission(UUID missionId, UUID userId);

    List<Mission> searchMission(SearchMissionCommand command, UUID userId);
}
