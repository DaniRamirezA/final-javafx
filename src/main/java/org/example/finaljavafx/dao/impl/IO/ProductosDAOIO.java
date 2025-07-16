package org.example.finaljavafx.dao.impl.IO;

import org.example.finaljavafx.dao.ProductosDAO;
import org.example.finaljavafx.dao.impl.DatosIO;
import org.example.finaljavafx.model.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.finaljavafx.model.Productos.fromString;

public class ProductosDAOIO extends DatosIO implements ProductosDAO {

    private File productosDB;

    public ProductosDAOIO() {
        System.out.println("Inicializando la base de datos de productos en disco con IO");
        // Inicializar la base de datos de notas
        this.productosDB = new File("productos");
        if (!productosDB.exists()) {
            try {
                productosDB.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo productos: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void guardarProducto(Producto producto) {
        String productoString = producto.toString() + "\n";
        BufferedWriter bw;
        try {
            FileWriter fw = new FileWriter(productosDB, true);
            bw = new BufferedWriter(fw);
            bw.write(productoString);
            bw.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo productos: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Producto> obtenerProductos() {
        List<Producto> resultado = new ArrayList<>();
        try {
            FileReader fr = new FileReader(productosDB);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                Producto producto = transformString(linea);
                resultado.add(producto);
            }
            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    protected Producto transformString(String linea) {
        String[] partes = linea.split(":");
        return new Producto(Double.parseDouble(partes[0]), fromString(partes[1]));
    }
}
