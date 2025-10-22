package com.legendme.missions_svc.application.mapper;

import com.legendme.missions_svc.adapters.in.rest.dto.CategoryResponse;
import com.legendme.missions_svc.domain.model.Category;

/**
 * Mapper para convertir entre la entidad Category y su DTO de respuesta CategoryResponse.
 */
public class CategoryMapper {

    public static CategoryResponse toResponse(Category category){
        return new CategoryResponse(
                category.id(),
                category.code(),
                category.name(),
                category.baseXp(),
                category.isActive()

        );
    }
}
