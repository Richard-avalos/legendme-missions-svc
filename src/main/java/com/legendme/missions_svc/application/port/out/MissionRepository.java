package com.legendme.missions_svc.application.port.out;

import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Esta interfaz define las operaciones de persistencia para las misiones.
 * Proporciona métodos para guardar, buscar y filtrar misiones en el sistema.
 * Puede ser implementada por diferentes tecnologías de almacenamiento, como bases de datos relacionales o NoSQL.
 *
 */
public interface MissionRepository {

    Mission save(Mission mission);
    Mission update(Mission mission);
    Optional<Mission> findById(UUID id);
    List<Mission> findByUserIdAndFilter(UUID userId, MissionStatus status, String categoryCode, LocalDateTime from, LocalDateTime to);
}
