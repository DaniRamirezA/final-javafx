package org.example.finaljavafx.CamiloDuran.dao;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class VentaDAO implements VentasDAO{
    private final File archivo;

    public VentaDAO(String ruta) {
        this.archivo = new File(ruta);
        try {
            if (!archivo.exists()) archivo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Map<String, int[]> cargarInventario() throws IOException {
        Map<String, int[]> inventario = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String nombre = partes[0];
                int cantidad = Integer.parseInt(partes[1]);
                int total = Integer.parseInt(partes[2]);
                inventario.put(nombre, new int[]{cantidad, total});
            }
        }
        return inventario;
    }
    @Override
    public void actualizarInventario(Map<String, int[]> inventario) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Map.Entry<String, int[]> entry : inventario.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue()[0] + "," + entry.getValue()[1]);
                bw.newLine();
            }
        }
    }
}
