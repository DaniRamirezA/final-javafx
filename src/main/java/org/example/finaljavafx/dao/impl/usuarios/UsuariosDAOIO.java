package org.example.finaljavafx.dao.impl.usuarios;

import org.example.finaljavafx.dao.UsuariosDAO;
import org.example.finaljavafx.dao.impl.DatosIO;
import org.example.finaljavafx.model.Perfil;
import org.example.finaljavafx.model.Usuario;
import org.example.finaljavafx.model.usuarios.Administrador;
import org.example.finaljavafx.model.usuarios.Cajero;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAOIO extends DatosIO implements UsuariosDAO {

    private File usuariosDB;

    public UsuariosDAOIO() {
        System.out.println("Inicializando la base de datos de usuarios en disco con IO");
        // Inicializar la base de datos de notas
        this.usuariosDB = new File("usuarios");
        if (!usuariosDB.exists()) {
            try {
                usuariosDB.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo usuarios: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        String usuarioString = usuario.toString() + "\n";
        FileWriter fw;
        BufferedWriter bw;
        try {
            fw = new FileWriter(usuariosDB, true);
            bw = new BufferedWriter(fw);
            bw.write(usuarioString);
            bw.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo productos: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> resultado = new ArrayList<>();
        try {
            FileReader fr = new FileReader(usuariosDB);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario usuario = transformString(linea);
                resultado.add(usuario);
            }
            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    protected Usuario transformString(String linea) {
        String[] partes = linea.split("#");

        String[] perfilString = partes[2].split("-");

        Perfil perfil = new Perfil(perfilString[0], perfilString[1], perfilString[2], perfilString[3]);

        if (linea.contains("Administrador")) {
            return new Administrador(partes[0], partes[1], perfil);
        } else if (linea.contains("Usuario")) {
            return new Cajero(partes[0], partes[1], perfil);
        }

        return null;
    }
}
