package org.example.finaljavafx.CamiloDuran.dao;

import com.example.restaurantefx.model.Producto;

import java.util.List;

public interface ProductosDAO {
    void guardarProducto(Producto producto);

    List<Producto> obtenerProductos();
}
