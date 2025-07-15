package org.example.finaljavafx.model;

import org.example.finaljavafx.model.usuarios.Cajero;

import java.util.List;

public class Reporte {
    private final int id;
    private final Cajero cajero;
    private final String fecha;
    private final List<Venta> ventas;
    private final double total;

    public Reporte(int id, Cajero cajero, String fecha, List<Venta> ventas, double total) {
        this.id = id;
        this.cajero = cajero;
        this.fecha = fecha;
        this.ventas = ventas;
        this.total = total;
    }

    @Override
    public String toString() {

        StringBuilder ventasStr = new StringBuilder();

        for (Venta venta : ventas) {
            ventasStr.append(venta.toString()).append("#");
        }

        return id + ", " + cajero.toString() + ", " + fecha + ", " + ventasStr + ", " + total;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public double getTotal() {
        return total;
    }
}
