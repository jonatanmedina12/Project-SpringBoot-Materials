package com.management.materials.exception;

import com.management.materials.dto.response.ApiResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja excepciones de recursos no encontrados
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        logger.warn("Recurso no encontrado: {}", ex.getMessage());

        ApiResponseDto<Object> response = ApiResponseDto.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Maneja excepciones de lógica de negocio
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleBusinessException(
            BusinessException ex, WebRequest request) {

        logger.warn("Error de lógica de negocio: {}", ex.getMessage());

        ApiResponseDto<Object> response = ApiResponseDto.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Maneja errores de validación de datos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        logger.warn("Errores de validación encontrados");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String message = "Errores de validación: " + errors.toString();
        ApiResponseDto<Object> response = ApiResponseDto.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Maneja argumentos ilegales
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        logger.warn("Argumento ilegal: {}", ex.getMessage());

        ApiResponseDto<Object> response = ApiResponseDto.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Maneja excepciones de acceso denegado
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException ex, WebRequest request) {

        logger.warn("Acceso denegado: {}", ex.getMessage());

        ApiResponseDto<Object> response = ApiResponseDto.error("No tiene permisos para realizar esta operación");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Maneja excepciones generales no capturadas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Object>> handleGenericException(
            Exception ex, WebRequest request) {

        logger.error("Error interno del servidor: {}", ex.getMessage(), ex);

        ApiResponseDto<Object> response = ApiResponseDto.error("Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Maneja excepciones de tiempo de ejecución
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        logger.error("Error de tiempo de ejecución: {}", ex.getMessage(), ex);

        String message = ex.getMessage() != null ? ex.getMessage() : "Error interno del servidor";
        ApiResponseDto<Object> response = ApiResponseDto.error(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}