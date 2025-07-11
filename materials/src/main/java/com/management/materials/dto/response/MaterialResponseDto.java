package com.management.materials.dto.response;

import com.management.materials.enums.MaterialStatus;
import com.management.materials.enums.MaterialType;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de respuesta para Material
 */
public class MaterialResponseDto {

    private Long id;
    private String name;
    private String description;
    private MaterialType type;
    private BigDecimal price;
    private LocalDate purchaseDate;
    private LocalDate saleDate;
    private MaterialStatus status;
    private CityResponseDto city;

    public MaterialResponseDto() {}

    public MaterialResponseDto(Long id, String name, String description, MaterialType type,
                               BigDecimal price, LocalDate purchaseDate, LocalDate saleDate,
                               MaterialStatus status, CityResponseDto city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.saleDate = saleDate;
        this.status = status;
        this.city = city;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MaterialType getType() { return type; }
    public void setType(MaterialType type) { this.type = type; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }

    public MaterialStatus getStatus() { return status; }
    public void setStatus(MaterialStatus status) { this.status = status; }

    public CityResponseDto getCity() { return city; }
    public void setCity(CityResponseDto city) { this.city = city; }
}