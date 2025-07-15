package org.example.finaljavafx.dao.impl.reportes;

import org.example.finaljavafx.dao.ReportesDAO;
import org.example.finaljavafx.dao.impl.DatosIO;
import org.example.finaljavafx.model.*;
import org.example.finaljavafx.model.usuarios.Cajero;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.finaljavafx.model.Productos.fromString;

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
        String reporteString = reporte.toString() + "\n";
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
        String[] partes = linea.split(", ");

        // partes[1] : Cajero
        String[] partesCajero = partes[1].split("#");
        String[] partesPerfil = partesCajero[2].split("-");
        Perfil perfil = new Perfil(partesPerfil[0], partesPerfil[1], partesPerfil[2], partesPerfil[3]);
        Cajero cajero = new Cajero(partesCajero[0], partesCajero[1], perfil);

        // partes[2] : Fecha
        // partes[3] : Ventas
        String[] partesVentas = partes[3].split("#");

        List<Venta> ventas = new ArrayList<>();

        for (String ventaString : partesVentas) {
            if (ventaString.isEmpty()) {
                throw new IllegalArgumentException("El reporte no puede contener campos vacíos");
            }

            String[] partesVenta = ventaString.split("-");

            String[] partesItemsVenta = partesVenta[4].split("\\^");

            List<ItemVenta> itemsVenta = new ArrayList<>();

            for (String item : partesItemsVenta) {
                if (item.isEmpty()) {
                    throw new IllegalArgumentException("El reporte no puede contener items vacíos");
                }

                String[] partesItem = item.split(";");
                String[] partesProducto = partesItem[0].split(":");

                Producto producto = new Producto(Double.parseDouble(partesProducto[0]), fromString(partesProducto[1]));

                ItemVenta itemVenta = new ItemVenta(producto, Integer.parseInt(partesItem[1]));
                itemsVenta.add(itemVenta);
            }

            Venta venta = new Venta(Integer.parseInt(partesVenta[0]), partesVenta[1], cajero, itemsVenta, Double.parseDouble(partesVenta[3]));
            ventas.add(venta);
        }

        // partes[4] : Total
        return new Reporte(Integer.parseInt(partes[0]), cajero, partes[2], ventas, Double.parseDouble(partes[4]));
    }
}
