package org.example.finaljavafx.ADaniela.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.example.finaljavafx.ADaniela.App;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AdminController {
    @FXML
    private StackPane contenidoPanel;

    @FXML
    public void mostrarProductos() {
        cargarVista("/org/example/finaljavafx/views/admin/ProductosView");
    }

    @FXML
    private void mostrarTrabajadores() {
        cargarVista("/org/example/finaljavafx/views/admin/TrabajadoresView");
    }

    @FXML
    private void mostrarReportes() {
        cargarVista("/org/example/finaljavafx/views/admin/ReportesView");
    }

    @FXML
    private void logout() {
        try {
            App.Login();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de login", AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void cargarVista(String fxmlPath) {
        try {
            contenidoPanel.getChildren().clear();
            contenidoPanel.getChildren().add(App.loadFXML(fxmlPath));
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista: " + fxmlPath, AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}