package org.example.finaljavafx.dao;

import org.example.finaljavafx.model.Reporte;

import java.util.List;

public interface ReportesDAO {
    void guardarReporte(Reporte reporte);

    List<Reporte> obtenerReportes();
}
