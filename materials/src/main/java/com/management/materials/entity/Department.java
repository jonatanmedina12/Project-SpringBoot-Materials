package com.management.materials.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Entidad que representa un Departamento
 */
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @NotBlank(message = "El código del departamento es obligatorio")
    @Size(min = 2, max = 10, message = "El código debe tener entre 2 y 10 caracteres")
    @Column(name = "code", length = 10)
    private String code;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<City> cities;

    // Constructores
    public Department() {}

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters y Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<City> getCities() { return cities; }
    public void setCities(List<City> cities) { this.cities = cities; }
}