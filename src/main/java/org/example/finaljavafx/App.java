package org.example.finaljavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.finaljavafx.models.Usuario;
import org.example.finaljavafx.services.ReporteService;
import org.example.finaljavafx.services.UsuariosService;

import java.io.IOException;
import java.util.List;

public class App extends Application {
    private static Stage primaryStage;
    private static Usuario usuarioActual = new Usuario("default", "default", "trabajador");
    private final UsuariosService usuariosService = new UsuariosService();
    private final ReporteService reporteService = new ReporteService();

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        crearAdminPorDefecto();
        cargarLogin();
        stage.setTitle("Sistema de Cafetería - UdeA");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void cargarLogin() throws IOException {
        Parent root = loadFXML("/org/example/finaljavafx/views/auth/LoginView");
        primaryStage.setScene(new Scene(root, 800, 600));
        usuarioActual = null;
    }

    public static void mostrarAdminPanel() throws IOException {
        Parent root = loadFXML("/org/example/finaljavafx/views/admin/AdminView");
        primaryStage.setScene(new Scene(root, 1200, 800));
    }

    public static void mostrarVentaPanel() throws IOException {
        Parent root = loadFXML("/org/example/finaljavafx/views/trabajador/venta_view");
        primaryStage.setScene(new Scene(root, 1000, 700));
    }

    public static void mostrarReportesPanel() throws IOException {
        if (usuarioActual != null && usuarioActual.getRol().equals("admin")) {
            Parent root = loadFXML("/org/example/finaljavafx/views/admin/ReportesView");
            primaryStage.setScene(new Scene(root, 1200, 800));
        } else {
            mostrarError("Acceso denegado", "Solo administradores pueden ver reportes");
            cargarLogin();
        }
    }

    public static Parent loadFXML(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath + ".fxml"));
        return loader.load();
    }

    private void crearAdminPorDefecto() {
        try {
            List<Usuario> usuarios = usuariosService.obtenerTodos();
            boolean adminExists = usuarios.stream()
                    .anyMatch(u -> u.getRol().equals("admin"));

            if (!adminExists) {
                Usuario admin = new Usuario("admin", "admin123", "admin");
                usuariosService.agregarUsuario(admin);
            }
        } catch (Exception e) {
            System.err.println("Error al crear admin por defecto: " + e.getMessage());
        }
    }

    public static void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }
    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static void main(String[] args) {
        launch(args);
    }
}