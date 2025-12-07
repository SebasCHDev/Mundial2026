package negocio;

import dominio.Grupo;
import dominio.Seleccion;
import dao.MundialDAO;

import java.util.*;

public class SorteoMundial {
    private List<List<Seleccion>> bombos;
    private List<Grupo> grupos;

    public SorteoMundial() {
        this.bombos = MundialDAO.crearBombos(); // ahora desde Excel
        this.grupos = new ArrayList<>();
        for (char c = 'A'; c <= 'L'; c++) {
            grupos.add(new Grupo(String.valueOf(c)));
        }
    }

    public List<Grupo> realizarSorteo() {
        Random rand = new Random();

        // Mezclamos los bombos 1, 2, 3 (el bombo 0 no se mezcla en el sorteo real, pero podemos mezclar todos)
        for (int i = 1; i < bombos.size(); i++) {
            Collections.shuffle(bombos.get(i), rand);
        }

        List<Grupo> grupos = new ArrayList<>();
        for (char c = 'A'; c <= 'L'; c++) {
            grupos.add(new Grupo(String.valueOf(c)));
        }

        // Asignar: de cada bombo, tomamos un equipo para cada grupo
        for (int i = 0; i < 12; i++) { // 12 grupos
            for (int j = 0; j < 4; j++) { // 4 bombos
                Seleccion sel = bombos.get(j).get(i);
                grupos.get(i).agregarSeleccion(sel);
            }
        }

        return grupos;
    }

    public Map<String, String> crearMapaGrupoPorSeleccion() {
        Map<String, String> mapa = new HashMap<>();
        for (Grupo g : grupos) {
            for (Seleccion s : g.getSelecciones()) {
                mapa.put(s.getNombre(), g.getNombre());
            }
        }
        return mapa;
    }
}