package org.example.finaljavafx.CamiloDuran;


import com.example.restaurantefx.model.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public static List<Producto> obtenerTodos(String rutaArchivo) throws IOException {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String codigo = partes[0];
                    String nombre = partes[1];
                    int precio = Integer.parseInt(partes[2]);
                    int cantidad = Integer.parseInt(partes[3]);
                    productos.add(new Producto(codigo, nombre, precio, cantidad));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new IOException("Error al cargar productos: " + e.getMessage(), e);
        }
        return productos;
    }
    public static void guardarTodos(String rutaArchivo, List<Producto> productos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Producto p : productos) {
                writer.write(p.getCodigo() + "," + p.getNombre() + "," + p.getPrecio() + "," + p.getCantidadDisponible());
                writer.newLine();
            }
        }
    }
}