package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.CompleteMissionUseCase;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Servicio para completar una misión.
 * Implementa el caso de uso CompleteMissionUseCase.
 */
@Service
@Transactional
public class CompleteMissionService implements CompleteMissionUseCase {

    private final MissionRepository missionRepository;

    public CompleteMissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public Mission execute(UUID missionId, UUID userId, String idempotencyKey, String note) {
        Mission mission = missionRepository.findById(missionId)
                .filter(m -> m.userId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Mission not found or does not belong to user"));

        if (mission.status() == MissionStatus.COMPLETED) {
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
                MissionStatus.COMPLETED,       // ✅ estado completado
                mission.startedAt(),
                mission.dueAt(),
                LocalDateTime.now(),           // ✅ completedAt
                mission.createdAt(),
                LocalDateTime.now()            // ✅ updatedAt
        );

        return missionRepository.save(updatedMission);
    }
}
