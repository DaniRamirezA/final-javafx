package org.example.finaljavafx.ADaniela;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.finaljavafx.ADaniela.models.Usuario;
import org.example.finaljavafx.ADaniela.services.UsuariosService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class App extends Application {
    private static Stage primaryStage;
    private final UsuariosService usuariosService = new UsuariosService();

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        crearAdminPorDefecto();
        Login();
        stage.setTitle("Sistema de Cafetería");
        stage.show();
    }

    public static void Login() throws IOException {
        Parent root = loadFXML("/org/example/finaljavafx/views/auth/LoginView");
        primaryStage.setScene(new Scene(root, 800, 600));
    }

    public static void mostrarAdminPanel() throws IOException {
        Parent root = loadFXML("/org/example/finaljavafx/views/admin/AdminView");
        primaryStage.setScene(new Scene(root, 1000, 700));
    }

    public static void mostrarVentaPanel() throws IOException {
        Parent root = loadFXML("/org/example/finaljavafx/views/trabajador/venta_view");
        primaryStage.setScene(new Scene(root, 1000, 700));
    }

    public static Parent loadFXML(String fxmlPath) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(
                App.class.getResource(fxmlPath + ".fxml")
        ));
    }

    private void crearAdminPorDefecto() {
        List<Usuario> usuarios = usuariosService.obtenerTodos();
        boolean adminExists = usuarios.stream()
                .anyMatch(u -> u.getUsername().equals("admin"));

        if (!adminExists) {
            Usuario admin = new Usuario("admin", "admin123", "admin");
            usuariosService.agregarUsuario(admin);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
