package com.legendme.missions_svc.application.port.in.command;

import com.legendme.missions_svc.domain.model.enums.MissionStatus;

import java.time.LocalDateTime;

public record SearchMissionCommand(
        MissionStatus status,
        String categoryCode,
        LocalDateTime from,
        LocalDateTime to
)
{
}
