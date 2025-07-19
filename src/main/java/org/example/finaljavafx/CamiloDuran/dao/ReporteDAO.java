package org.example.finaljavafx.CamiloDuran.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporteDAO implements ReportesDAO{
    @Override
    public void guardarReporte() {
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String origen = "src/main/resources/ventas.txt";
        String destino = "src/main/resources/reportes/reporte_" + fecha + ".txt";

        try {
            Files.createDirectories(Paths.get("src/main/resources/reportes"));
            Files.copy(Paths.get(origen), Paths.get(destino), StandardCopyOption.REPLACE_EXISTING);

            // Vaciar inventario actual
            Files.write(Paths.get(origen), "".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
