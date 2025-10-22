package com.legendme.missions_svc.domain.service;

import com.legendme.missions_svc.domain.model.enums.MissionStatus;
import com.legendme.missions_svc.shared.exceptions.ErrorException;

/**
 * Esta clase maneja las transiciones de estado de una misión.
 * Proporciona métodos para validar transiciones de estado y verificar si un estado es terminal.
 * Puede ser utilizada para asegurar que las misiones sigan un flujo de estado válido.
 *
 */
public class MissionStateMachine {
    /**
     * Valida si la transición de estado de una misión es válida.
     *
     * @param current El estado actual de la misión.
     * @param next    El estado al que se desea transicionar.
     * @throws ErrorException Si la transición no es válida.
     */
    public static void validateTransition(MissionStatus current, MissionStatus next){
        switch (current){
            case PENDING -> {
                if(!(next == MissionStatus.IN_PROGRESS|| next == MissionStatus.CANCELLED || next == MissionStatus.EXPIRED)){
                    throw new ErrorException("Transicion inválida:  PENDING ->" + next);
                }
            }
            case IN_PROGRESS -> {
                if(!(next == MissionStatus.PAUSED || next == MissionStatus.COMPLETED || next == MissionStatus.CANCELLED || next == MissionStatus.EXPIRED)){
                    throw new ErrorException("Transicion inválida:  IN_PROGRESS ->" + next);
                }
            }
            case PAUSED -> {
                if (!(next == MissionStatus.IN_PROGRESS || next == MissionStatus.CANCELLED || next == MissionStatus.EXPIRED)) {
                    throw new ErrorException("Transicion inválida:  PAUSED ->" + next);
                }
            }
            case COMPLETED, CANCELLED, EXPIRED -> {
                throw new ErrorException("Transicion inválida:  " + current + " ->" + next);
            }
            default -> throw new ErrorException("Estado desconocido: " + current);
        }
    }
    /**
     * Verifica si un estado de misión es terminal.
     *
     * @param status El estado de la misión a verificar.
     * @return true si el estado es terminal (COMPLETED, CANCELLED, EXPIRED), false en caso contrario.
     */
    public static boolean isTerminal(MissionStatus status){
        return switch (status) {
            case COMPLETED, CANCELLED, EXPIRED -> true;
            default -> false;
        };
    }
}
