package org.example.finaljavafx.ADaniela.dao.impl.IO;

import org.example.finaljavafx.ADaniela.dao.ReportesDAO;
import org.example.finaljavafx.ADaniela.models.Reporte;
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
        // Genera un timestamp único (formato: HHmmss)
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HHmmss"));

        String usuario = reporte.getUsuario();
        String nombreArchivo = String.format("%sreporte_%s_%s_%s.txt",
                directorioReportes,
                reporte.getFecha(),
                usuario,
                timestamp); // Ejemplo: reporte_2025-07-20_trabajador1_143022.txt

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            // Encabezado
            writer.println("Producto,Cantidad,PrecioUnitario,Total,Usuario,Timestamp");

            // Datos
            reporte.getVentas().forEach(item ->
                    writer.printf("%s,%d,%.0f,%.0f,%s,%s%n",
                            item.getNombreProducto(),
                            item.getCantidad(),
                            item.getPrecioUnitario(),
                            item.getTotal(),
                            usuario,
                            timestamp) // Añade timestamp a cada registro
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

    @Override
    public Reporte obtenerReportePorFecha(LocalDate fecha) throws IOException {
        // Ahora busca cualquier reporte de esa fecha (ignorando usuario)
        return Files.list(Paths.get(directorioReportes))
                .filter(path -> path.getFileName().toString().contains("reporte_" + fecha))
                .findFirst()
                .map(this::leerReporteDesdeArchivo)
                .orElse(null);
    }

    private boolean esArchivoDeReporte(Path path) {
        String nombre = path.getFileName().toString();
        return nombre.startsWith("reporte_") && nombre.endsWith(".txt");
    }

    private Reporte leerReporteDesdeArchivo(Path archivo) {
        try {
            String nombreArchivo = archivo.getFileName().toString();
            LocalDate fecha = LocalDate.parse(
                    nombreArchivo.substring(8, 18)); // Extrae fecha de "reporte_AAAA-MM-DD_...txt"

            String usuario = nombreArchivo.split("_|\\.")[2]; // Extrae usuario
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
        if (partes.length >= 4) { // Producto,Cantidad,PrecioUnitario,Total[,Usuario]
            reporte.agregarVenta(
                    partes[0].trim(),
                    Integer.parseInt(partes[1].trim()),
                    Double.parseDouble(partes[2].trim()), // Precio unitario
                    Double.parseDouble(partes[3].trim()), // Total
                    partes.length > 4 ? partes[4].trim() : "N/A" // Usuario (opcional)
            );
        }
    }
}