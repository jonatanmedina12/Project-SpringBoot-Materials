package com.management.materials.repository;

import com.management.materials.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Department
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

    /**
     * Busca departamentos por nombre (búsqueda parcial)
     */
    List<Department> findByNameContainingIgnoreCase(String name);

    /**
     * Busca departamentos ordenados por nombre
     */
    List<Department> findAllByOrderByNameAsc();
}