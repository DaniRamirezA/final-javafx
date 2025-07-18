/*
package org.example.finaljavafx.CamiloDuran;


import com.example.restaurantefx.model.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements ProductosDAO{
    private File productosDB;

    public ProductoDAO() {
        this.productosDB = new File("src/main/resources/productos.txt");
    }

    @Override
    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        try {
            FileReader fr = new FileReader(productosDB);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                Producto producto = transformarProductoString(linea);
                if (producto != null) {
                    productos.add(producto);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }

    private Producto transformarProductoString(String linea) {
        String[] partes = linea.split(",");
        if (partes.length == 4) {
            try {
                String codigo = partes[0];
                String nombre = partes[1];
                int precio = Integer.parseInt(partes[2]);
                int cantidad = Integer.parseInt(partes[3]);
                return new Producto(codigo, nombre, precio, cantidad);
            } catch (NumberFormatException e) {
                System.err.println("Error al parsear número en línea: " + linea);
            }
        }
        return null;
    }

    @Override
    public void guardarProducto(Producto producto){
    }

    public static void guardarTodos(String rutaArchivo, List<Producto> productos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Producto p : productos) {
                writer.write(p.getCodigo() + "," + p.getNombre() + "," + p.getPrecio() + "," + p.getCantidadDisponible());
                writer.newLine();
            }
        }
    }
}*/
