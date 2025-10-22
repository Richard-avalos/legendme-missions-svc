package com.legendme.missions_svc.application.port.out;

import com.legendme.missions_svc.domain.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Esta interfaz define las operaciones de persistencia relacionadas con la entidad Category.
 * Proporciona métodos para buscar categorías por su ID y para obtener todas las categorías activas
 * en el sistema.
 * Puede ser implementada por diferentes tecnologías de almacenamiento, como bases de datos relacionales o NoSQL.
 * @see Category
 */
public interface CategoryRepository {
    Optional<Category> findById(Integer id);

    List<Category> findAllActive();
}
