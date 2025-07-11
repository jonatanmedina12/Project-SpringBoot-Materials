package com.management.materials.service.impl;

import com.management.materials.dto.response.DepartmentResponseDto;
import com.management.materials.entity.Department;
import com.management.materials.exception.ResourceNotFoundException;
import com.management.materials.repository.DepartmentRepository;
import com.management.materials.service.IDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de departamentos
 */
@Service
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> getAllDepartments() {
        logger.info("Obteniendo todos los departamentos");

        List<Department> departments = departmentRepository.findAllByOrderByNameAsc();

        logger.info("Se encontraron {} departamentos", departments.size());
        return departments.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponseDto getDepartmentByCode(String code) {
        logger.info("Buscando departamento con código: {}", code);

        Department department = departmentRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con código: " + code));

        logger.info("Departamento encontrado: {}", department.getName());
        return convertToResponseDto(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> getDepartmentsByName(String name) {
        logger.info("Buscando departamentos por nombre: {}", name);

        List<Department> departments = departmentRepository.findByNameContainingIgnoreCase(name);

        logger.info("Se encontraron {} departamentos que contienen '{}'", departments.size(), name);
        return departments.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Department a DepartmentResponseDto
     */
    private DepartmentResponseDto convertToResponseDto(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setCode(department.getCode());
        dto.setName(department.getName());

        return dto;
    }
}