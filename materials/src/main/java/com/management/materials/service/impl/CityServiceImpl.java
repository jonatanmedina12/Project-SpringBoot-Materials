package com.management.materials.service.impl;

import com.management.materials.dto.response.CityResponseDto;
import com.management.materials.dto.response.DepartmentResponseDto;
import com.management.materials.entity.City;
import com.management.materials.exception.ResourceNotFoundException;
import com.management.materials.repository.CityRepository;
import com.management.materials.service.ICityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de ciudades
 */
@Service
@Transactional
public class CityServiceImpl implements ICityService {

    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityResponseDto> getAllCities() {
        logger.info("Obteniendo todas las ciudades");

        List<City> cities = cityRepository.findAllByOrderByNameAsc();

        logger.info("Se encontraron {} ciudades", cities.size());
        return cities.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CityResponseDto getCityByCode(String code) {
        logger.info("Buscando ciudad con código: {}", code);

        City city = cityRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con código: " + code));

        logger.info("Ciudad encontrada: {}", city.getName());
        return convertToResponseDto(city);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityResponseDto> getCitiesByDepartmentCode(String departmentCode) {
        logger.info("Buscando ciudades por código de departamento: {}", departmentCode);

        List<City> cities = cityRepository.findByDepartmentCode(departmentCode);

        logger.info("Se encontraron {} ciudades en el departamento {}", cities.size(), departmentCode);
        return cities.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityResponseDto> getCitiesByName(String name) {
        logger.info("Buscando ciudades por nombre: {}", name);

        List<City> cities = cityRepository.findByNameContainingIgnoreCase(name);

        logger.info("Se encontraron {} ciudades que contienen '{}'", cities.size(), name);
        return cities.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad City a CityResponseDto
     */
    private CityResponseDto convertToResponseDto(City city) {
        CityResponseDto dto = new CityResponseDto();
        dto.setCode(city.getCode());
        dto.setName(city.getName());

        // Convertir departamento
        DepartmentResponseDto departmentDto = new DepartmentResponseDto();
        departmentDto.setCode(city.getDepartment().getCode());
        departmentDto.setName(city.getDepartment().getName());

        dto.setDepartment(departmentDto);

        return dto;
    }
}
