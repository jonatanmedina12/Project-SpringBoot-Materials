package com.management.materials.dto.response;

/**
 * DTO de respuesta para Department
 */
public class DepartmentResponseDto {

    private String code;
    private String name;

    public DepartmentResponseDto() {}

    public DepartmentResponseDto(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters y Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}