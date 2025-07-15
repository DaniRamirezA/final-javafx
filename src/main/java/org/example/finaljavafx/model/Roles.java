package org.example.finaljavafx.model;

public enum Roles {
    ADMINISTRADOR("Administrador"),
    CAJERO("Cajero");

    private final String rol;

    Roles(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public static Roles fromString(String rol) {
        for (Roles r : Roles.values()) {
            if (r.rol.equalsIgnoreCase(rol)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Rol no válido: " + rol);
    }
}
