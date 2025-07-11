package com.management.materials.enums;

/**
 * Enum que define los tipos de materiales disponibles
 */
public enum MaterialType {
    ELECTRONICO("Electrónico"),
    MECANICO("Mecánico"),
    QUIMICO("Químico"),
    TEXTIL("Textil"),
    CONSTRUCCION("Construcción"),
    HERRAMIENTA("Herramienta"),
    OFICINA("Oficina");

    private final String displayName;

    MaterialType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
