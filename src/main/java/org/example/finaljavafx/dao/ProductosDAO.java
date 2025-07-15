package org.example.finaljavafx.dao;

import org.example.finaljavafx.model.Producto;

import java.util.List;

public interface ProductosDAO {
    void guardarProducto(Producto producto);

    List<Producto> obtenerProductos();
}
