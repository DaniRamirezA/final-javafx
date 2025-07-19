package org.example.finaljavafx.ADaniela.dao.impl.IO;

import org.example.finaljavafx.ADaniela.dao.UsuariosDAO;
import org.example.finaljavafx.ADaniela.dao.impl.IO.UsuariosDAOIO;
import org.example.finaljavafx.ADaniela.models.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAOIO implements UsuariosDAO {

    private static final String ARCHIVO_USUARIOS = "data/usuarios.txt";

    public UsuariosDAOIO() {
        File archivo = new File(ARCHIVO_USUARIOS);
        File carpeta = archivo.getParentFile();

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de usuarios: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            writer.write(String.join(",",
                    usuario.getUsername(),
                    usuario.getPassword(),
                    usuario.getRol()));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void guardarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            for (Usuario usuario : usuarios) {
                writer.write(String.join(",",
                        usuario.getUsername(),
                        usuario.getPassword(),
                        usuario.getRol()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar la lista de usuarios: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File archivo = new File(ARCHIVO_USUARIOS);

        if (!archivo.exists()) {
            return usuarios;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    usuarios.add(new Usuario(
                            partes[0].trim(),
                            partes[1].trim(),
                            partes[2].trim()
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer usuarios: " + e.getMessage());
        }

        return usuarios;
    }
}
