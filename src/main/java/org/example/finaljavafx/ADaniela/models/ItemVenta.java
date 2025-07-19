package org.example.finaljavafx.ADaniela.models;

public class ItemVenta {
    private Producto producto;
    private int cantidad;
    private double subtotal;

    // Constructor
    public ItemVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio() * cantidad;
    }

    // Getters
    public double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }
}

