package org.example.finaljavafx.controllers.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.finaljavafx.App;
import org.example.finaljavafx.models.Usuario;
import org.example.finaljavafx.services.AuthService;

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
        String usuario = campoUsuario.getText().trim();
        String contrasena = campoContrasena.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Usuario y contraseña son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        try {
            Usuario usuarioAutenticado = authService.autenticar(usuario, contrasena);
            App.setUsuarioActual(usuarioAutenticado); // Establecer usuario en la sesión

            mostrarAlerta("Éxito", "Bienvenido, " + usuarioAutenticado.getUsername(), Alert.AlertType.INFORMATION);

            if ("admin".equals(usuarioAutenticado.getRol())) {
                App.mostrarAdminPanel();
            } else {
                App.mostrarVentaPanel();
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Credenciales incorrectas o error del sistema", Alert.AlertType.ERROR);
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
