package org.example.finaljavafx.dao.impl.List;

import org.example.finaljavafx.dao.ProductosDAO;
import org.example.finaljavafx.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosDAOList implements ProductosDAO {

    private static final List<Producto> productosDB = new ArrayList<>();

    public ProductosDAOList() {
        System.out.println("Inicializando la base de datos de productos en memoria");
    }

    @Override
    public void guardarProducto(Producto producto) {
        productosDB.add(producto);
    }

    @Override
    public List<Producto> obtenerProductos() {
        return productosDB;
    }
}
