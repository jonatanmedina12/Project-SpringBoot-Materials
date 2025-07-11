package com.management.materials.controller;

import com.management.materials.dto.response.ApiResponseDto;
import com.management.materials.dto.response.CityResponseDto;
import com.management.materials.service.ICityService;
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
 * Controlador REST para gestión de ciudades
 */
@RestController
@RequestMapping("/api/cities")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Ciudades", description = "Operaciones para gestión de ciudades")
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final ICityService cityService;

    public CityController(ICityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Obtiene todas las ciudades
     */
    @GetMapping
    @Operation(summary = "Obtener todas las ciudades",
            description = "Retorna la lista completa de ciudades del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ciudades obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron ciudades"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponseDto<List<CityResponseDto>>> getAllCities() {
        logger.info("Solicitud para obtener todas las ciudades");

        try {
            List<CityResponseDto> cities = cityService.getAllCities();

            if (cities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron ciudades"));
            }

            ApiResponseDto<List<CityResponseDto>> response = ApiResponseDto.success(
                    cities,
                    "Ciudades obtenidas exitosamente. Total: " + cities.size()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al obtener todas las ciudades", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al obtener ciudades"));
        }
    }

    /**
     * Obtiene una ciudad por código
     */
    @GetMapping("/{code}")
    @Operation(summary = "Obtener ciudad por código",
            description = "Retorna una ciudad específica basada en su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad encontrada"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<CityResponseDto>> getCityByCode(
            @Parameter(description = "Código de la ciudad", example = "BOG")
            @PathVariable String code) {

        logger.info("Solicitud para obtener ciudad con código: {}", code);

        try {
            CityResponseDto city = cityService.getCityByCode(code);

            ApiResponseDto<CityResponseDto> response = ApiResponseDto.success(
                    city,
                    "Ciudad encontrada"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar ciudad con código: {}", code, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error("Ciudad no encontrada con código: " + code));
        }
    }

    /**
     * Busca ciudades por código de departamento
     */
    @GetMapping("/by-department/{departmentCode}")
    @Operation(summary = "Buscar ciudades por departamento",
            description = "Retorna ciudades filtradas por código de departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron ciudades"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<CityResponseDto>>> getCitiesByDepartmentCode(
            @Parameter(description = "Código del departamento", example = "DC")
            @PathVariable String departmentCode) {

        logger.info("Solicitud para buscar ciudades por código de departamento: {}", departmentCode);

        try {
            List<CityResponseDto> cities = cityService.getCitiesByDepartmentCode(departmentCode);

            if (cities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron ciudades en el departamento: " + departmentCode));
            }

            ApiResponseDto<List<CityResponseDto>> response = ApiResponseDto.success(
                    cities,
                    "Se encontraron " + cities.size() + " ciudades en el departamento " + departmentCode
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar ciudades por departamento: {}", departmentCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar ciudades"));
        }
    }

    /**
     * Busca ciudades por nombre
     */
    @GetMapping("/by-name")
    @Operation(summary = "Buscar ciudades por nombre",
            description = "Busca ciudades que contengan el nombre especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron ciudades"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<CityResponseDto>>> getCitiesByName(
            @Parameter(description = "Nombre o parte del nombre de la ciudad")
            @RequestParam String name) {

        logger.info("Solicitud para buscar ciudades por nombre: {}", name);

        try {
            List<CityResponseDto> cities = cityService.getCitiesByName(name);

            if (cities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron ciudades que contengan: " + name));
            }

            ApiResponseDto<List<CityResponseDto>> response = ApiResponseDto.success(
                    cities,
                    "Se encontraron " + cities.size() + " ciudades que contienen '" + name + "'"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar ciudades por nombre: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar ciudades"));
        }
    }
}