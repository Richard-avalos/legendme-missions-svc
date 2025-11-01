package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.application.port.in.command.CreateMissionCommand;
import com.legendme.missions_svc.application.port.in.command.SearchMissionCommand;
import com.legendme.missions_svc.application.port.in.command.UpdateMissionCommand;
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
     * @return La misión actualizada.
     */
    Mission updateMission(UpdateMissionCommand command);

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

    /**
     * Pausa una misión específica.
     *
     * @param missionId El ID de la misión a pausar.
     * @param userId    El ID del usuario que realiza la solicitud.
     * @param note      Una nota opcional sobre la pausa de la misión.
     * @return La misión pausada.
     */
    Mission pauseMission(UUID missionId, UUID userId, String note);

    /**
     * Reanuda una misión específica.
     *
     * @param missionId El ID de la misión a reanudar.
     * @param userId    El ID del usuario que realiza la solicitud.
     * @return La misión reanudada.
     */
    Mission startMission(UUID missionId, UUID userId);

    /**
     * Busca misiones según los criterios especificados en el comando de búsqueda.
     *
     * @param command
     * @param userId
     * @return
     */
    List<Mission> searchMission(SearchMissionCommand command, UUID userId);
}
