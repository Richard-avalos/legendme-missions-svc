package com.legendme.missions_svc.adapters.in.rest.dto;
/**
 * Este registro representa la respuesta de error para las solicitudes REST.
 *
 * @param code    Código de error que identifica el tipo de error
 * @param message Mensaje descriptivo del error
 * @param details Detalles adicionales sobre el error
 */
public record ErrorResponse(
        String code,
        String message,
        String details
) {
}
