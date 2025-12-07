package dominio;

import java.util.Objects;

public class Seleccion {
    private String nombre;
    private String confederacion;
    private double probabilidadMundial;

    public Seleccion(String nombre, String confederacion, double probabilidadMundial) {
        this.nombre = nombre;
        this.confederacion = confederacion;
        this.probabilidadMundial = probabilidadMundial;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getConfederacion() {
        return confederacion;
    }

    public double getProbabilidadMundial() {
        return probabilidadMundial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seleccion)) return false;
        Seleccion that = (Seleccion) o;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}