package org.example.finaljavafx.ADaniela.dao;

import org.example.finaljavafx.ADaniela.models.Usuario;
import java.util.List;

public interface UsuariosDAO {
    void guardarUsuario(Usuario usuario); // guarda uno solo
    void guardarUsuarios(List<Usuario> usuarios); // guarda varios
    List<Usuario> obtenerUsuarios(); // carga todos
}
