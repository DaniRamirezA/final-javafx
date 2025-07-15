package org.example.finaljavafx.dao.impl.usuarios;

import org.example.finaljavafx.dao.UsuariosDAO;
import org.example.finaljavafx.dao.impl.DatosIO;
import org.example.finaljavafx.model.Usuario;

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
        String usuarioString = producto.getNombre() + "," + producto.getDescripcion() + "," + producto.getPorcentaje() + "\n";
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
        String[] partes = linea.split(",");
        return new Usuario(partes[0], partes[1], Float.parseFloat(partes[2]));
    }
}
