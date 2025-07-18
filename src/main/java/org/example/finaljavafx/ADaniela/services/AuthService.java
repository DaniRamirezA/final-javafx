package org.example.finaljavafx.ADaniela.services;

import org.example.finaljavafx.ADaniela.dao.UsuariosDAO;
import org.example.finaljavafx.ADaniela.dao.impl.IO.UsuariosDAOIO;
import org.example.finaljavafx.ADaniela.models.Usuario;

import java.util.List;

public class AuthService {
    private final UsuariosDAO usuariosDAO = new UsuariosDAOIO();

    public boolean validarCredenciales(String username, String password) {
        List<Usuario> usuarios = usuariosDAO.obtenerUsuarios();
        return usuarios.stream()
                .anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
    }

    public Usuario autenticar(String usuario, String contrasena) {
        List<Usuario> usuarios = usuariosDAO.obtenerUsuarios();
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(usuario) && u.getPassword().equals(contrasena))
                .findFirst()
                .orElse(null);
    }
}
