package org.example.finaljavafx.ADaniela.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.finaljavafx.ADaniela.models.Producto;
import org.example.finaljavafx.ADaniela.services.VentaService;

import java.io.IOException;

public class ProductosController {
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> columnaCodigo;
    @FXML private TableColumn<Producto, String> columnaNombre;
    @FXML private TableColumn<Producto, Integer> columnaPrecio;
    @FXML private TableColumn<Producto, Integer> columnaCantidad;

    private final VentaService ventaService = new VentaService();

    @FXML
    private void initialize() {
        configurarColumnas();
        cargarProductos();
    }

    private void configurarColumnas() {
        columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadDisponible"));
    }

    private void cargarProductos() {
        tablaProductos.getItems().setAll(ventaService.obtenerProductos());
    }

    @FXML
    private void agregarProducto() {
        mostrarDialogoProducto(false, null);
    }

    @FXML
    private void editarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            mostrarDialogoProducto(true, seleccionado);
        } else {
            mostrarAlerta("Error", "Debe seleccionar un producto", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            boolean confirmacion = mostrarConfirmacion("Eliminar Producto",
                    "¿Está seguro de eliminar el producto " + seleccionado.getNombre() + "?");

            if (confirmacion) {
                ventaService.eliminarProducto(seleccionado.getCodigo());
                cargarProductos();
            }
        } else {
            mostrarAlerta("Error", "Debe seleccionar un producto", Alert.AlertType.WARNING);
        }
    }

    private void mostrarDialogoProducto(boolean isEdicion, Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/finaljavafx/views/admin/ProductoDialog.fxml"));

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(loader.load());
            dialog.setTitle(isEdicion ? "Editar Producto" : "Agregar Producto");

            ProductoDialogController controller = loader.getController();
            controller.setEdicion(isEdicion);

            if (isEdicion && producto != null) {
                controller.setDatosProducto(
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getCantidadDisponible()
                );
            }

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Validación antes de OK
            Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.addEventFilter(ActionEvent.ACTION, event -> {
                if (!validarProducto(controller)) {
                    event.consume();
                }
            });

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    Producto p = new Producto(
                            controller.getCodigo(),
                            controller.getNombre(),
                            controller.getPrecio(),
                            controller.getCantidad()
                    );

                    if (isEdicion) {
                        ventaService.actualizarProducto(p);
                    } else {
                        ventaService.agregarProducto(p);
                    }
                    cargarProductos();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el diálogo", Alert.AlertType.ERROR);
        }
    }

    private boolean validarProducto(ProductoDialogController controller) {
        // Validar código (3 dígitos)
        if (!controller.getCodigo().matches("\\d{3}")) {
            mostrarAlerta("Error", "El código debe tener exactamente 3 dígitos", Alert.AlertType.ERROR);
            return false;
        }

        // Validar nombre (solo letras y números)
        if (!controller.getNombre().matches("[a-zA-Z0-9 ]+")) {
            mostrarAlerta("Error", "El nombre solo puede contener letras y números", Alert.AlertType.ERROR);
            return false;
        }

        // Validar precio
        if (controller.getPrecio() <= 0) {
            mostrarAlerta("Error", "El precio debe ser un número positivo", Alert.AlertType.ERROR);
            return false;
        }

        // Validar cantidad
        if (controller.getCantidad() < 0) {
            mostrarAlerta("Error", "La cantidad no puede ser negativa", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}