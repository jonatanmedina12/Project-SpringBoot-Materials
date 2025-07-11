package com.management.materials.service;

import com.management.materials.dto.response.DepartmentResponseDto;

import java.util.List;

/**
 * Interface para el servicio de gestión de departamentos
 */
public interface IDepartmentService {
    /**
     * Obtiene todos los departamentos
     *
     * @return Lista de todos los departamentos
     */
    List<DepartmentResponseDto> getAllDepartments();

    /**
     * Obtiene un departamento por su código
     *
     * @param code Código del departamento
     * @return Departamento encontrado
     */
    DepartmentResponseDto getDepartmentByCode(String code);

    /**
     * Busca departamentos por nombre (búsqueda parcial)
     *
     * @param name Nombre o parte del nombre del departamento
     * @return Lista de departamentos que contienen el nombre especificado
     */
    List<DepartmentResponseDto> getDepartmentsByName(String name);
}
