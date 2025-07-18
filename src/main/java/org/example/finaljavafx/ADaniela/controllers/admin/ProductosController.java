package org.example.finaljavafx.ADaniela.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.finaljavafx.ADaniela.models.Producto;
import org.example.finaljavafx.ADaniela.services.ProductoService;

public class ProductosController {
    @FXML private TableView<Producto> tablaProductos;
    private final ProductoService productoService = new ProductoService();

    @FXML
    private void initialize() {
        cargarProductos();
    }

    private void cargarProductos() {
        tablaProductos.getItems().setAll(productoService.obtenerTodos());
    }
}
