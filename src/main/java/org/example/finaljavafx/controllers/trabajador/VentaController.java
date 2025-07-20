package org.example.finaljavafx.controllers.trabajador;

import javafx.scene.control.ButtonType;
import org.example.finaljavafx.App;
import org.example.finaljavafx.models.Producto;
import org.example.finaljavafx.services.ReporteService;
import org.example.finaljavafx.services.VentaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

            int fila = 0;
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
                reporteService.agregarVentaDiaria(productos, cantidades);

                mostrarAlerta("Éxito", "Venta registrada correctamente", Alert.AlertType.INFORMATION);
                subtotalField.setText("0");

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
            if (reporteService.tieneVentasDiarias()) {
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                String mensaje = String.format(
                        "✅ Reporte guardado\n" +
                                "⏰ Hora: %s\n" +
                                "👤 Usuario: %s\n" +
                                "📦 Productos: %d\n" +
                                "💰 Total: $%,.0f",
                        timestamp,
                        App.getUsuarioActual().getUsername(),
                        reporteService.getReporteDiario().getTotalProductos(),
                        reporteService.getReporteDiario().getTotalVentas()
                );

                reporteService.guardarReporteDiario();

                mostrarAlerta("Éxito", mensaje, Alert.AlertType.INFORMATION);

            } else {
                mostrarAlerta("Información",
                        "No hay ventas registradas para generar el reporte",
                        Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta("Error",
                    "Error al generar el reporte:\n" + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace(); // Para depuración
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            if (reporteService.tieneVentasDiarias()) {
                boolean confirmar = mostrarConfirmacion("Ventas pendientes",
                        "Tiene ventas no reportadas. ¿Desea generar el reporte antes de salir?");

                if (confirmar) {
                    reporteService.guardarReporteDiario();
                }
            }
            App.cargarLogin();
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

    private boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}