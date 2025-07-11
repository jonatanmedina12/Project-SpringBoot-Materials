package com.management.materials.repository;

import com.management.materials.entity.Material;
import com.management.materials.enums.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la entidad Material
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    /**
     * Busca materiales por tipo
     */
    List<Material> findByType(MaterialType type);

    /**
     * Busca materiales por fecha de compra
     */
    List<Material> findByPurchaseDate(LocalDate purchaseDate);

    /**
     * Busca materiales por código de ciudad
     */
    @Query("SELECT m FROM Material m WHERE m.city.code = :cityCode")
    List<Material> findByCityCode(@Param("cityCode") String cityCode);

    /**
     * Busca materiales por código de departamento
     */
    @Query("SELECT m FROM Material m WHERE m.city.department.code = :departmentCode")
    List<Material> findByDepartmentCode(@Param("departmentCode") String departmentCode);

    /**
     * Búsqueda avanzada con múltiples filtros
     */
    @Query("SELECT m FROM Material m WHERE " +
            "(:type IS NULL OR m.type = :type) AND " +
            "(:purchaseDate IS NULL OR m.purchaseDate = :purchaseDate) AND " +
            "(:cityCode IS NULL OR m.city.code = :cityCode) AND " +
            "(:departmentCode IS NULL OR m.city.department.code = :departmentCode)")
    List<Material> findByFilters(@Param("type") MaterialType type,
                                 @Param("purchaseDate") LocalDate purchaseDate,
                                 @Param("cityCode") String cityCode,
                                 @Param("departmentCode") String departmentCode);

    /**
     * Busca materiales por nombre (búsqueda parcial)
     */
    List<Material> findByNameContainingIgnoreCase(String name);

    /**
     * Cuenta materiales por tipo
     */
    long countByType(MaterialType type);

    /**
     * Busca materiales ordenados por fecha de compra descendente
     */
    List<Material> findAllByOrderByPurchaseDateDesc();
}
