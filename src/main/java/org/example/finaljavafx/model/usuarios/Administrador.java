package org.example.finaljavafx.model.usuarios;

import org.example.finaljavafx.model.Perfil;
import org.example.finaljavafx.model.Roles;
import org.example.finaljavafx.model.Usuario;

public class Administrador extends Usuario {
    public Administrador(String usuario, String contrasena, Perfil perfil) {
        super(usuario, contrasena, perfil, Roles.ADMINISTRADOR);
    }
}
