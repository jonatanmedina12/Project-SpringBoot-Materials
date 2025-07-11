package com.management.materials.dto.request;

import com.management.materials.enums.MaterialType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * DTO para búsqueda de materiales con filtros
 */
public class MaterialSearchDto {

    private MaterialType type;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate purchaseDate;

    private String cityCode;

    private String departmentCode;

    public MaterialSearchDto() {}

    public MaterialSearchDto(MaterialType type, LocalDate purchaseDate, String cityCode, String departmentCode) {
        this.type = type;
        this.purchaseDate = purchaseDate;
        this.cityCode = cityCode;
        this.departmentCode = departmentCode;
    }

    // Getters y Setters
    public MaterialType getType() { return type; }
    public void setType(MaterialType type) { this.type = type; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getCityCode() { return cityCode; }
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }
}