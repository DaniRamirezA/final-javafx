package org.example.finaljavafx.ADaniela.dao;

import org.example.finaljavafx.ADaniela.models.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private static final String ARCHIVO = "data/productos.dat";

    public void guardar(List<Producto> productos) {
        try {
            new File("data").mkdirs();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO));
            oos.writeObject(productos);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> cargar() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO));
            return (List<Producto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}

