package com.management.materials.service;

import com.management.materials.dto.response.CityResponseDto;

import java.util.List;

/**
 * Interface para el servicio de gestión de ciudades
 */
public interface ICityService {

    /**
     * Obtiene todas las ciudades
     *
     * @return Lista de todas las ciudades
     */
    List<CityResponseDto> getAllCities();

    /**
     * Obtiene una ciudad por su código
     *
     * @param code Código de la ciudad
     * @return Ciudad encontrada
     */
    CityResponseDto getCityByCode(String code);

    /**
     * Busca ciudades por código de departamento
     *
     * @param departmentCode Código del departamento
     * @return Lista de ciudades del departamento especificado
     */
    List<CityResponseDto> getCitiesByDepartmentCode(String departmentCode);

    /**
     * Busca ciudades por nombre (búsqueda parcial)
     *
     * @param name Nombre o parte del nombre de la ciudad
     * @return Lista de ciudades que contienen el nombre especificado
     */
    List<CityResponseDto> getCitiesByName(String name);
}