package org.example.finaljavafx.model;

public abstract class Usuario {
    private String usuario;
    private String contrasena;
    private Perfil perfil;
    private Roles rol;

    public Usuario(String usuario, String contrasena, Perfil perfil, Roles rol) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.perfil = perfil;
        this.rol = rol;
    }

    @Override
    public String toString() {
        return usuario + "#" + contrasena + "#" + perfil.toString() + "#" + rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }
}
