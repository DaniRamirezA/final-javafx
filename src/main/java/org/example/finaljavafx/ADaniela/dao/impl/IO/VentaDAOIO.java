package org.example.finaljavafx.ADaniela.dao.impl.IO;

import org.example.finaljavafx.ADaniela.dao.VentasDAO;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class VentaDAOIO implements VentasDAO {
    private final String rutaArchivo;

    public VentaDAOIO(String ruta) {
        this.rutaArchivo = ruta;
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear archivo de ventas: " + e.getMessage());
            }
        }
    }

    @Override
    public Map<String, int[]> cargarInventario() throws IOException {
        Map<String, int[]> inventario = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    try {
                        String nombre = partes[0].trim();
                        int cantidad = Integer.parseInt(partes[1].trim());
                        int total = Integer.parseInt(partes[2].trim());
                        inventario.put(nombre, new int[]{cantidad, total});
                    } catch (NumberFormatException e) {
                        System.err.println("Formato numérico inválido en línea: " + linea);
                    }
                }
            }
        }
        return inventario;
    }

    @Override
    public void actualizarInventario(Map<String, int[]> inventario) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Map.Entry<String, int[]> entry : inventario.entrySet()) {
                bw.write(String.format("%s,%d,%d",
                        entry.getKey(),
                        entry.getValue()[0],
                        entry.getValue()[1]));
                bw.newLine();
            }
        }
    }

    public void registrarVenta(String nombreProducto, int cantidad, int total) throws IOException {
        Map<String, int[]> inventario = cargarInventario();
        int[] datos = inventario.getOrDefault(nombreProducto, new int[]{0, 0});
        datos[0] += cantidad;
        datos[1] += total;
        inventario.put(nombreProducto, datos);
        actualizarInventario(inventario);
    }
}