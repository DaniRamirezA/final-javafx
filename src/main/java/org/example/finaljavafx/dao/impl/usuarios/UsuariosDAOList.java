package org.example.finaljavafx.dao.impl.usuarios;

import org.example.finaljavafx.dao.UsuariosDAO;
import org.example.finaljavafx.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuariosDAOList implements UsuariosDAO {

    private static final List<Usuario> usuariosDB = new ArrayList<>();

    public UsuariosDAOList() {
        System.out.println("Inicializando la base de datos de usuarios en memoria");
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuariosDB.add(usuario);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuariosDB;
    }
}
