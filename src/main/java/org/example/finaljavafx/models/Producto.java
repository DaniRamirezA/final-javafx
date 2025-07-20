package org.example.finaljavafx.models;

import java.io.Serializable;

public class Producto implements Serializable {
    private String codigo;
    private String nombre;
    private int precio;
    private int cantidadDisponible;

    public Producto(String codigo, String nombre, int precio, int cantidadDisponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; }
    public int getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(int cantidad) { this.cantidadDisponible = cantidad; }
}
