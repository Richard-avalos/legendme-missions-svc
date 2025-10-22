package com.legendme.missions_svc.adapters.out.persistence;

import com.legendme.missions_svc.adapters.out.persistence.entity.CategoryEntity;
import com.legendme.missions_svc.adapters.out.persistence.entity.MissionEntity;
import com.legendme.missions_svc.adapters.out.persistence.spring.SpringDataMissionRepository;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Category;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * Adaptador de persistencia para la entidad Mission.
 * Implementa el puerto de salida MissionRepository utilizando Spring Data JPA.
 */
@Repository
@RequiredArgsConstructor
public class MissionPersistenceAdapter implements MissionRepository {

    private final SpringDataMissionRepository spring;

    @Override
    @Transactional
    public Mission save(Mission mission) {

        MissionEntity entity;

        if (mission.uuid() == null) {
            // NUEVA
            entity = toEntity(mission);
            if (entity.getId() == null) entity.setId(UUID.randomUUID());
            LocalDateTime now = LocalDateTime.now();
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);

        } else {
            // ACTUALIZAR EXISTENTE
            entity = spring.findById(mission.uuid())
                    .orElseGet(() -> toEntity(mission));

            entity.setTitle(mission.title());
            entity.setDescription(mission.description());
            entity.setBaseXp(mission.baseXp());
            entity.setDifficulty(mission.difficulty());
            entity.setStreakGroup(mission.streakGroup());
            entity.setStatus(mission.status());
            entity.setStartedAt(mission.startedAt());
            entity.setDueAt(mission.dueAt());
            entity.setCompletedAt(mission.completedAt());
            entity.setUpdatedAt(LocalDateTime.now());

            // category: solo referencia
            if (entity.getCategory() == null || !entity.getCategory().getId().equals(mission.category().id())) {
                entity.setCategory(CategoryEntity.builder()
                        .id(mission.category().id())
                        .build());
            }
        }

        MissionEntity saved = spring.save(entity);
        return toDomain(saved);
    }

    private MissionEntity toEntity(Mission m) {
        return MissionEntity.builder()
                .id(m.uuid())
                .userId(m.userId())
                .category(CategoryEntity.builder()
                        .id(m.category().id())
                        .build())
                .title(m.title())
                .description(m.description())
                .baseXp(m.baseXp())
                .difficulty(m.difficulty())
                .streakGroup(m.streakGroup())
                .status(m.status())
                .startedAt(m.startedAt())
                .dueAt(m.dueAt())
                .completedAt(m.completedAt())
                .createdAt(m.createdAt())
                .updatedAt(m.updatedAt())
                .build();
    }

    @Override
    public Optional<Mission> findById(UUID id) {
        return spring.findById(id).map(this::toDomain);
    }

    @Override
    public List<Mission> findByUserIdandFilter(UUID userId, MissionStatus status, String categoryCode, LocalDateTime from, LocalDateTime to) {
        List<MissionEntity> found = spring.search(userId, status, categoryCode, from, to);
        return found.stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Mission toDomain(MissionEntity e) {
        Category c = new Category(
                e.getCategory().getId(),
                e.getCategory().getCode(),
                e.getCategory().getName(),
                e.getCategory().getBaseXp(),
                e.getCategory().isActive(),
                e.getCategory().getCreatedAt(),
                e.getCategory().getUpdatedAt()
        );

        return new Mission(
                e.getId(),
                e.getUserId(),
                c,
                e.getTitle(),
                e.getDescription(),
                e.getBaseXp(),
                e.getDifficulty(),
                e.getStreakGroup(),
                e.getStatus(),
                e.getStartedAt(),
                e.getDueAt(),
                e.getCompletedAt(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
