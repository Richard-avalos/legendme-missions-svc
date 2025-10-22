package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.StartMissionUseCase;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class StartMissionService implements StartMissionUseCase {

    private final MissionRepository missionRepository;

    public StartMissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public Mission execute(UUID missionId, UUID userId) {
        Mission mission = missionRepository.findById(missionId)
                .filter(m -> m.userId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Mission not found or does not belong to user"));

        if (mission.status() != MissionStatus.PENDING) {
            throw new RuntimeException("Only missions in PENDING status can be started");
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
                MissionStatus.IN_PROGRESS,       // ✅ nuevo estado
                LocalDateTime.now(),             // ✅ startedAt
                mission.dueAt(),                 // se mantiene
                null,                            // completedAt sigue null
                mission.createdAt(),             // se mantiene
                LocalDateTime.now()              // ✅ updatedAt
        );

        return missionRepository.save(updatedMission);
    }
}
