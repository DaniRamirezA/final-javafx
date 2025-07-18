package org.example.finaljavafx.ADaniela.models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String username;
    private String password;
    private String rol; // "admin" o "trabajador"

    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
}
