package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.CreateMissionUseCase;
import com.legendme.missions_svc.application.port.in.command.CreateMissionCommand;
import com.legendme.missions_svc.application.port.out.CategoryRepository;
import com.legendme.missions_svc.application.port.out.MissionRepository;
import com.legendme.missions_svc.domain.model.Category;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import com.legendme.missions_svc.domain.model.enums.Difficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateMissionService implements CreateMissionUseCase {

    private final MissionRepository missionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Mission execute(CreateMissionCommand command) {
        // Obtener la categoría
        Optional<Category> categoryOpt = categoryRepository.findById(command.categoryId());
        if (categoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Category not found with id: " + command.categoryId());
        }

        Category category = categoryOpt.get();

        // Crear la misión
        Mission mission = new Mission(
                UUID.randomUUID(),               // uuid
                command.userId(),                // userId
                category,                        // category
                command.title(),                 // title
                command.description(),           // description
                category.baseXp(),               // baseXp
                command.difficulty(),            // difficulty
                command.streakGroup(),           // streakGroup
                MissionStatus.PENDING,           // status inicial
                null,                            // startedAt
                command.dueAt(),                 // dueAt
                null,                            // completedAt
                LocalDateTime.now(),             // createdAt
                LocalDateTime.now()              // updatedAt
        );

        // Guardar en la base de datos
        return missionRepository.save(mission);
    }
}

