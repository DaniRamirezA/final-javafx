package org.example.finaljavafx.ADaniela.models;

import java.io.Serializable;

public class Producto implements Serializable {
    private String nombre;
    private double precio;
    private String categoria;
    private boolean activo;
    private int codigo;

    public Producto(String nombre, double precio, String categoria, int codigo) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.codigo = codigo;
        this.activo = true;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public int getCodigo() { return codigo; }
}
