package org.example.finaljavafx.dao;

import org.example.finaljavafx.model.Usuario;

import java.util.List;

public interface UsuariosDAO {
    void guardarUsuario(Usuario usuario);

    List<Usuario> obtenerUsuarios();
}
