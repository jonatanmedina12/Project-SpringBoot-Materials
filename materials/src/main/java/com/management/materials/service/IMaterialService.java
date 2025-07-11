package com.management.materials.service;

import com.management.materials.dto.request.MaterialRequestDto;
import com.management.materials.dto.request.MaterialSearchDto;
import com.management.materials.dto.response.MaterialResponseDto;
import com.management.materials.enums.MaterialType;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface para el servicio de gestión de materiales
 */
public interface IMaterialService {

    /**
     * Obtiene todos los materiales
     *
     * @return Lista de todos los materiales
     */
    List<MaterialResponseDto> getAllMaterials();

    /**
     * Obtiene un material por su ID
     *
     * @param id ID del material
     * @return Material encontrado
     */
    MaterialResponseDto getMaterialById(Long id);

    /**
     * Crea un nuevo material
     *
     * @param materialRequest Datos del material a crear
     * @return Material creado
     */
    MaterialResponseDto createMaterial(MaterialRequestDto materialRequest);

    /**
     * Actualiza un material existente
     *
     * @param id ID del material a actualizar
     * @param materialRequest Nuevos datos del material
     * @return Material actualizado
     */
    MaterialResponseDto updateMaterial(Long id, MaterialRequestDto materialRequest);

    /**
     * Elimina un material
     *
     * @param id ID del material a eliminar
     */
    void deleteMaterial(Long id);

    /**
     * Busca materiales por tipo
     *
     * @param type Tipo de material
     * @return Lista de materiales del tipo especificado
     */
    List<MaterialResponseDto> getMaterialsByType(MaterialType type);

    /**
     * Busca materiales por fecha de compra
     *
     * @param purchaseDate Fecha de compra
     * @return Lista de materiales comprados en la fecha especificada
     */
    List<MaterialResponseDto> getMaterialsByPurchaseDate(LocalDate purchaseDate);

    /**
     * Busca materiales por código de ciudad
     *
     * @param cityCode Código de la ciudad
     * @return Lista de materiales de la ciudad especificada
     */
    List<MaterialResponseDto> getMaterialsByCityCode(String cityCode);

    /**
     * Busca materiales por código de departamento
     *
     * @param departmentCode Código del departamento
     * @return Lista de materiales del departamento especificado
     */
    List<MaterialResponseDto> getMaterialsByDepartmentCode(String departmentCode);

    /**
     * Busca materiales con filtros múltiples
     *
     * @param searchDto Criterios de búsqueda
     * @return Lista de materiales que cumplen los criterios
     */
    List<MaterialResponseDto> searchMaterials(MaterialSearchDto searchDto);

    /**
     * Busca materiales por nombre (búsqueda parcial)
     *
     * @param name Nombre o parte del nombre del material
     * @return Lista de materiales que contienen el nombre especificado
     */
    List<MaterialResponseDto> getMaterialsByName(String name);
}