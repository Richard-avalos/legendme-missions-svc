package com.legendme.missions_svc.application.port.in;

import com.legendme.missions_svc.domain.model.Category;

import java.util.List;

/**
 * Esta interfaz define el caso de uso para listar todas las categorías disponibles.
 * Proporciona un metodo para ejecutar la lógica de negocio asociada a la obtención de
 * categorías.
 * Puede ser implementada por diferentes clases que manejen la obtención de categorías en el sistema
 */
public interface ListCategoriesUseCase {

    /**
     * Ejecuta la lógica de negocio para obtener todas las categorías disponibles.
     *
     * @return Una lista de categorías.
     */
    List<Category> listCategoriesExecute();
}
