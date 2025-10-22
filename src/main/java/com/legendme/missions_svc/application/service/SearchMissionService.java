package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.SearchMissionsUseCase;
import com.legendme.missions_svc.application.port.in.command.SearchMissionCommand;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Mission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SearchMissionService implements SearchMissionsUseCase {

    private final MissionRepository missionRepository;

    public SearchMissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public List<Mission> execute(SearchMissionCommand command, UUID userId) {
        return missionRepository.findByUserIdandFilter(
                userId,
                command.status(),
                command.categoryCode(),
                command.from(),
                command.to()
        );
    }
}

