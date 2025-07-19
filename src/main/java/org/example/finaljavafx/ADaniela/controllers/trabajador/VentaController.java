package org.example.finaljavafx.ADaniela.controllers.trabajador;

import org.example.finaljavafx.ADaniela.App;
import org.example.finaljavafx.ADaniela.models.Producto;
import org.example.finaljavafx.ADaniela.services.ReporteService;
import org.example.finaljavafx.ADaniela.services.VentaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VentaController {
    @FXML
    private GridPane gridProductos;
    @FXML
    private TextField subtotalField;

    private final VentaService ventaService = new VentaService();
    private final ReporteService reporteService = new ReporteService();
    private final List<TextField> camposVenta = new ArrayList<>();
    private final List<Producto> productos = new ArrayList<>();

    @FXML
    public void initialize() {
        try {
            productos.addAll(ventaService.obtenerProductos());
            cargarProductosEnVista();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los productos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarProductosEnVista() {
        try {
            gridProductos.getChildren().clear();
            camposVenta.clear();

            // Encabezados
            gridProductos.add(new Label("Código"), 0, 0);
            gridProductos.add(new Label("Nombre"), 1, 0);
            gridProductos.add(new Label("Disponible"), 2, 0);
            gridProductos.add(new Label("Cantidad"), 3, 0);

            // Datos de productos
            int fila = 1;
            for (Producto p : productos) {
                gridProductos.add(new Label(p.getCodigo()), 0, fila);
                gridProductos.add(new Label(p.getNombre()), 1, fila);
                gridProductos.add(new Label(String.valueOf(p.getCantidadDisponible())), 2, fila);

                TextField txtCantidad = new TextField("0");
                txtCantidad.setPrefWidth(60);
                txtCantidad.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal.matches("\\d*")) {
                        txtCantidad.setText(oldVal);
                    } else {
                        actualizarSubtotal();
                    }
                });
                camposVenta.add(txtCantidad);
                gridProductos.add(txtCantidad, 3, fila);
                fila++;
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar la vista de productos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void actualizarSubtotal() {
        try {
            List<Integer> cantidades = new ArrayList<>();
            for (TextField campo : camposVenta) {
                cantidades.add(campo.getText().isEmpty() ? 0 : Integer.parseInt(campo.getText()));
            }
            subtotalField.setText(String.valueOf(ventaService.calcularSubtotal(productos, cantidades)));
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al calcular subtotal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void venderProductos() {
        try {
            List<Integer> cantidades = camposVenta.stream()
                    .map(tf -> tf.getText().isEmpty() ? 0 : Integer.parseInt(tf.getText()))
                    .collect(Collectors.toList());

            if (cantidades.stream().allMatch(c -> c == 0)) {
                mostrarAlerta("Error", "Debe ingresar cantidades para vender", Alert.AlertType.WARNING);
                return;
            }

            boolean exito = ventaService.procesarVenta(productos, cantidades);

            if (exito) {
                mostrarAlerta("Éxito", "Venta registrada correctamente", Alert.AlertType.INFORMATION);
                subtotalField.setText("0");
                // Actualizar vista
                productos.clear();
                productos.addAll(ventaService.obtenerProductos());
                cargarProductosEnVista();
            } else {
                mostrarAlerta("Error", "No hay suficiente stock para algunos productos", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al procesar venta: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void enviarReporte() {
        try {
            reporteService.guardarReporteDiario();
            mostrarAlerta("Éxito", "Reporte diario generado correctamente", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo generar el reporte: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            App.Login(); // Asume que App.Login() maneja la navegación al login
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cerrar la sesión", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}