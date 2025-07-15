package org.example.finaljavafx.dao.impl.reportes;

import org.example.finaljavafx.dao.ReportesDAO;
import org.example.finaljavafx.model.Reporte;

import java.util.ArrayList;
import java.util.List;

public class ReportesDAOList implements ReportesDAO {

    private static final List<Reporte> reportesDB = new ArrayList<>();

    public ReportesDAOList() {
        System.out.println("Inicializando la base de datos de reportes en memoria");
    }

    @Override
    public void guardarReporte(Reporte reporte) {
        reportesDB.add(reporte);
    }

    @Override
    public List<Reporte> obtenerReportes() {
        return reportesDB;
    }
}
