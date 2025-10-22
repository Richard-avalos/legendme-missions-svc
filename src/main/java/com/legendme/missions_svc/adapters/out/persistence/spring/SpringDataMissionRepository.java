package com.legendme.missions_svc.adapters.out.persistence.spring;

import com.legendme.missions_svc.adapters.out.persistence.entity.MissionEntity;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
/**
 * Repositorio Spring Data JPA para la entidad MissionEntity.
 */
public interface SpringDataMissionRepository extends JpaRepository<MissionEntity, UUID> {

    @Query("""
        SELECT m 
        FROM MissionEntity m
        WHERE m.userId = :userId
          AND (:status IS NULL OR m.status = :status)
          AND (:categoryCode IS NULL OR m.category.code = :categoryCode)
          AND (
               (:from IS NULL OR (COALESCE(m.createdAt, m.startedAt) >= :from))
               AND (:to IS NULL OR (COALESCE(m.dueAt, m.completedAt) <= :to))
          )
    """)
    List<MissionEntity> search(
            @Param("userId") UUID userId,
            @Param("status") MissionStatus status,
            @Param("categoryCode") String categoryCode,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
