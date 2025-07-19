package org.example.finaljavafx.CamiloDuran.services;

import com.example.restaurantefx.dao.ReporteDAO;
import com.example.restaurantefx.dao.ReportesDAO;

public class ReporteService {

    private final ReportesDAO reporteDAO;

    public ReporteService() {
        this.reporteDAO = new ReporteDAO(); // Usa la implementación de archivo por defecto
    }

    public void guardarReporteDiario() {
        reporteDAO.guardarReporte();
    }
}