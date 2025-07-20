package org.example.finaljavafx.ADaniela.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reporte {
    private final LocalDate fecha;
    private final List<ItemReporte> ventas;
    private String usuario;

    public static class ItemReporte {
        private final String nombreProducto;
        private final int cantidad;
        private final double precioUnitario;
        private final double total;
        private final String usuario;

        public ItemReporte(String nombreProducto, int cantidad, double precioUnitario, double total, String usuario) {
            this.nombreProducto = nombreProducto;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.total = total;
            this.usuario = usuario;
        }

        // Getters
        public String getNombreProducto() { return nombreProducto; }
        public int getCantidad() { return cantidad; }
        public double getPrecioUnitario() { return precioUnitario; }
        public double getTotal() { return total; }
        public String getUsuario() { return usuario; }
    }

    public Reporte(LocalDate fecha, String usuario) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.ventas = new ArrayList<>();
    }

    // Método modificado para consolidar ventas automáticamente
    public void agregarVenta(String nombreProducto, int cantidad, double precioUnitario, double total, String usuario) {
        // Busca si ya existe un ítem para este producto y usuario
        for (ItemReporte item : ventas) {
            if (item.getNombreProducto().equals(nombreProducto) && item.getUsuario().equals(usuario)) {
                // Crea un nuevo ítem con las cantidades sumadas (los objetos son inmutables)
                ItemReporte consolidado = new ItemReporte(
                        nombreProducto,
                        item.getCantidad() + cantidad,
                        precioUnitario, // Mantiene el último precio unitario (o puedes promediarlo)
                        item.getTotal() + total,
                        usuario
                );
                ventas.remove(item);
                ventas.add(consolidado);
                return;
            }
        }
        // Si no existe, agrega un nuevo ítem
        ventas.add(new ItemReporte(nombreProducto, cantidad, precioUnitario, total, usuario));
    }

    // Método sobrecargado para Producto
    public void agregarVenta(Producto producto, int cantidad, String usuario) {
        agregarVenta(
                producto.getNombre(),
                cantidad,
                producto.getPrecio(),
                cantidad * producto.getPrecio(),
                usuario
        );
    }

    // Métodos existentes (sin cambios)
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public int getTotalProductos() { return ventas.stream().mapToInt(ItemReporte::getCantidad).sum(); }
    public double getTotalVentas() { return ventas.stream().mapToDouble(ItemReporte::getTotal).sum(); }
    public LocalDate getFecha() { return fecha; }
    public List<ItemReporte> getVentas() { return new ArrayList<>(ventas); }
    public boolean tieneVentas() { return !ventas.isEmpty(); }

    @Override
    public String toString() {
        return "Reporte{" +
                "fecha=" + fecha +
                ", usuario=" + usuario +
                ", totalProductos=" + getTotalProductos() +
                ", totalVentas=" + getTotalVentas() +
                '}';
    }
}