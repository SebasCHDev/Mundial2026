package negocio;

import dominio.*;
import java.util.*;

public class SimuladorFaseGrupos {

    public List<Partido> simularFaseGrupos(List<Grupo> grupos) {
        List<Partido> todosLosPartidos = new ArrayList<>();

        for (Grupo grupo : grupos) {
            List<Seleccion> selecciones = grupo.getSelecciones();
            for (int i = 0; i < selecciones.size(); i++) {
                for (int j = i + 1; j < selecciones.size(); j++) {
                    Partido p1 = new Partido(selecciones.get(i), selecciones.get(j));
                    p1.simular();
                    todosLosPartidos.add(p1);

                    // Partido de vuelta (opcional: Mundial 2026 no lo tiene, pero si quieres round-robin simple, solo uno basta)
                    // Partido p2 = new Partido(selecciones.get(j), selecciones.get(i));
                    // p2.simular();
                    // todosLosPartidos.add(p2);
                }
            }
        }

        return todosLosPartidos;
    }

    public List<Seleccion> obtenerClasificados(List<Grupo> grupos, List<Partido> partidos) {
        Map<Seleccion, EstadisticaEquipo> estadisticas = new HashMap<>();

        for (Grupo g : grupos) {
            for (Seleccion s : g.getSelecciones()) {
                estadisticas.put(s, new EstadisticaEquipo(s));
            }
        }

        for (Partido p : partidos) {
            estadisticas.get(p.getEquipoLocal()).sumarPartido(p.getGolesLocal(), p.getGolesVisitante());
            estadisticas.get(p.getEquipoVisitante()).sumarPartido(p.getGolesVisitante(), p.getGolesLocal());
        }

        List<Seleccion> clasificados = new ArrayList<>();
        List<EstadisticaEquipo> todosTerceros = new ArrayList<>();

        for (Grupo g : grupos) {
            List<EstadisticaEquipo> stats = g.getSelecciones().stream()
                    .map(estadisticas::get)
                    .sorted()
                    .toList();

            clasificados.add(stats.get(0).getSeleccion()); // 1°
            clasificados.add(stats.get(1).getSeleccion()); // 2°
            todosTerceros.add(stats.get(2));              // 3°
        }

        // Ordenar terceros y tomar 8 mejores
        todosTerceros.sort(null);
        for (int i = 0; i < 8; i++) {
            clasificados.add(todosTerceros.get(i).getSeleccion());
        }

        return clasificados; // 24 + 8 = 32 equipos
    }
}