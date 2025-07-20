package org.example.finaljavafx.services;

import org.example.finaljavafx.dao.ProductosDAO;
import org.example.finaljavafx.dao.impl.IO.ProductoDAOIO;
import org.example.finaljavafx.models.Producto;

import java.util.List;

public class ProductoService {
    private final ProductosDAO productoDAO;

    public ProductoService() {
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
        if (producto != null && producto.getCodigo() != null) {
            productoDAO.guardarProducto(producto);
        }
    }

    public void actualizarProducto(Producto producto) {
        agregarProducto(producto);
    }

    public void eliminarProducto(String codigo) {
        List<Producto> productos = productoDAO.obtenerProductos();
        productos.removeIf(p -> p.getCodigo().equals(codigo));

        try {
            ProductoDAOIO productoDAOIO = (ProductoDAOIO) productoDAO;
            productoDAOIO.guardarTodosProductos(productos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean existeProducto(String codigo) {
        return productoDAO.obtenerProductos().stream()
                .anyMatch(p -> p.getCodigo().equals(codigo));
    }
}
