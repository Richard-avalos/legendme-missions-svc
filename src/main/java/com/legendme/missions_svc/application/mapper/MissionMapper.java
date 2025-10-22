package com.legendme.missions_svc.application.mapper;
import com.legendme.missions_svc.adapters.in.rest.dto.MissionResponse;
import com.legendme.missions_svc.domain.model.Mission;

/**
 * Mapper para convertir entre la entidad Mission y su DTO de respuesta MissionResponse.
 */
public class MissionMapper {

    public static MissionResponse toResponse(Mission mission){
        return new MissionResponse(
                mission.uuid(),
                mission.userId(),
                mission.title(),
                mission.description(),
                mission.category().code(),
                mission.category().name(),
                mission.baseXp(),
                mission.difficulty(),
                mission.streakGroup(),
                mission.status(),
                mission.startedAt(),
                mission.dueAt(),
                mission.completedAt(),
                mission.createdAt(),
                mission.updatedAt()
        );
    }
}
