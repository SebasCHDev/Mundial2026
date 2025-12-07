package dominio;

import java.util.Comparator;

public class EstadisticaEquipo implements Comparable<EstadisticaEquipo> {
    private Seleccion seleccion;
    private int puntos;
    private int golesFavor;
    private int golesContra;
    private int partidosJugados;

    public EstadisticaEquipo(Seleccion seleccion) {
        this.seleccion = seleccion;
        this.puntos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.partidosJugados = 0;
    }

    public void sumarPartido(int golesFavor, int golesContra) {
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;
        this.partidosJugados++;

        if (golesFavor > golesContra) {
            puntos += 3;
        } else if (golesFavor == golesContra) {
            puntos += 1;
        }
    }

    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }

    // Getters
    public Seleccion getSeleccion() { return seleccion; }
    public int getPuntos() { return puntos; }
    public int getGolesFavor() { return golesFavor; }
    public int getGolesContra() { return golesContra; }

    @Override
    public int compareTo(EstadisticaEquipo o) {
        if (this.puntos != o.puntos) {
            return Integer.compare(o.puntos, this.puntos); // descendente
        }
        if (this.getDiferenciaGoles() != o.getDiferenciaGoles()) {
            return Integer.compare(o.getDiferenciaGoles(), this.getDiferenciaGoles());
        }
        return Integer.compare(o.golesFavor, this.golesFavor);
    }

    @Override
    public String toString() {
        return String.format("%-20s | %2d pts | %2d GF | %2d GC | %+3d DG",
                seleccion.getNombre(), puntos, golesFavor, golesContra, getDiferenciaGoles());
    }
}