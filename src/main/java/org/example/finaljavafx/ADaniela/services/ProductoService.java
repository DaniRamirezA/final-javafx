package org.example.finaljavafx.ADaniela.services;

import org.example.finaljavafx.ADaniela.dao.ProductosDAO;
import org.example.finaljavafx.ADaniela.dao.impl.IO.ProductoDAOIO;
import org.example.finaljavafx.ADaniela.models.Producto;

import java.util.List;

public class ProductoService {
    private final ProductosDAO productoDAO;

    public ProductoService() {
        // Usamos directamente ProductoDAOIO en lugar de depender de VentaService
        this.productoDAO = new ProductoDAOIO("src/main/resources/productos.txt");
    }

    public List<Producto> obtenerTodos() {
        return productoDAO.obtenerProductos();
    }

    public Producto buscarPorCodigo(String codigo) {
        return productoDAO.obtenerProductos().stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public void agregarProducto(Producto producto) {
        // Validación básica antes de agregar
        if (producto != null && producto.getCodigo() != null) {
            productoDAO.guardarProducto(producto);
        }
    }

    public void actualizarProducto(Producto producto) {
        // El DAO ya maneja la actualización en guardarProducto()
        agregarProducto(producto);
    }

    public void eliminarProducto(String codigo) {
        List<Producto> productos = productoDAO.obtenerProductos();
        productos.removeIf(p -> p.getCodigo().equals(codigo));

        try {
            ProductoDAOIO productoDAOIO = (ProductoDAOIO) productoDAO;
            productoDAOIO.guardarTodosProductos(productos);
        } catch (Exception e) {
            // Manejar error al guardar
            e.printStackTrace();
        }
    }

    public boolean existeProducto(String codigo) {
        return productoDAO.obtenerProductos().stream()
                .anyMatch(p -> p.getCodigo().equals(codigo));
    }
}
