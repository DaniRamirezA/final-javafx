package org.example.finaljavafx.ADaniela.services;

import org.example.finaljavafx.ADaniela.dao.impl.IO.ProductoDAOIO;
import org.example.finaljavafx.ADaniela.dao.impl.IO.VentaDAOIO;
import org.example.finaljavafx.ADaniela.models.Producto;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class VentaService {
    private final ProductoDAOIO productoDAO;
    private final VentaDAOIO ventaDAO;

    public VentaService() {
        this.productoDAO = new ProductoDAOIO("src/main/resources/productos.txt");
        this.ventaDAO = new VentaDAOIO("src/main/resources/ventas.txt");
    }

    public List<Producto> obtenerProductos() {
        return productoDAO.obtenerProductos();
    }

    public void agregarProducto(Producto producto) {
        productoDAO.guardarProducto(producto);
    }

    public void actualizarProducto(Producto producto) {
        productoDAO.guardarProducto(producto);
    }

    public void eliminarProducto(String codigo) {
        List<Producto> productos = productoDAO.obtenerProductos().stream()
                .filter(p -> !p.getCodigo().equals(codigo))
                .collect(Collectors.toList());

        ((ProductoDAOIO) productoDAO).guardarTodosProductos(productos);
    }

    public int calcularSubtotal(List<Producto> productos, List<Integer> cantidadesVendidas) {
        int total = 0;
        for (int i = 0; i < productos.size(); i++) {
            total += cantidadesVendidas.get(i) * productos.get(i).getPrecio();
        }
        return total;
    }

    public boolean procesarVenta(List<Producto> productos, List<Integer> cantidades) {
        // Validar stock
        for (int i = 0; i < productos.size(); i++) {
            if (cantidades.get(i) > productos.get(i).getCantidadDisponible()) {
                return false;
            }
        }

        // Actualizar cantidades
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cantidad = cantidades.get(i);
            if (cantidad > 0) {
                p.setCantidadDisponible(p.getCantidadDisponible() - cantidad);
                registrarVenta(p, cantidad);
            }
        }

        // Guardar cambios en productos
        ((ProductoDAOIO) productoDAO).guardarTodosProductos(productos);
        return true;
    }

    private void registrarVenta(Producto producto, int cantidadVendida) {
        try {
            ventaDAO.registrarVenta(
                    producto.getNombre(),
                    cantidadVendida,
                    cantidadVendida * producto.getPrecio()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}