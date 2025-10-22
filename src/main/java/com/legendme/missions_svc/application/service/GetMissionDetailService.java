package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.GetMissionDetailUseCase;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Mission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GetMissionDetailService implements GetMissionDetailUseCase {

    private final MissionRepository missionRepository;

    public GetMissionDetailService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public Mission execute(UUID missionId, UUID userId) {
        return missionRepository.findById(missionId)
                .filter(mission -> mission.userId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Mission not found or not owned by user"));
    }
}
