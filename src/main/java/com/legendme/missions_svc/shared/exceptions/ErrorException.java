package com.legendme.missions_svc.shared.exceptions;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Excepción personalizada para manejar errores específicos en la aplicación.
 * Esta clase extiende {@link RuntimeException} y permite incluir un código de error
 * y un estado HTTP asociado con el error.
 */
public class ErrorException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;

    /**
     * Constructor de la excepción que inicializa el mensaje, el código de error y el estado HTTP.
     *
     * @param message   El mensaje descriptivo del error.
     * @param errorCode El código de error único asociado con la excepción.
     * @param status    El estado HTTP asociado con la excepción.
     */
    public ErrorException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public ErrorException(HttpStatus httpStatus, String preconditionRequired, String s, String headerRequired) {
        super(s);
        this.errorCode = preconditionRequired;
        this.status = httpStatus;
    }

    public ErrorException(String s) {
        super(s);
        this.errorCode = "error.exception.generic";
        this.status = HttpStatus.BAD_REQUEST;
    }


    /**
     * Obtiene el código de error asociado con la excepción.
     *
     * @return El código de error como una cadena.
     */
    public String errorCode() {
        return errorCode;
    }

    /**
     * Obtiene el estado HTTP asociado con la excepción.
     *
     * @return El estado HTTP como un objeto {@link HttpStatus}.
     */
    public HttpStatus status() {
        return status;
    }
}