package org.example.finaljavafx.ADaniela.models;

import java.time.LocalDate;
import java.util.List;

public class Reporte {
    private int id;
    private LocalDate fecha;
    private List<Venta> ventas;
    private double total;

    // Constructor, getters y setters
    public Reporte(int id, LocalDate fecha, List<Venta> ventas) {
        this.id = id;
        this.fecha = fecha;
        this.ventas = ventas;
        this.total = ventas.stream().mapToDouble(Venta::getTotal).sum();
    }

    public double getTotal() { return total; }
    public LocalDate getFecha() { return fecha; }
}
