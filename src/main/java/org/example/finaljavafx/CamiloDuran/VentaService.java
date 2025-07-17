package org.example.finaljavafx.CamiloDuran;

import com.example.restaurantefx.dao.InventarioDAO;
import com.example.restaurantefx.dao.ProductoDAO;
import com.example.restaurantefx.model.Producto;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentaService {
    private final ProductoDAO dao;

    public VentaService() {
        this.dao = new ProductoDAO();
    }

    public List<Producto> obtenerProductos() {
        return dao.obtenerProductos();
    }

    public int calcularSubtotal(List<Producto> productos, List<Integer> cantidadesVendidas) {
        int total = 0;
        for (int i = 0; i < productos.size(); i++) {
            total += cantidadesVendidas.get(i) * productos.get(i).getPrecio();
        }
        return total;
    }

    private final InventarioDAO inventarioDAO = new InventarioDAO("src/main/resources/inventario.txt");

    public boolean procesarVenta(List<Producto> productos, List<Integer> cantidades) {
        boolean exito = true;

        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cantidadVendida = cantidades.get(i);

            if (cantidadVendida > p.getCantidadDisponible()) {
                exito = false;
                break;
            }
        }

        if (!exito) return false;

        // Actualizar cantidades y archivo inventario
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cantidadVendida = cantidades.get(i);

            if (cantidadVendida > 0) {
                p.setCantidadDisponible(p.getCantidadDisponible() - cantidadVendida);
                registrarEnInventario(p, cantidadVendida);
            }
        }

        try {
            ProductoDAO.guardarTodos("src/main/resources/productos.txt", productos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
    private void registrarEnInventario(Producto producto, int cantidadVendida) {
        File archivo = new File("src/main/resources/inventario.txt");
        Map<String, int[]> inventario = new HashMap<>();

        // Leer datos existentes
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        String nombre = partes[0];
                        int cantidad = Integer.parseInt(partes[1]);
                        int total = Integer.parseInt(partes[2]);
                        inventario.put(nombre, new int[]{cantidad, total});
                    }
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Sumar nueva venta
        int[] datos = inventario.getOrDefault(producto.getNombre(), new int[]{0, 0});
        datos[0] += cantidadVendida;
        datos[1] += cantidadVendida * producto.getPrecio();
        inventario.put(producto.getNombre(), datos);

        // Guardar el inventario actualizado
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Map.Entry<String, int[]> entry : inventario.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue()[0] + "," + entry.getValue()[1]);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


