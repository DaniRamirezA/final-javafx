package org.example.finaljavafx.model;

import org.example.finaljavafx.model.usuarios.Cajero;

import java.util.List;

public class Venta {
    private final int id;
    private final String fechaVenta;
    private final Cajero cajero;
    private final List<ItemVenta> items;
    private final double total;

    public Venta(int id, String fechaVenta, Cajero cajero, List<ItemVenta> items, double total) {
        this.id = id;
        this.fechaVenta = fechaVenta;
        this.cajero = cajero;
        this.items = items;
        this.total = total;
    }

    public String toString() {
        StringBuilder itemsStr = new StringBuilder();

        for (ItemVenta item : items) {
            itemsStr.append(item.toString()).append("^");
        }

        return id + "-" + fechaVenta + "-" + cajero.toString() + "-" + total + "-" + itemsStr;
    }
}
