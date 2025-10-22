package com.legendme.missions_svc.domain.model.enums;

/**
 * Esta enumeración define los posibles estados de una misión.
 * Puede ser utilizada para rastrear el progreso y la gestión de misiones en el sistema.
 *
 */
public enum MissionStatus {
    PENDING,
    IN_PROGRESS,
    PAUSED,
    COMPLETED,
    CANCELLED,
    EXPIRED

}
