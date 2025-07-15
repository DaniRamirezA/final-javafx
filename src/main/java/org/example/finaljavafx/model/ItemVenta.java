package org.example.finaljavafx.model;

public class ItemVenta {
    private final Producto producto;
    private final int cantidad;
    private final double subtotal;

    public ItemVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio() * cantidad;
    }

    public String toString() {
        return producto.toString() + ";" + cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
