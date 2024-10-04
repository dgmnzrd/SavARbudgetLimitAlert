package com.example.appalertbudgetlimit;

public class Product {
    private final String nombre;
    private final double precio;

    public Product(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}
