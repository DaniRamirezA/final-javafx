package org.example.finaljavafx.model;

public enum Productos {
    CAFE_AMERICANO("Café Americano"),
    CAFE_CAPUCCINO("Café Capuccino"),
    CAFE_ESPRESSO("Café Espresso"),
    CAFE_IRLANDES("Café Irlandés"),
    CAFE_HAWAIANO("Café Hawaiano"),
    CAFE_CON_LECHE("Café con Leche"),
    TE("Te"),
    PAN_DE_QUESO("Pan de Queso"),
    PANDEBONO("Pandebono"),
    PALITO_DE_QUESO("Palito de Queso"),
    ALMOJABANA("Almojábana"),
    PAN_DE_YUCA("Pan de Yuca"),
    BUNUELO("Buñuelo"),
    EMPANADA("Empanada"),
    PAN_INTEGRAL("Pan Integral"),
    PAPA_CON_RELLENO("Papa con Relleno"),
    CROISSANT("Croissant"),
    CHOCORRAMO("Chocoramo"),
    PAN_ARTESANAL("Pan Artesanal"),
    SANDWICH("Sandwich");

    private final String nombre;

    Productos(String s) {
        this.nombre = s;
    }

    public String getNombre() {
        return nombre;
    }

    public static Productos fromString(String nombre) {
        for (Productos producto : Productos.values()) {
            if (producto.nombre.equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        throw new IllegalArgumentException("Producto no encontrado: " + nombre);
    }
}
