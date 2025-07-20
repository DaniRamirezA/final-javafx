package org.example.finaljavafx.dao.impl.IO;

import org.example.finaljavafx.models.Producto;
import org.example.finaljavafx.dao.ProductosDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOIO implements ProductosDAO {
    private final String rutaArchivo;

    public ProductoDAOIO(String ruta) {
        this.rutaArchivo = ruta;
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear archivo de productos: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Producto producto = parsearLineaProducto(linea);
                if (producto != null) {
                    productos.add(producto);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de productos: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public void guardarProducto(Producto producto) {
        List<Producto> productos = obtenerProductos();
        boolean encontrado = false;

        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(producto.getCodigo())) {
                productos.set(i, producto);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            productos.add(producto);
        }

        guardarTodosProductos(productos);
    }

    public void guardarTodosProductos(List<Producto> productos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Producto p : productos) {
                bw.write(serializarProducto(p));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }

    private Producto parsearLineaProducto(String linea) {
        String[] partes = linea.split(",");
        if (partes.length == 4) {
            try {
                return new Producto(
                        partes[0].trim(),
                        partes[1].trim(),
                        Integer.parseInt(partes[2].trim()),
                        Integer.parseInt(partes[3].trim())
                );
            } catch (NumberFormatException e) {
                System.err.println("Formato numérico inválido en línea: " + linea);
            }
        }
        return null;
    }

    private String serializarProducto(Producto producto) {
        return String.join(",",
                producto.getCodigo(),
                producto.getNombre(),
                String.valueOf(producto.getPrecio()),
                String.valueOf(producto.getCantidadDisponible())
        );
    }
}