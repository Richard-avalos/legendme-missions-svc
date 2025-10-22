package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.CancelMissionUseCase;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Servicio para cancelar misiones.
 * Implementa la lógica de negocio para cancelar una misión específica.
 */
@Service
@Transactional
public class CancelMissionService implements CancelMissionUseCase {

    /** Repositorio para acceder a las misiones */
    private final MissionRepository missionRepository;

    /** Constructor que inyecta el repositorio de misiones */
    public CancelMissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    /**
     * Cancela una misión específica.
     *
     * @param missionId Identificador de la misión a cancelar.
     * @param userId    Identificador del usuario que cancela la misión.
     * @param reason    Razón de la cancelación.
     * @return La misión cancelada.
     * @throws RuntimeException Si la misión no se encuentra o no pertenece al usuario.
     */
    @Override
    public Mission execute(UUID missionId, UUID userId, String reason) {
        Mission mission = missionRepository.findById(missionId)
                .filter(m -> m.userId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Mission not found or does not belong to user"));

        if (mission.status() == MissionStatus.CANCELLED) {
            return mission; // ya estaba cancelada
        }

        // Crear nueva instancia con estado cancelado
        Mission updatedMission = new Mission(
                mission.uuid(),
                mission.userId(),
                mission.category(),
                mission.title(),
                mission.description(),
                mission.baseXp(),
                mission.difficulty(),
                mission.streakGroup(),
                MissionStatus.CANCELLED,       // ✅ estado cancelado
                mission.startedAt(),
                mission.dueAt(),
                mission.completedAt(),
                mission.createdAt(),
                LocalDateTime.now()            // ✅ updatedAt
        );

        // Aquí podrías loggear la razón si quieres
        System.out.println("Mission canceled. Reason: " + reason);

        return missionRepository.save(updatedMission);
    }
}
