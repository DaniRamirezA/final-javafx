package org.example.finaljavafx.dao;

import org.example.finaljavafx.models.Reporte;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ReportesDAO {
 void guardarReporte(Reporte reporte) throws IOException;
 List<Reporte> obtenerTodosReportes() throws IOException;
 Reporte obtenerReportePorFecha(LocalDate fecha) throws IOException;
}