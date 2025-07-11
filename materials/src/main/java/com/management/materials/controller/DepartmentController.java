package com.management.materials.controller;
import com.management.materials.dto.response.ApiResponseDto;
import com.management.materials.dto.response.DepartmentResponseDto;
import com.management.materials.service.IDepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controlador REST para gestión de departamentos
 */
@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Departamentos", description = "Operaciones para gestión de departamentos")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final IDepartmentService departmentService;

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Obtiene todos los departamentos
     */
    @GetMapping
    @Operation(summary = "Obtener todos los departamentos",
            description = "Retorna la lista completa de departamentos del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de departamentos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron departamentos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponseDto<List<DepartmentResponseDto>>> getAllDepartments() {
        logger.info("Solicitud para obtener todos los departamentos");

        try {
            List<DepartmentResponseDto> departments = departmentService.getAllDepartments();

            if (departments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron departamentos"));
            }

            ApiResponseDto<List<DepartmentResponseDto>> response = ApiResponseDto.success(
                    departments,
                    "Departamentos obtenidos exitosamente. Total: " + departments.size()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al obtener todos los departamentos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al obtener departamentos"));
        }
    }

    /**
     * Obtiene un departamento por código
     */
    @GetMapping("/{code}")
    @Operation(summary = "Obtener departamento por código",
            description = "Retorna un departamento específico basado en su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<DepartmentResponseDto>> getDepartmentByCode(
            @Parameter(description = "Código del departamento", example = "DC")
            @PathVariable String code) {

        logger.info("Solicitud para obtener departamento con código: {}", code);

        try {
            DepartmentResponseDto department = departmentService.getDepartmentByCode(code);

            ApiResponseDto<DepartmentResponseDto> response = ApiResponseDto.success(
                    department,
                    "Departamento encontrado"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar departamento con código: {}", code, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error("Departamento no encontrado con código: " + code));
        }
    }

    /**
     * Busca departamentos por nombre
     */
    @GetMapping("/by-name")
    @Operation(summary = "Buscar departamentos por nombre",
            description = "Busca departamentos que contengan el nombre especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron departamentos"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<DepartmentResponseDto>>> getDepartmentsByName(
            @Parameter(description = "Nombre o parte del nombre del departamento")
            @RequestParam String name) {

        logger.info("Solicitud para buscar departamentos por nombre: {}", name);

        try {
            List<DepartmentResponseDto> departments = departmentService.getDepartmentsByName(name);

            if (departments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron departamentos que contengan: " + name));
            }

            ApiResponseDto<List<DepartmentResponseDto>> response = ApiResponseDto.success(
                    departments,
                    "Se encontraron " + departments.size() + " departamentos que contienen '" + name + "'"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar departamentos por nombre: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar departamentos"));
        }
    }
}