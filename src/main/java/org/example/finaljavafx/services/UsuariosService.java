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

    public void actualizarUsuario(Usuario usuarioActualizado) {
        if (usuarioActualizado.getUsername().equals("admin")) {
            throw new IllegalStateException("No se puede modificar el administrador principal");
        }

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario actual = usuarios.get(i);
            if (actual.getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }

        usuariosDAO.guardarUsuarios(usuarios);
    }

    public boolean existeUsuario(String username) {
        return usuarios.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public Usuario autenticar(String username, String password) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
