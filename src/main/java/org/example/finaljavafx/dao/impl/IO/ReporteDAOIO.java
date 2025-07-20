package org.example.finaljavafx.dao.impl.IO;

import org.example.finaljavafx.dao.ReportesDAO;
import org.example.finaljavafx.models.Reporte;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

public class ReporteDAOIO implements ReportesDAO {
    private final String directorioReportes;

    public ReporteDAOIO(String directorioReportes) {
        this.directorioReportes = directorioReportes;
        crearDirectorioSiNoExiste();
    }

    private void crearDirectorioSiNoExiste() {
        Path path = Paths.get(directorioReportes);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Error al crear directorio de reportes", e);
            }
        }
    }

    @Override
    public void guardarReporte(Reporte reporte) throws IOException {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HHmmss"));
        String usuario = reporte.getUsuario();
        String nombreArchivo = String.format("%sreporte_%s_%s_%s.txt",
                directorioReportes,
                reporte.getFecha(),
                usuario,
                timestamp);

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println("Producto,Cantidad,PrecioUnitario,Total,Usuario,Timestamp");
            reporte.getVentas().forEach(item ->
                    writer.printf("%s,%d,%.0f,%.0f,%s,%s%n",
                            item.getNombreProducto(),
                            item.getCantidad(),
                            item.getPrecioUnitario(),
                            item.getTotal(),
                            usuario,
                            timestamp)
            );
        }
    }

    @Override
    public List<Reporte> obtenerTodosReportes() throws IOException {
        return Files.list(Paths.get(directorioReportes))
                .filter(this::esArchivoDeReporte)
                .map(this::leerReporteDesdeArchivo)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Reporte::getFecha).reversed())
                .collect(Collectors.toList());
    }

    private boolean esArchivoDeReporte(Path path) {
        String nombre = path.getFileName().toString();
        return nombre.startsWith("reporte_") && nombre.endsWith(".txt");
    }

    private Reporte leerReporteDesdeArchivo(Path archivo) {
        try {
            String nombreArchivo = archivo.getFileName().toString();
            LocalDate fecha = LocalDate.parse(
                    nombreArchivo.substring(8, 18));
            String usuario = nombreArchivo.split("_|\\.")[2];
            Reporte reporte = new Reporte(fecha, usuario);
            Files.lines(archivo)
                    .skip(1)
                    .forEach(linea -> procesarLineaReporte(linea, reporte));
            return reporte;
        } catch (Exception e) {
            System.err.println("Error leyendo archivo " + archivo + ": " + e.getMessage());
            return null;
        }
    }

    private void procesarLineaReporte(String linea, Reporte reporte) {
        String[] partes = linea.split(",");
        if (partes.length >= 4) {
            reporte.agregarVenta(
                    partes[0].trim(),
                    Integer.parseInt(partes[1].trim()),
                    Double.parseDouble(partes[2].trim()),
                    Double.parseDouble(partes[3].trim()),
                    partes.length > 4 ? partes[4].trim() : "N/A"
            );
        }
    }
}