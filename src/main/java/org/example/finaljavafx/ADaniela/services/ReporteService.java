package org.example.finaljavafx.ADaniela.services;

import org.example.finaljavafx.ADaniela.dao.impl.IO.ReporteDAOIO;
import org.example.finaljavafx.ADaniela.dao.ReportesDAO;

public class ReporteService {

    private final ReportesDAO reporteDAO;

    public ReporteService() {
        this.reporteDAO = new ReporteDAOIO(); // Usa la implementación de archivo por defecto
    }

    public void guardarReporteDiario() {
        reporteDAO.guardarReporte();
    }
}