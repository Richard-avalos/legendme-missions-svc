package com.legendme.missions_svc.adapters.out.persistence;

import com.legendme.missions_svc.adapters.out.persistence.entity.CategoryEntity;
import com.legendme.missions_svc.adapters.out.persistence.spring.SpringDataCategoryRepository;
import com.legendme.missions_svc.application.port.out.CategoryRepository;
import com.legendme.missions_svc.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
/**
 * Adaptador de persistencia para la entidad Category.
 * Implementa el puerto de salida CategoryRepository utilizando Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryRepository {

    private final SpringDataCategoryRepository repository;

    @Override
    public Optional<Category> findById(Integer id) {
        return repository.findById(id).map(CategoryPersistenceAdapter::toDomain);
    }

    @Override
    public List<Category> findAllActive() {
        return repository.findByIsActiveTrue()
                .stream()
                .map(CategoryPersistenceAdapter::toDomain)
                .toList(); // más limpio que collect(Collectors.toList())
    }

    private static Category toDomain(CategoryEntity e) {
        return new Category(
                e.getId(),
                e.getCode(),
                e.getName(),
                e.getBaseXp(),
                e.isActive(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
