package org.example.finaljavafx.dao.impl.reportes;

import org.example.finaljavafx.dao.ReportesDAO;
import org.example.finaljavafx.dao.impl.DatosIO;
import org.example.finaljavafx.model.Reporte;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReportesDAOIO extends DatosIO implements ReportesDAO {

    private File reportesDB;

    public ReportesDAOIO() {
        System.out.println("Inicializando la base de datos de reportes en disco con IO");
        // Inicializar la base de datos de notas
        this.reportesDB = new File("reportes");
        if (!reportesDB.exists()) {
            try {
                reportesDB.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo reportes: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void guardarReporte(Reporte reporte) {
        String reporteString = reporte.getNombre() + "," + reporte.getDescripcion() + "," + reporte.getPorcentaje() + "\n";
        FileWriter fw;
        BufferedWriter bw;
        try {
            fw = new FileWriter(reportesDB, true);
            bw = new BufferedWriter(fw);
            bw.write(reporteString);
            bw.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo reportes: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reporte> obtenerReportes() {
        List<Reporte> resultado = new ArrayList<>();
        try {
            FileReader fr = new FileReader(reportesDB);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                Reporte reporte = transformString(linea);
                resultado.add(reporte);
            }
            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    protected Reporte transformString(String linea) {
        String[] partes = linea.split(",");
        return new Reporte(partes[0], partes[1], Float.parseFloat(partes[2]));
    }
}
