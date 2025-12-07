package negocio;

import dominio.Partido;
import dominio.Seleccion;
import java.util.*;

public class SimuladorFaseFinal {

    public List<String> simularFaseFinal(List<Seleccion> clasificados) {
        List<String> resultados = new ArrayList<>();

        if (clasificados.size() != 32) {
            throw new IllegalStateException("Se esperaban 32 equipos clasificados, pero hay " + clasificados.size());
        }

        Collections.shuffle(clasificados, new Random(42));

        // Dieciseisavos (32 → 16)
        resultados.add("=== DIECISEISAVOS DE FINAL ===");
        List<Seleccion> octavos = simularRonda(clasificados, resultados);

        // Octavos (16 → 8)
        resultados.add("=== OCTAVOS DE FINAL ===");
        List<Seleccion> cuartos = simularRonda(octavos, resultados);

        // Cuartos (8 → 4)
        resultados.add("=== CUARTOS DE FINAL ===");
        List<Seleccion> semifinales = simularRonda(cuartos, resultados);

        // Semis (4 → 2)
        resultados.add("=== SEMIFINALES ===");
        List<Seleccion> finalistas = new ArrayList<>();
        List<Seleccion> perdedoresSemifinales = new ArrayList<>(); // ← Nueva lista para los perdedores

        for (int i = 0; i < semifinales.size(); i += 2) {
            if (i + 1 >= semifinales.size()) break;
            Partido p = new Partido(semifinales.get(i), semifinales.get(i + 1));
            p.simular();
            resultados.add(p.toString());

            Seleccion ganador = p.getGanador();
            Seleccion perdedor = p.getEquipoLocal().equals(ganador) ? p.getEquipoVisitante() : p.getEquipoLocal();

            finalistas.add(ganador);
            perdedoresSemifinales.add(perdedor); // ← Guardamos el perdedor
        }

        // Final
        resultados.add("=== FINAL ===");
        Partido partidoFinal = new Partido(finalistas.get(0), finalistas.get(1));
        partidoFinal.simular();
        resultados.add(partidoFinal.toString());
        resultados.add("🏆 ¡CAMPEÓN: " + partidoFinal.getGanador().getNombre() + "!");

        // Tercer lugar
        resultados.add("=== PARTIDO POR EL TERCER LUGAR ===");
        if (perdedoresSemifinales.size() == 2) {
            Partido partidoTercero = new Partido(perdedoresSemifinales.get(0), perdedoresSemifinales.get(1));
            partidoTercero.simular();
            resultados.add(partidoTercero.toString());
            resultados.add("🥉 TERCER LUGAR: " + partidoTercero.getGanador().getNombre());
        } else {
            resultados.add("No se pudo determinar el tercer lugar.");
        }

        return resultados;
    }

    private List<Seleccion> simularRonda(List<Seleccion> equipos, List<String> resultados) {
        List<Seleccion> ganadores = new ArrayList<>();
        for (int i = 0; i < equipos.size(); i += 2) {
            if (i + 1 >= equipos.size()) {
                // pasa automáticamente (no debería pasar con 16, 8, 4)
                ganadores.add(equipos.get(i));
                continue;
            }
            Partido p = new Partido(equipos.get(i), equipos.get(i + 1));
            p.simular();
            resultados.add(p.toString());
            ganadores.add(p.getGanador());
        }
        return ganadores;
    }
}