package org.example.finaljavafx.CamiloDuran.controller;

import com.example.restaurantefx.model.Producto;
import com.example.restaurantefx.services.ReporteService;
import com.example.restaurantefx.services.VentaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class VentaController {
    @FXML
    private GridPane gridProductos;
    @FXML
    private TextField subtotalField;

    private final VentaService service = new VentaService();
    private final ReporteService reporte = new ReporteService();
    private final List<TextField> camposVenta = new ArrayList<>();
    private final List<Producto> productos = new ArrayList<>();

    @FXML
    public void initialize() {
        productos.addAll(service.obtenerProductos());
        cargarProductosEnVista();
    }

    private void cargarProductosEnVista() {
        gridProductos.getChildren().clear();
        camposVenta.clear();
        gridProductos.add(new Label("Codigo"), 0, 0);
        gridProductos.add(new Label("Nombre"), 1, 0);
        gridProductos.add(new Label("Cantidad"), 2, 0);
        gridProductos.add(new Label("Vender"), 3, 0);

        int fila = 1;
        for (Producto p : productos) {
            gridProductos.add(new Label(p.getCodigo()), 0, fila);
            gridProductos.add(new Label(p.getNombre()), 1, fila);
            gridProductos.add(new Label(String.valueOf(p.getCantidadDisponible())), 2, fila);

            TextField txtVender = new TextField("0");
            txtVender.setPrefWidth(50);
            txtVender.textProperty().addListener((obs, oldVal, newVal) -> actualizarSubtotal());
            camposVenta.add(txtVender);

            gridProductos.add(txtVender, 3, fila);
            fila++;
        }
    }

    private void actualizarSubtotal() {
        List<Integer> cantidades = new ArrayList<>();

        for (TextField campo : camposVenta) {
            try {
                cantidades.add(Integer.parseInt(campo.getText()));
            } catch (NumberFormatException e) {
                cantidades.add(0);
            }
        }

        int total = service.calcularSubtotal(productos, cantidades);
        subtotalField.setText(String.valueOf(total));
    }
    @FXML
    private void venderProductos() {
        List<Integer> cantidades = new ArrayList<>();

        for (TextField campo : camposVenta) {
            try {
                cantidades.add(Integer.parseInt(campo.getText()));
            } catch (NumberFormatException e) {
                cantidades.add(0);
            }
        }
        boolean todosCero = cantidades.stream().allMatch(c -> c == 0);
        if (todosCero) {
            mostrarAlerta("Error", "No se puede procesar una venta sin productos.");
            return;
        }


        boolean exito = service.procesarVenta(productos, cantidades);

        if (exito) {
            mostrarAlerta("Venta exitosa", "Se ha realizado la venta correctamente.");
            subtotalField.setText("0");
            cargarProductosEnVista();
        } else {
            mostrarAlerta("Error", "No hay suficiente stock para alguno de los productos.");
        }
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void enviarReporte() {
        reporte.guardarReporteDiario();
        mostrarAlerta("Reporte enviado", "El reporte del día se ha guardado correctamente.");
    }

}