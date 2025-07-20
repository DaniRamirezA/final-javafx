package org.example.finaljavafx.ADaniela.services;

import org.example.finaljavafx.ADaniela.dao.ReportesDAO;
import org.example.finaljavafx.ADaniela.dao.impl.IO.ReporteDAOIO;
import org.example.finaljavafx.ADaniela.models.Producto;
import org.example.finaljavafx.ADaniela.models.Reporte;
import org.example.finaljavafx.ADaniela.App;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReporteService {
    private final ReportesDAO reporteDAO;
    private Reporte reporteDiario;

    public ReporteService() {
        this.reporteDAO = new ReporteDAOIO("src/main/resources/reportes/");
        this.reporteDiario = new Reporte(LocalDate.now(), App.getUsuarioActual().getUsername());
    }

    /**
     * Agrega una venta al reporte diario con usuario
     */
    public void agregarVentaDiaria(List<Producto> productos, List<Integer> cantidades) {
        if (productos.size() != cantidades.size()) {
            throw new IllegalArgumentException("Las listas de productos y cantidades deben tener el mismo tamaño");
        }

        String usuario = App.getUsuarioActual().getUsername();
        for (int i = 0; i < productos.size(); i++) {
            int cantidad = cantidades.get(i);
            if (cantidad > 0) {
                Producto p = productos.get(i);
                reporteDiario.agregarVenta(
                        p.getNombre(),
                        cantidad,
                        p.getPrecio(),       // Precio unitario
                        p.getPrecio() * cantidad, // Total
                        usuario
                );
            }
        }
    }

    /**
     * Guarda el reporte diario con el usuario actual
     */
    public void guardarReporteDiario() throws IOException {
        if (reporteDiario != null && reporteDiario.tieneVentas()) {
            reporteDiario.setUsuario(App.getUsuarioActual().getUsername());
            reporteDAO.guardarReporte(reporteDiario);
            reporteDiario = new Reporte(LocalDate.now(), App.getUsuarioActual().getUsername());
        }
    }

    /**
     * Obtiene reportes ordenados por fecha (más reciente primero)
     */
    public List<Reporte> obtenerReportes() throws IOException {
        return reporteDAO.obtenerTodosReportes();
    }

    /**
     * Obtiene reportes por usuario y fecha
     */
    public Reporte obtenerReportePorFechaYUsuario(LocalDate fecha, String usuario) throws IOException {
        return reporteDAO.obtenerTodosReportes().stream()
                .filter(r -> r.getFecha().equals(fecha) && r.getUsuario().equals(usuario))
                .findFirst()
                .orElse(null);
    }

    // Métodos existentes (sin cambios estructurales)
    public boolean tieneVentasDiarias() {
        return reporteDiario != null && reporteDiario.tieneVentas();
    }

    public Reporte getReporteDiario() {
        return reporteDiario;
    }

    public Reporte reiniciarReporteDiario() {
        this.reporteDiario = new Reporte(LocalDate.now(), App.getUsuarioActual().getUsername());
        return this.reporteDiario;
    }
}