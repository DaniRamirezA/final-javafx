package org.example.finaljavafx.ADaniela.dao;

import java.io.IOException;
import java.util.Map;

public interface VentasDAO {
    Map<String, int[]> cargarInventario() throws IOException;
    void actualizarInventario(Map<String, int[]> inventario) throws IOException;
}
