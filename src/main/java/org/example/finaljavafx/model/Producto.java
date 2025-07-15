package org.example.finaljavafx.model;

public class Producto {
    private double precio;
    private Productos categoria;
    private boolean activo;

    public Producto(double precio, Productos categoria) {
        this.precio = precio;
        this.categoria = categoria;
        this.activo = true;
    }
    public Producto(double precio, Productos categoria, boolean activo) {
        this.precio = precio;
        this.categoria = categoria;
        this.activo = activo;
    }

    @Override
    public String toString() {
        return precio + ":" + categoria.getNombre() + ":" + activo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Productos getCategoria() {
        return categoria;
    }

    public void setCategoria(Productos categoria) {
        this.categoria = categoria;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
