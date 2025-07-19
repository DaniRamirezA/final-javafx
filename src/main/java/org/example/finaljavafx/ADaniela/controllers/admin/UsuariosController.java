package org.example.finaljavafx.ADaniela.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.finaljavafx.ADaniela.models.Usuario;
import org.example.finaljavafx.ADaniela.services.UsuariosService;
import javafx.scene.control.ComboBox;


public class UsuariosController {
    @FXML private TableView<Usuario> tablaTrabajadores;
    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoContrasena;
    @FXML private ComboBox<String> comboRol;
    @FXML    private Button btnEliminar;

    private final UsuariosService usuariosService = new UsuariosService();

    @FXML
    public void initialize() {
        if (tablaTrabajadores == null) {
            System.err.println("Error: tablaTrabajadores no fue inyectada correctamente");
            return;
        }

        cargarTrabajadores();

        tablaTrabajadores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.getUsername().equals("admin")) {
                btnEliminar.setDisable(true);
            } else {
                btnEliminar.setDisable(false);
            }
        });
    }

    private void cargarTrabajadores() {
        tablaTrabajadores.getItems().setAll(usuariosService.obtenerTodos());
    }

    @FXML
    private void agregarTrabajador() {
        String usuario = campoUsuario.getText();
        String contrasena = campoContrasena.getText();
        String rol = comboRol.getValue();

        if (usuario.isEmpty() || contrasena.isEmpty() || rol == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        Usuario nuevo = new Usuario(usuario, contrasena, rol);
        usuariosService.agregarUsuario(nuevo);
        cargarTrabajadores();
        limpiarCampos();
    }

    @FXML
    private void eliminarTrabajador() {
        Usuario seleccionado = tablaTrabajadores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Error", "Seleccione un usuario para eliminar", Alert.AlertType.WARNING);
            return;
        }

        // Verificar si es el admin
        if (seleccionado.getUsername().equals("admin")) {
            mostrarAlerta(
                    "Acción denegada",
                    "No se puede eliminar al administrador principal.",
                    Alert.AlertType.WARNING
            );
            return; // Sale del método sin eliminar
        }

        // Si no es admin, procede con la eliminación
        usuariosService.eliminarUsuario(seleccionado.getUsername());
        cargarTrabajadores(); // Actualiza la tabla
        mostrarAlerta("Éxito", "Usuario eliminado correctamente", Alert.AlertType.INFORMATION);
    }

    private void limpiarCampos() {
        campoUsuario.clear();
        campoContrasena.clear();
        comboRol.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
