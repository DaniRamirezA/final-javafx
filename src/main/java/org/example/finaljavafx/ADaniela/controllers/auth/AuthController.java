package org.example.finaljavafx.ADaniela.controllers.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.finaljavafx.ADaniela.App;
import org.example.finaljavafx.ADaniela.models.Usuario;
import org.example.finaljavafx.ADaniela.services.AuthService;

public class AuthController {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoContrasena;

    @FXML
    private Button btnIngresar;

    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        // ENTER activa login desde campo de contraseña
        campoContrasena.setOnAction(event -> login());
    }

    @FXML
    private void login() {
        String usuario = campoUsuario.getText();
        String contrasena = campoContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Usuario y contraseña son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        Usuario usuarioAutenticado = authService.autenticar(usuario, contrasena);

        if (usuarioAutenticado != null) {
            mostrarAlerta("Éxito", "Bienvenido, " + usuarioAutenticado.getUsername(), Alert.AlertType.INFORMATION);
            try {
                if ("admin".equals(usuarioAutenticado.getRol())) {
                    App.mostrarAdminPanel();
                } else {
                    App.mostrarVentaPanel(); // Nuevo método que implementaremos luego
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo cargar el panel correspondiente", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas", Alert.AlertType.ERROR);
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
