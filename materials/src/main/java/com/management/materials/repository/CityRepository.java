package com.management.materials.repository;

import com.management.materials.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad City
 */
@Repository
public interface CityRepository extends JpaRepository<City, String> {

    /**
     * Busca ciudades por código de departamento
     */
    @Query("SELECT c FROM City c WHERE c.department.code = :departmentCode")
    List<City> findByDepartmentCode(@Param("departmentCode") String departmentCode);

    /**
     * Busca ciudades por nombre (búsqueda parcial)
     */
    List<City> findByNameContainingIgnoreCase(String name);

    /**
     * Busca ciudades ordenadas por nombre
     */
    List<City> findAllByOrderByNameAsc();
}