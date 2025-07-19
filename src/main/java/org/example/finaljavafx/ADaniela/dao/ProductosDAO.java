package org.example.finaljavafx.ADaniela.dao;

import org.example.finaljavafx.ADaniela.models.Producto;

import java.util.List;

public interface ProductosDAO {
    void guardarProducto(Producto producto);

    List<Producto> obtenerProductos();
}
