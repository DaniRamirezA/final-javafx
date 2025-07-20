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
    }

