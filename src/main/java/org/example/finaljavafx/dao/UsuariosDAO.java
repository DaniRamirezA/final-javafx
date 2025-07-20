package org.example.finaljavafx.dao;

import org.example.finaljavafx.models.Usuario;
import java.util.List;

public interface UsuariosDAO {
    void guardarUsuarios(List<Usuario> usuarios);
    List<Usuario> obtenerUsuarios();
}
