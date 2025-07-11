package com.management.materials.dto.request;

import com.management.materials.enums.MaterialStatus;
import com.management.materials.enums.MaterialType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de request para crear o actualizar un Material
 */
public class MaterialRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String description;

    @NotNull(message = "El tipo es obligatorio")
    private MaterialType type;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @NotNull(message = "La fecha de compra es obligatoria")
    private LocalDate purchaseDate;

    private LocalDate saleDate;

    @NotNull(message = "El estado es obligatorio")
    private MaterialStatus status;

    @NotBlank(message = "El código de ciudad es obligatorio")
    private String cityCode;

    public MaterialRequestDto() {}

    public MaterialRequestDto(String name, String description, MaterialType type, BigDecimal price,
                              LocalDate purchaseDate, LocalDate saleDate, MaterialStatus status, String cityCode) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.saleDate = saleDate;
        this.status = status;
        this.cityCode = cityCode;
    }

    // Validación personalizada para fechas
    @AssertTrue(message = "La fecha de venta no puede ser anterior a la fecha de compra")
    public boolean isValidDates() {
        if (saleDate == null || purchaseDate == null) {
            return true;
        }
        return !saleDate.isBefore(purchaseDate);
    }

    // Getters y Setters
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

    public String getCityCode() { return cityCode; }
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }
}