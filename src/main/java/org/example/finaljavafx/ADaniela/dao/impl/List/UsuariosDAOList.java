package org.example.finaljavafx.ADaniela.dao.impl.List;

import org.example.finaljavafx.ADaniela.dao.UsuariosDAO;
import org.example.finaljavafx.ADaniela.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuariosDAOList implements UsuariosDAO {

    private static final List<Usuario> usuariosDB = new ArrayList<>();

    public UsuariosDAOList() {
        System.out.println("Base de datos de usuarios en memoria (List)");
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuariosDB.add(usuario);
    }

    @Override
    public void guardarUsuarios(List<Usuario> usuarios) {
        usuariosDB.clear();             // Opcional: limpiar antes de agregar todo
        usuariosDB.addAll(usuarios);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return new ArrayList<>(usuariosDB); // Para evitar exponer la lista interna
    }
}
