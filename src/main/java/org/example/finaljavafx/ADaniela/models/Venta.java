package org.example.finaljavafx.ADaniela.models;

import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private int id;
    private LocalDateTime fecha;
    private List<ItemVenta> items;
    private double total;

    // Constructor
    public Venta(int id, List<ItemVenta> items) {
        this.id = id;
        this.fecha = LocalDateTime.now();
        this.items = items;
        this.total = calcularTotal();
    }

    // Método para calcular el total
    private double calcularTotal() {
        return items.stream()
                .mapToDouble(ItemVenta::getSubtotal)
                .sum();
    }

    // Getters
    public double getTotal() {
        return total;
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getId() {
        return id;
    }
}
