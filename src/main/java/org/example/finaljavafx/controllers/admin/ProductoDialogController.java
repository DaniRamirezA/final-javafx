package org.example.finaljavafx.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProductoDialogController {
    @FXML private TextField codigoField;
    @FXML private TextField nombreField;
    @FXML private TextField precioField;
    @FXML private TextField cantidadField;

    private boolean isEdicion;

    public void setEdicion(boolean isEdicion) {
        this.isEdicion = isEdicion;
        if (isEdicion) {
            codigoField.setDisable(true);
        }
    }

    public void setDatosProducto(String codigo, String nombre, int precio, int cantidad) {
        codigoField.setText(codigo);
        nombreField.setText(nombre);
        precioField.setText(String.valueOf(precio));
        cantidadField.setText(String.valueOf(cantidad));
    }

    public String getCodigo() {
        return codigoField.getText();
    }
    public String getNombre() {
        return nombreField.getText();
    }

    public int getPrecio() {
        try {
            return Integer.parseInt(precioField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getCantidad() {
        try {
            return Integer.parseInt(cantidadField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}