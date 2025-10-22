package com.legendme.missions_svc.domain.model;

import java.time.LocalDateTime;


/**
 * Esta clase representa una categoría de misión en el sistema.
 * Puede ser utilizada para agrupar misiones similares y asignarles características comunes.
 *
 * @param id
 * @param code
 * @param name
 * @param baseXp
 * @param isActive
 * @param createdAt
 * @param updatedAt
 */
public record Category(

        Integer id,
        String code,
        String name,
        int baseXp,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
