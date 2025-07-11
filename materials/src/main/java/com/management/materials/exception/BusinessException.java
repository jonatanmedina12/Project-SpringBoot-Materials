package com.management.materials.exception;

/**
 * Excepción personalizada para errores de lógica de negocio
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}