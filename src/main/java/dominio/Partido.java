package dominio;

import java.util.Random;

public class Partido {
    private Seleccion equipoLocal;
    private Seleccion equipoVisitante;
    private int golesLocal;
    private int golesVisitante;

    public Partido(Seleccion local, Seleccion visitante) {
        this.equipoLocal = local;
        this.equipoVisitante = visitante;
        this.golesLocal = 0;
        this.golesVisitante = 0;
    }

    public void simular() {
        Random rand = new Random();
        double probLocal = equipoLocal.getProbabilidadMundial();
        double probVisitante = equipoVisitante.getProbabilidadMundial();

        // Normalizar las probabilidades para que sumen 1
        double total = probLocal + probVisitante;
        if (total == 0) {
            probLocal = 0.5;
            probVisitante = 0.5;
        } else {
            probLocal /= total;
            probVisitante /= total;
        }

        // Generar resultado aleatorio basado en probabilidades
        double random = rand.nextDouble();
        if (random < probLocal * 0.7) { // 70% de probabilidad de victoria local
            golesLocal = 2;
            golesVisitante = 0;
        } else if (random < probLocal * 0.7 + probVisitante * 0.7) { // 70% de probabilidad de victoria visitante
            golesLocal = 0;
            golesVisitante = 2;
        } else { // Empate (restante 60%)
            golesLocal = 1;
            golesVisitante = 1;
        }
    }

    public Seleccion getGanador() {
        if (golesLocal > golesVisitante) {
            return equipoLocal;
        } else if (golesVisitante > golesLocal) {
            return equipoVisitante;
        } else {
            // En caso de empate, elegir aleatoriamente (según regla del enunciado)
            return Math.random() < 0.5 ? equipoLocal : equipoVisitante;
        }
    }

    public int getGolesLocal() { return golesLocal; }
    public int getGolesVisitante() { return golesVisitante; }
    public Seleccion getEquipoLocal() { return equipoLocal; }
    public Seleccion getEquipoVisitante() { return equipoVisitante; }

    @Override
    public String toString() {
        return equipoLocal.getNombre() + " " + golesLocal + " - " + golesVisitante + " " + equipoVisitante.getNombre();
    }
}