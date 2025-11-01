package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.MissionUseCase;
import com.legendme.missions_svc.application.port.in.command.CreateMissionCommand;
import com.legendme.missions_svc.application.port.in.command.SearchMissionCommand;
import com.legendme.missions_svc.application.port.in.command.UpdateMissionCommand;
import com.legendme.missions_svc.application.port.out.CategoryRepository;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Category;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import com.legendme.missions_svc.shared.exceptions.ErrorException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementación del caso de uso MissionUseCase.
 * Proporciona la lógica de negocio para gestionar misiones.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class MissionService implements MissionUseCase {

    /** Repositorio para operaciones de misión */
    private final MissionRepository missionRepository;
    private final CategoryRepository categoryRepository;


    /**    * Cancela una misión específica.
     *
     * @param id      El ID de la misión a cancelar.
     * @param userId  El ID del usuario que realiza la solicitud.
     * @param reason  La razón para cancelar la misión.
     * @return La misión cancelada.
     */
    @Override
    public Mission cancelMission(UUID id, UUID userId, String reason) {
        log.info("[CancelMissionService] Iniciando cancelación para misión ID={}", id);

        try {
            Mission mission = missionRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("[CancelMissionService] Misión no encontrada ID={}", id);
                        return new ErrorException("Mission not found with id " + id);
                    });

            Mission updatedMission = mission.withStatus(MissionStatus.CANCELLED);

            Mission saved = missionRepository.save(updatedMission);

            log.info("[CancelMissionService] Misión cancelada correctamente ID={}", id);
            return saved;

        } catch (ErrorException ex) {
            // Errores esperados del negocio
            throw ex;

        } catch (Exception ex) {
            // Cualquier otro error inesperado
            log.error("[CancelMissionService] Error inesperado cancelando misión ID={}", id, ex);
            throw new ErrorException("Unexpected error cancelling mission");
        }
    }
    /**    * Marca una misión como completada.
     *
     * @param missionId      El ID de la misión a completar.
     * @param userId         El ID del usuario que realiza la solicitud.
     * @param idempotencyKey La clave de idempotencia para evitar operaciones duplicadas.
     * @param note           Una nota opcional sobre la finalización de la misión.
     * @return La misión completada.
     */
    @Override
    public Mission completeMission(UUID missionId, UUID userId, String idempotencyKey, String note) {
        log.info("[CompleteMissionService] Iniciando completado de misión {} para usuario {}", missionId, userId);

        try {
            Mission mission = missionRepository.findById(missionId)
                    .filter(m -> m.userId().equals(userId))
                    .orElseThrow(() -> new ErrorException(
                            HttpStatus.NOT_FOUND,
                            "MISSION_NOT_FOUND",
                            "Mission not found or does not belong to user",
                            "Verifique el ID o permisos del usuario"
                    ));

            if (mission.status() == MissionStatus.COMPLETED) {
                log.warn("[CompleteMissionService] La misión {} ya estaba completada", missionId);
                return mission;
            }

            Mission updatedMission = new Mission(
                    mission.uuid(),
                    mission.userId(),
                    mission.category(),
                    mission.title(),
                    mission.description(),
                    mission.baseXp(),
                    mission.difficulty(),
                    mission.streakGroup(),
                    MissionStatus.COMPLETED,
                    mission.startedAt(),
                    mission.dueAt(),
                    LocalDateTime.now(),
                    mission.createdAt(),
                    LocalDateTime.now()
            );

            log.info("[CompleteMissionService] Misión {} completada exitosamente", missionId);
            return missionRepository.save(updatedMission);

        } catch (ErrorException e) {
            log.error("[CompleteMissionService] Error controlado: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("[CompleteMissionService] Error inesperado al completar misión {}: {}", missionId, e.getMessage());
            throw new ErrorException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "COMPLETE_MISSION_FAILED",
                    "Ocurrió un error al completar la misión",
                    e.getMessage()
            );
        }
    }

    /**    * Crea una nueva misión en el sistema.
     *
     * @return La misión creada.
     */
    @Override
    public Mission createMission(CreateMissionCommand command) {
        log.info("[CreateMissionService] Creando misión para el usuario {}", command.userId());

        try {
            Category category = categoryRepository.findById(command.categoryId())
                    .orElseThrow(() -> new ErrorException(
                            HttpStatus.NOT_FOUND,
                            "CATEGORY_NOT_FOUND",
                            "Category not found",
                            "No existe una categoría con el ID enviado"
                    ));

            Mission mission = new Mission(
                    null,
                    command.userId(),
                    category,
                    command.title(),
                    command.description(),
                    category.baseXp(),
                    command.difficulty(),
                    command.streakGroup(),
                    MissionStatus.PENDING,
                    null,
                    command.dueAt(),
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            Mission savedMission = missionRepository.save(mission);
            log.info("[CreateMissionService] Misión {} creada exitosamente", savedMission.uuid());

            return savedMission;

        } catch (ErrorException e) {
            log.error("[CreateMissionService] Error controlado: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("[CreateMissionService] Error inesperado al crear misión: {}", e.getMessage());
            throw new ErrorException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "CREATE_MISSION_FAILED",
                    "Ocurrió un error al crear la misión",
                    e.getMessage()
            );
        }
    }

    /**    * Obtiene los detalles de una misión específica.
     *
     * @param missionId El ID de la misión a obtener.
     * @param userId    El ID del usuario que realiza la solicitud.
     * @return La misión solicitada.
     */
    @Override
    public Mission getMissionDetail(UUID missionId, UUID userId) {
        log.info("[GetMissionDetailService] Obteniendo detalle de misión {} para usuario {}", missionId, userId);

        try {
            Mission mission = missionRepository.findById(missionId)
                    .filter(m -> m.userId().equals(userId))
                    .orElseThrow(() -> new ErrorException(
                            HttpStatus.NOT_FOUND,
                            "MISSION_NOT_FOUND",
                            "Mission not found or does not belong to user",
                            "Verifique el ID o permisos del usuario"
                    ));

            log.info("[GetMissionDetailService] Misión {} obtenida exitosamente", mission.uuid());
            return mission;

        } catch (ErrorException e) {
            log.error("[GetMissionDetailService] Error controlado: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("[GetMissionDetailService] Error inesperado al obtener misión {}: {}", missionId, e.getMessage());
            throw new ErrorException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "GET_MISSION_FAILED",
                    "Ocurrió un error al obtener el detalle de la misión",
                    e.getMessage()
            );
        }
    }

    /**
     * Pausa una misión específica.
     * @param id El ID de la misión a pausar.
     * @param userId    El ID del usuario que realiza la solicitud.
     * @param note      Una nota opcional sobre la pausa de la misión.
     * @return
     */
    @Override
    public Mission pauseMission(UUID id, UUID userId, String note) {
        log.info("[PauseMissionService] Pausing mission ID: {} del UserId: {}",id,userId);

        try{
            Mission mission = missionRepository.findById(id)
                    .filter(m -> m.userId().equals(userId))
                    .orElseThrow(() -> {
                        log.warn("[PauseMissionService] Misión no encontrada ID={}", id);
                        return new ErrorException("Mission not found or does not belong to user");
                    });
            Mission updateMission = mission.withStatus(MissionStatus.PAUSED);
            Mission saved = missionRepository.save(updateMission);
            log.info("[PauseMissionService] Misión pausada correctamente ID={}", id);
            return saved;

        } catch (ErrorException ex) {
            // Errores esperados del negocio
            throw ex;

        } catch (Exception ex) {
            // Cualquier otro error inesperado
            log.error("[PauseMissionService] Error inesperado pausando misión ID={}", id, ex);
            throw new ErrorException("Unexpected error pausing mission");
        }
    }

    /**
     * Busca misiones según los criterios especificados en el comando.
     *
     * @param command Comando con los criterios de búsqueda.
     * @param userId  ID del usuario para filtrar las misiones.
     * @return Lista de misiones que cumplen con los criterios de búsqueda.
     */
    @Override
    public List<Mission> searchMission(SearchMissionCommand command, UUID userId) {
        log.info("[SearchMissionService] Buscando misiones para UsuarioID: {} con filtros: {}", userId, command);

        try {
            List<Mission> missions = missionRepository.findByUserIdAndFilter(
                    userId,
                    command.status(),
                    command.categoryCode(),
                    command.from(),
                    command.to()
            );

            if (missions.isEmpty()) {
                log.warn("[SearchMissionService] No se encontraron misiones para UsuarioID: {}", userId);
            } else {
                log.info("[SearchMissionService] {} misiones encontradas para UsuarioID: {}", missions.size(), userId);
            }

            return missions;
        }
        catch (ErrorException ex) {
            log.error("[SearchMissionService] Error controlado buscando misiones para UsuarioID: {}", userId, ex);
            throw ex; // dejamos que la ErrorException viaje tal cual
        }
        catch (Exception ex) {
            log.error("[SearchMissionService] Error inesperado buscando misiones para UsuarioID: {}", userId, ex);
            throw new ErrorException("Ocurrió un error inesperado al buscar misiones");
        }
    }
    /**
     * Reanuda una misión específica.
     *
     * @param missionId El ID de la misión a reanudar.
     * @param userId    El ID del usuario que realiza la solicitud.
     * @return La misión reanudada.
     */
    @Override
    public Mission startMission(UUID missionId, UUID userId) {
        log.info("[StartMissionService] Intentando iniciar misión {} para usuario {}", missionId, userId);

        try {
            Mission mission = missionRepository.findById(missionId)
                    .filter(m -> m.userId().equals(userId))
                    .orElseThrow(() -> new ErrorException(
                            HttpStatus.NOT_FOUND,
                            "MISSION_NOT_FOUND",
                            "Mission not found or does not belong to user",
                            "Verificar ID de misión o permisos del usuario"
                    ));

            if (mission.status() != MissionStatus.PENDING) {
                throw new ErrorException(
                        HttpStatus.BAD_REQUEST,
                        "INVALID_MISSION_STATUS",
                        "Only missions in PENDING status can be started",
                        "La misión debe estar en estado PENDING"
                );
            }

            Mission updatedMission = new Mission(
                    mission.uuid(),
                    mission.userId(),
                    mission.category(),
                    mission.title(),
                    mission.description(),
                    mission.baseXp(),
                    mission.difficulty(),
                    mission.streakGroup(),
                    MissionStatus.IN_PROGRESS,
                    LocalDateTime.now(),
                    mission.dueAt(),
                    null,
                    mission.createdAt(),
                    LocalDateTime.now()
            );

            Mission savedMission = missionRepository.save(updatedMission);
            log.info("[StartMissionService] Misión {} iniciada exitosamente para usuario {}", missionId, userId);

            return savedMission;

        } catch (ErrorException e) {
            log.error("[StartMissionService] Error controlado al iniciar misión {}: {}", missionId, e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("[StartMissionService] Error inesperado al iniciar misión {}: {}", missionId, e.getMessage());
            throw new ErrorException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "START_MISSION_FAILED",
                    "Ocurrió un error al iniciar la misión",
                    e.getMessage()
            );
        }
    }
    /**
     * Actualiza una misión existente.
     *
     * @return Misión actualizada
     */
    @Override
    public Mission updateMission(UpdateMissionCommand command) {
        log.info("[UpdateMissionService] Actualizando misión {}", command.missionID());

        try {
            Mission existingMission = missionRepository.findById(command.missionID())
                    .orElseThrow(() -> new ErrorException(
                            HttpStatus.NOT_FOUND,
                            "MISSION_NOT_FOUND",
                            "Mission not found",
                            "No existe una misión con el ID enviado"
                    ));

            Category newCategory = categoryRepository.findById(command.categoryId())
                    .orElseThrow(() -> new ErrorException(
                            HttpStatus.NOT_FOUND,
                            "CATEGORY_NOT_FOUND",
                            "Category not found",
                            "No existe una categoría con el ID enviado"
                    ));

            Mission updatedMission = new Mission(
                    existingMission.uuid(),
                    existingMission.userId(),
                    newCategory,
                    command.title() != null ? command.title() : existingMission.title(),
                    command.description() != null ? command.description() : existingMission.description(),
                    newCategory.baseXp(),
                    command.difficulty() != null ? command.difficulty() : existingMission.difficulty(),
                    command.streakGroup() != null ? command.streakGroup() : existingMission.streakGroup(),
                    existingMission.status(),
                    existingMission.startedAt(),
                    existingMission.dueAt(),
                    existingMission.completedAt(),
                    existingMission.createdAt(),
                    LocalDateTime.now()
            );

            Mission savedMission = missionRepository.update(updatedMission);
            log.info("[UpdateMissionService] Misión {} actualizada exitosamente", savedMission.uuid());

            return savedMission;

        } catch (ErrorException e) {
            log.error("[UpdateMissionService] Error controlado: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("[UpdateMissionService] Error inesperado al actualizar misión: {}", e.getMessage());
            throw new ErrorException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "UPDATE_MISSION_FAILED",
                    "Ocurrió un error al actualizar la misión",
                    e.getMessage()
            );
        }
    }

}
