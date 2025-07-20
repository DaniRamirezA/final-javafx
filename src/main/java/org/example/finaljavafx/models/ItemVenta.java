package org.example.finaljavafx.models;

public class ItemVenta {
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public ItemVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio() * cantidad;
    }

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

