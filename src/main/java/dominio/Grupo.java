package dominio;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
    private String nombre; // A, B, C...
    private List<Seleccion> selecciones;

    public Grupo(String nombre) {
        this.nombre = nombre;
        this.selecciones = new ArrayList<>();
    }

    public void agregarSeleccion(Seleccion s) {
        selecciones.add(s);
    }

    public List<Seleccion> getSelecciones() {
        return selecciones;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre + ": " + selecciones;
    }
}