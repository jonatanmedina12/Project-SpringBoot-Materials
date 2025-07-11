package com.management.materials.service.impl;

import com.management.materials.dto.request.MaterialRequestDto;
import com.management.materials.dto.request.MaterialSearchDto;
import com.management.materials.dto.response.CityResponseDto;
import com.management.materials.dto.response.DepartmentResponseDto;
import com.management.materials.dto.response.MaterialResponseDto;
import com.management.materials.entity.City;
import com.management.materials.entity.Material;
import com.management.materials.enums.MaterialType;
import com.management.materials.exception.ResourceNotFoundException;
import com.management.materials.repository.CityRepository;
import com.management.materials.repository.MaterialRepository;
import com.management.materials.service.IMaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de materiales
 */
@Service
@Transactional
public class MaterialServiceImpl implements IMaterialService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final MaterialRepository materialRepository;
    private final CityRepository cityRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository, CityRepository cityRepository) {
        this.materialRepository = materialRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> getAllMaterials() {
        logger.info("Obteniendo todos los materiales");

        List<Material> materials = materialRepository.findAllByOrderByPurchaseDateDesc();

        logger.info("Se encontraron {} materiales", materials.size());
        return materials.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialResponseDto getMaterialById(Long id) {
        logger.info("Buscando material con ID: {}", id);

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado con ID: " + id));

        logger.info("Material encontrado: {}", material.getName());
        return convertToResponseDto(material);
    }

    @Override
    public MaterialResponseDto createMaterial(MaterialRequestDto materialRequest) {
        logger.info("Creando nuevo material: {}", materialRequest.getName());

        // Validar que la ciudad existe
        City city = cityRepository.findById(materialRequest.getCityCode())
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con código: " + materialRequest.getCityCode()));

        // Crear entidad Material
        Material material = new Material();
        material.setName(materialRequest.getName());
        material.setDescription(materialRequest.getDescription());
        material.setType(materialRequest.getType());
        material.setPrice(materialRequest.getPrice());
        material.setPurchaseDate(materialRequest.getPurchaseDate());
        material.setSaleDate(materialRequest.getSaleDate());
        material.setStatus(materialRequest.getStatus());
        material.setCity(city);

        Material savedMaterial = materialRepository.save(material);

        logger.info("Material creado exitosamente con ID: {}", savedMaterial.getId());
        return convertToResponseDto(savedMaterial);
    }

    @Override
    public MaterialResponseDto updateMaterial(Long id, MaterialRequestDto materialRequest) {
        logger.info("Actualizando material con ID: {}", id);

        Material existingMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado con ID: " + id));

        // Validar que la ciudad existe
        City city = cityRepository.findById(materialRequest.getCityCode())
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con código: " + materialRequest.getCityCode()));

        // Actualizar campos
        existingMaterial.setName(materialRequest.getName());
        existingMaterial.setDescription(materialRequest.getDescription());
        existingMaterial.setType(materialRequest.getType());
        existingMaterial.setPrice(materialRequest.getPrice());
        existingMaterial.setPurchaseDate(materialRequest.getPurchaseDate());
        existingMaterial.setSaleDate(materialRequest.getSaleDate());
        existingMaterial.setStatus(materialRequest.getStatus());
        existingMaterial.setCity(city);

        Material updatedMaterial = materialRepository.save(existingMaterial);

        logger.info("Material actualizado exitosamente: {}", updatedMaterial.getName());
        return convertToResponseDto(updatedMaterial);
    }

    @Override
    public void deleteMaterial(Long id) {
        logger.info("Eliminando material con ID: {}", id);

        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Material no encontrado con ID: " + id);
        }

        materialRepository.deleteById(id);
        logger.info("Material eliminado exitosamente");
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> getMaterialsByType(MaterialType type) {
        logger.info("Buscando materiales por tipo: {}", type);

        List<Material> materials = materialRepository.findByType(type);

        logger.info("Se encontraron {} materiales del tipo {}", materials.size(), type);
        return materials.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> getMaterialsByPurchaseDate(LocalDate purchaseDate) {
        logger.info("Buscando materiales por fecha de compra: {}", purchaseDate);

        List<Material> materials = materialRepository.findByPurchaseDate(purchaseDate);

        logger.info("Se encontraron {} materiales comprados en {}", materials.size(), purchaseDate);
        return materials.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> getMaterialsByCityCode(String cityCode) {
        logger.info("Buscando materiales por código de ciudad: {}", cityCode);

        List<Material> materials = materialRepository.findByCityCode(cityCode);

        logger.info("Se encontraron {} materiales en la ciudad {}", materials.size(), cityCode);
        return materials.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> getMaterialsByDepartmentCode(String departmentCode) {
        logger.info("Buscando materiales por código de departamento: {}", departmentCode);

        List<Material> materials = materialRepository.findByDepartmentCode(departmentCode);

        logger.info("Se encontraron {} materiales en el departamento {}", materials.size(), departmentCode);
        return materials.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> searchMaterials(MaterialSearchDto searchDto) {
        logger.info("Buscando materiales con filtros: tipo={}, fecha={}, ciudad={}, departamento={}",
                searchDto.getType(), searchDto.getPurchaseDate(), searchDto.getCityCode(), searchDto.getDepartmentCode());

        List<Material> materials = materialRepository.findByFilters(
                searchDto.getType(),
                searchDto.getPurchaseDate(),
                searchDto.getCityCode(),
                searchDto.getDepartmentCode()
        );

        logger.info("Se encontraron {} materiales con los filtros aplicados", materials.size());
        return materials.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> getMaterialsByName(String name) {
        logger.info("Buscando materiales por nombre: {}", name);

        List<Material> materials = materialRepository.findByNameContainingIgnoreCase(name);

        logger.info("Se encontraron {} materiales que contienen '{}'", materials.size(), name);
        return materials.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Material a MaterialResponseDto
     */
    private MaterialResponseDto convertToResponseDto(Material material) {
        MaterialResponseDto dto = new MaterialResponseDto();
        dto.setId(material.getId());
        dto.setName(material.getName());
        dto.setDescription(material.getDescription());
        dto.setType(material.getType());
        dto.setPrice(material.getPrice());
        dto.setPurchaseDate(material.getPurchaseDate());
        dto.setSaleDate(material.getSaleDate());
        dto.setStatus(material.getStatus());

        // Convertir ciudad
        CityResponseDto cityDto = new CityResponseDto();
        cityDto.setCode(material.getCity().getCode());
        cityDto.setName(material.getCity().getName());

        // Convertir departamento
        DepartmentResponseDto departmentDto = new DepartmentResponseDto();
        departmentDto.setCode(material.getCity().getDepartment().getCode());
        departmentDto.setName(material.getCity().getDepartment().getName());

        cityDto.setDepartment(departmentDto);
        dto.setCity(cityDto);

        return dto;
    }
}