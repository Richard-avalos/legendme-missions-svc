package com.legendme.missions_svc.adapters.out.persistence.spring;

import com.legendme.missions_svc.adapters.out.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad CategoryEntity.
 */
public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    List<CategoryEntity> findByIsActiveTrue();
}
