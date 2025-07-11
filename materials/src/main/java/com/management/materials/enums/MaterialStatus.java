package com.management.materials.enums;

/**
 * Enum que define los estados posibles de un material
 */
public enum MaterialStatus {
    ACTIVE("Activo"),
    AVAILABLE("Disponible"),
    ASSIGNED("Asignado"),
    INACTIVE("Inactivo"),
    DAMAGED("Dañado"),
    SOLD("Vendido");

    private final String displayName;

    MaterialStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}