package com.management.materials.controller;


import com.management.materials.dto.request.MaterialRequestDto;
import com.management.materials.dto.request.MaterialSearchDto;
import com.management.materials.dto.response.ApiResponseDto;
import com.management.materials.dto.response.MaterialResponseDto;
import com.management.materials.enums.MaterialType;
import com.management.materials.service.IMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestión de materiales
 */
@RestController
@RequestMapping("/api/materials")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Materiales", description = "Operaciones para gestión de materiales")
public class MaterialController {

    private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);

    private final IMaterialService materialService;

    public MaterialController(IMaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * Obtiene todos los materiales
     */
    @GetMapping
    @Operation(summary = "Obtener todos los materiales",
            description = "Retorna la lista completa de materiales del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de materiales obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponseDto<List<MaterialResponseDto>>> getAllMaterials() {
        logger.info("Solicitud para obtener todos los materiales");

        try {
            List<MaterialResponseDto> materials = materialService.getAllMaterials();

            if (materials.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron materiales"));
            }

            ApiResponseDto<List<MaterialResponseDto>> response = ApiResponseDto.success(
                    materials,
                    "Materiales obtenidos exitosamente. Total: " + materials.size()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al obtener todos los materiales", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al obtener materiales"));
        }
    }

    /**
     * Obtiene un material por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener material por ID",
            description = "Retorna un material específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material encontrado"),
            @ApiResponse(responseCode = "404", description = "Material no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<MaterialResponseDto>> getMaterialById(
            @Parameter(description = "ID del material", example = "1")
            @PathVariable Long id) {

        logger.info("Solicitud para obtener material con ID: {}", id);

        try {
            MaterialResponseDto material = materialService.getMaterialById(id);

            ApiResponseDto<MaterialResponseDto> response = ApiResponseDto.success(
                    material,
                    "Material encontrado"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar material con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error("Material no encontrado con ID: " + id));
        }
    }

    /**
     * Crea un nuevo material
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Crear nuevo material",
            description = "Crea un nuevo material en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<ApiResponseDto<MaterialResponseDto>> createMaterial(
            @Parameter(description = "Datos del material a crear")
            @Valid @RequestBody MaterialRequestDto materialRequest) {

        logger.info("Solicitud para crear nuevo material: {}", materialRequest.getName());

        try {
            MaterialResponseDto createdMaterial = materialService.createMaterial(materialRequest);

            ApiResponseDto<MaterialResponseDto> response = ApiResponseDto.success(
                    createdMaterial,
                    "Material creado exitosamente"
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("Error al crear material: {}", materialRequest.getName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al crear material"));
        }
    }

    /**
     * Actualiza un material existente
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Actualizar material",
            description = "Actualiza un material existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Material no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<ApiResponseDto<MaterialResponseDto>> updateMaterial(
            @Parameter(description = "ID del material", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del material")
            @Valid @RequestBody MaterialRequestDto materialRequest) {

        logger.info("Solicitud para actualizar material con ID: {}", id);

        try {
            MaterialResponseDto updatedMaterial = materialService.updateMaterial(id, materialRequest);

            ApiResponseDto<MaterialResponseDto> response = ApiResponseDto.success(
                    updatedMaterial,
                    "Material actualizado exitosamente"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al actualizar material con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error("Material no encontrado con ID: " + id));
        }
    }

    /**
     * Elimina un material
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar material",
            description = "Elimina un material del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Material no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<ApiResponseDto<String>> deleteMaterial(
            @Parameter(description = "ID del material", example = "1")
            @PathVariable Long id) {

        logger.info("Solicitud para eliminar material con ID: {}", id);

        try {
            materialService.deleteMaterial(id);

            ApiResponseDto<String> response = ApiResponseDto.success(
                    "OK",
                    "Material eliminado exitosamente"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al eliminar material con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error("Material no encontrado con ID: " + id));
        }
    }

    /**
     * Busca materiales por tipo
     */
    @GetMapping("/by-type/{type}")
    @Operation(summary = "Buscar materiales por tipo",
            description = "Retorna materiales filtrados por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron materiales"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<MaterialResponseDto>>> getMaterialsByType(
            @Parameter(description = "Tipo de material")
            @PathVariable MaterialType type) {

        logger.info("Solicitud para buscar materiales por tipo: {}", type);

        try {
            List<MaterialResponseDto> materials = materialService.getMaterialsByType(type);

            if (materials.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron materiales del tipo: " + type));
            }

            ApiResponseDto<List<MaterialResponseDto>> response = ApiResponseDto.success(
                    materials,
                    "Se encontraron " + materials.size() + " materiales del tipo " + type
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar materiales por tipo: {}", type, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar materiales"));
        }
    }

    /**
     * Busca materiales por fecha de compra
     */
    @GetMapping("/by-purchase-date/{purchaseDate}")
    @Operation(summary = "Buscar materiales por fecha de compra",
            description = "Retorna materiales filtrados por fecha de compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron materiales"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<MaterialResponseDto>>> getMaterialsByPurchaseDate(
            @Parameter(description = "Fecha de compra (formato: yyyy-MM-dd)", example = "2024-01-15")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDate) {

        logger.info("Solicitud para buscar materiales por fecha de compra: {}", purchaseDate);

        try {
            List<MaterialResponseDto> materials = materialService.getMaterialsByPurchaseDate(purchaseDate);

            if (materials.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron materiales comprados en: " + purchaseDate));
            }

            ApiResponseDto<List<MaterialResponseDto>> response = ApiResponseDto.success(
                    materials,
                    "Se encontraron " + materials.size() + " materiales comprados en " + purchaseDate
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar materiales por fecha de compra: {}", purchaseDate, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar materiales"));
        }
    }

    /**
     * Busca materiales por código de ciudad
     */
    @GetMapping("/by-city/{cityCode}")
    @Operation(summary = "Buscar materiales por ciudad",
            description = "Retorna materiales filtrados por código de ciudad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron materiales"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<MaterialResponseDto>>> getMaterialsByCityCode(
            @Parameter(description = "Código de la ciudad", example = "BOG")
            @PathVariable String cityCode) {

        logger.info("Solicitud para buscar materiales por código de ciudad: {}", cityCode);

        try {
            List<MaterialResponseDto> materials = materialService.getMaterialsByCityCode(cityCode);

            if (materials.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron materiales en la ciudad: " + cityCode));
            }

            ApiResponseDto<List<MaterialResponseDto>> response = ApiResponseDto.success(
                    materials,
                    "Se encontraron " + materials.size() + " materiales en la ciudad " + cityCode
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar materiales por ciudad: {}", cityCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar materiales"));
        }
    }

    /**
     * Busca materiales por código de departamento
     */
    @GetMapping("/by-department/{departmentCode}")
    @Operation(summary = "Buscar materiales por departamento",
            description = "Retorna materiales filtrados por código de departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron materiales"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<MaterialResponseDto>>> getMaterialsByDepartmentCode(
            @Parameter(description = "Código del departamento", example = "DC")
            @PathVariable String departmentCode) {

        logger.info("Solicitud para buscar materiales por código de departamento: {}", departmentCode);

        try {
            List<MaterialResponseDto> materials = materialService.getMaterialsByDepartmentCode(departmentCode);

            if (materials.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron materiales en el departamento: " + departmentCode));
            }

            ApiResponseDto<List<MaterialResponseDto>> response = ApiResponseDto.success(
                    materials,
                    "Se encontraron " + materials.size() + " materiales en el departamento " + departmentCode
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar materiales por departamento: {}", departmentCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar materiales"));
        }
    }

    /**
     * Búsqueda avanzada de materiales con múltiples filtros
     */
    @GetMapping("/search")
    @Operation(summary = "Búsqueda avanzada de materiales",
            description = "Busca materiales usando múltiples filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron materiales"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<MaterialResponseDto>>> searchMaterials(
            @Parameter(description = "Tipo de material") @RequestParam(required = false) MaterialType type,
            @Parameter(description = "Fecha de compra") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDate,
            @Parameter(description = "Código de ciudad") @RequestParam(required = false) String cityCode,
            @Parameter(description = "Código de departamento") @RequestParam(required = false) String departmentCode) {

        logger.info("Solicitud de búsqueda avanzada: tipo={}, fecha={}, ciudad={}, departamento={}",
                type, purchaseDate, cityCode, departmentCode);

        try {
            MaterialSearchDto searchDto = new MaterialSearchDto(type, purchaseDate, cityCode, departmentCode);
            List<MaterialResponseDto> materials = materialService.searchMaterials(searchDto);

            if (materials.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron materiales con los criterios especificados"));
            }

            ApiResponseDto<List<MaterialResponseDto>> response = ApiResponseDto.success(
                    materials,
                    "Se encontraron " + materials.size() + " materiales con los criterios especificados"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error en búsqueda avanzada de materiales", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar materiales"));
        }
    }

    /**
     * Busca materiales por nombre
     */
    @GetMapping("/by-name")
    @Operation(summary = "Buscar materiales por nombre",
            description = "Busca materiales que contengan el nombre especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron materiales"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponseDto<List<MaterialResponseDto>>> getMaterialsByName(
            @Parameter(description = "Nombre o parte del nombre del material")
            @RequestParam String name) {

        logger.info("Solicitud para buscar materiales por nombre: {}", name);

        try {
            List<MaterialResponseDto> materials = materialService.getMaterialsByName(name);

            if (materials.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("No se encontraron materiales que contengan: " + name));
            }

            ApiResponseDto<List<MaterialResponseDto>> response = ApiResponseDto.success(
                    materials,
                    "Se encontraron " + materials.size() + " materiales que contienen '" + name + "'"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al buscar materiales por nombre: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("Error interno al buscar materiales"));
        }
    }
}