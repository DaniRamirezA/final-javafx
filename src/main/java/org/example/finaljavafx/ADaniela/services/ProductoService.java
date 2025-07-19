package org.example.finaljavafx.ADaniela.services;

import org.example.finaljavafx.ADaniela.dao.ProductoDAO;
import org.example.finaljavafx.ADaniela.models.Producto;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoService {
    private final ProductoDAO productoDAO = new ProductoDAO();

    public List<Producto> obtenerTodos() {
        return productoDAO.cargar();
    }

    public void agregarProducto(Producto producto) {
        List<Producto> productos = productoDAO.cargar();
        productos.add(producto);
        productoDAO.guardar(productos);
    }
}
