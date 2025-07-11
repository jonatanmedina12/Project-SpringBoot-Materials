package com.management.materials.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Entidad que representa una Ciudad
 */
@Entity
@Table(name = "cities")
public class City {

    @Id
    @NotBlank(message = "El código de la ciudad es obligatorio")
    @Size(min = 2, max = 10, message = "El código debe tener entre 2 y 10 caracteres")
    @Column(name = "code", length = 10)
    private String code;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_code", referencedColumnName = "code", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Material> materials;

    // Constructores
    public City() {}

    public City(String code, String name, Department department) {
        this.code = code;
        this.name = name;
        this.department = department;
    }

    // Getters y Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public List<Material> getMaterials() { return materials; }
    public void setMaterials(List<Material> materials) { this.materials = materials; }
}