package org.example.finaljavafx.services;

import org.example.finaljavafx.dao.UsuariosDAO;
import org.example.finaljavafx.dao.impl.IO.UsuariosDAOIO;
import org.example.finaljavafx.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuariosService {

    private final UsuariosDAO usuariosDAO = new UsuariosDAOIO();
    private final List<Usuario> usuarios;

    public UsuariosService() {
        this.usuarios = new ArrayList<>(usuariosDAO.obtenerUsuarios());
    }

    public List<Usuario> obtenerTodos() {
        return usuarios;
    }

    public void agregarUsuario(Usuario usuario) {
        if (existeUsuario(usuario.getUsername())) {
            throw new IllegalStateException("El usuario ya existe");
        }
        usuarios.add(usuario);
        usuariosDAO.guardarUsuarios(usuarios);
    }

    public void eliminarUsuario(String username) {
        if (username.equals("admin")) {
            throw new IllegalStateException("No se puede eliminar el administrador principal");
        }

        usuarios.removeIf(u -> u.getUsername().equals(username));
        usuariosDAO.guardarUsuarios(usuarios);
    }

    public boolean existeUsuario(String username) {
        return usuarios.stream().anyMatch(u -> u.getUsername().equals(username));
    }


}
