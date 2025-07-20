package org.example.finaljavafx.dao;

import org.example.finaljavafx.models.Usuario;
import java.util.List;

public interface UsuariosDAO {
    void guardarUsuario(Usuario usuario);
    void guardarUsuarios(List<Usuario> usuarios);
    List<Usuario> obtenerUsuarios();
}
