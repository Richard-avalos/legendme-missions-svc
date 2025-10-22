package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.PauseMissionUseCase;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PauseMissionService implements PauseMissionUseCase {

    private final MissionRepository missionRepository;

    public PauseMissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public Mission execute(UUID missionId, UUID userId, String note) {
        Mission mission = missionRepository.findById(missionId)
                .filter(m -> m.userId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Mission not found or does not belong to user"));

        if (mission.status() != MissionStatus.IN_PROGRESS) {
            throw new RuntimeException("Only missions IN_PROGRESS can be paused");
        }

        // Si quieres guardar la nota, tendrías que agregar un campo en Mission o en otra entidad de logs

        Mission updatedMission = new Mission(
                mission.uuid(),
                mission.userId(),
                mission.category(),
                mission.title(),
                mission.description(),
                mission.baseXp(),
                mission.difficulty(),
                mission.streakGroup(),
                MissionStatus.PAUSED,
                mission.startedAt(),
                mission.dueAt(),
                mission.completedAt(),
                mission.createdAt(),
                LocalDateTime.now()
        );

        return missionRepository.save(updatedMission);
    }
}
