package org.example.finaljavafx.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import org.example.finaljavafx.App;
import org.example.finaljavafx.models.Reporte;
import org.example.finaljavafx.services.ReporteService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ReportesController {
    @FXML private TableView<Reporte> tablaReportes;
    @FXML private TableColumn<Reporte, LocalDate> colFecha;
    @FXML private TableColumn<Reporte, Integer> colTotalProductos;
    @FXML private TableColumn<Reporte, Double> colTotalVentas;

    private final ReporteService reporteService = new ReporteService();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        configurarTabla();
        cargarReportes();
    }

    private void configurarTabla() {
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTotalProductos.setCellValueFactory(new PropertyValueFactory<>("totalProductos"));
        colTotalVentas.setCellValueFactory(new PropertyValueFactory<>("totalVentas"));

        colTotalVentas.setCellFactory(column -> new TableCell<Reporte, Double>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                setText(empty ? "" : String.format("$%,.0f", amount));
            }
        });

        colTotalVentas.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                setText(empty ? "" : String.format("$%,.2f", amount));
            }
        });
    }

    private void cargarReportes() {
        try {
            tablaReportes.getItems().setAll(
                    reporteService.obtenerReportes().stream()
                            .sorted((r1, r2) -> r2.getFecha().compareTo(r1.getFecha())) // Orden descendente
                            .collect(Collectors.toList())
            );
        } catch (IOException e) {
            App.mostrarError("Error al cargar reportes", "No se pudieron obtener los reportes: " + e.getMessage());
        }
    }

    @FXML
    private void verDetalleReporte() {
        Reporte seleccionado = tablaReportes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            mostrarDetalleCompleto(seleccionado);
        } else {
            App.mostrarError("Error", "Seleccione un reporte de la lista para ver detalles");
        }
    }

    private void mostrarDetalleCompleto(Reporte reporte) {
        StringBuilder detalle = new StringBuilder();
        detalle.append("=== Reporte del ").append(reporte.getFecha().format(dateFormatter)).append(" ===\n\n")
                .append("Total productos vendidos: ").append(reporte.getTotalProductos()).append("\n")
                .append("Total en ventas: ").append(String.format("$%,.2f", reporte.getTotalVentas())).append("\n\n")
                .append("=== Detalle por producto ===\n");

        reporte.getVentas().forEach(item ->
                detalle.append("- ").append(item.getNombreProducto())
                        .append(": ").append(item.getCantidad()).append(" unidades")
                        .append(" | Total: ").append(String.format("$%,.2f", item.getTotal()))
                        .append("\n")
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalle completo del reporte");
        alert.setHeaderText("Reporte del " + reporte.getFecha().format(dateFormatter));
        alert.setContentText(detalle.toString());
        alert.getDialogPane().setPrefSize(400, 500); // Tamaño adecuado para mostrar la información
        alert.showAndWait();
    }

    @FXML
    private void actualizarReportes() {
        cargarReportes();
        App.mostrarInfo("Éxito", "Lista de reportes actualizada");
    }
}