package com.management.materials.dto.response;

/**
 * DTO de respuesta para City
 */
public class CityResponseDto {

    private String code;
    private String name;
    private DepartmentResponseDto department;

    public CityResponseDto() {}

    public CityResponseDto(String code, String name, DepartmentResponseDto department) {
        this.code = code;
        this.name = name;
        this.department = department;
    }

    // Getters y Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public DepartmentResponseDto getDepartment() { return department; }
    public void setDepartment(DepartmentResponseDto department) { this.department = department; }
}
