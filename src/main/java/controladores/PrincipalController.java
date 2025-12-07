package controladores;

import dominio.Grupo;
import dominio.Seleccion;
import dominio.Partido;
import negocio.SorteoMundial;
import negocio.SimuladorFaseGrupos;
import negocio.SimuladorFaseFinal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrincipalController {

    @FXML
    private Button btnSortear;

    @FXML
    private ListView<String> listaGrupos;

    @FXML
    private ListView<String> listaResultadosGrupos; // ← Para resultados de la fase de grupos

    @FXML
    private ListView<String> listaResultadosFinal;  // ← Para resultados de octavos, cuartos, etc.

    private List<Grupo> gruposSorteados;

    private SorteoMundial sorteo;

    @FXML
    private void initialize() {
        sorteo = new SorteoMundial();
    }

    @FXML
    private void onSortear() {
        gruposSorteados = sorteo.realizarSorteo();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Grupo g : gruposSorteados) {
            items.add(g.toString());
        }
        listaGrupos.setItems(items);
    }

    @FXML
    private void simularFaseGrupos() {
        if (gruposSorteados == null) {
            gruposSorteados = sorteo.realizarSorteo();
        }

        ObservableList<String> items = FXCollections.observableArrayList();

        for (Grupo grupo : gruposSorteados) {
            items.add("=== GRUPO " + grupo.getNombre() + " ===");
            List<Seleccion> selecciones = grupo.getSelecciones();
            for (int i = 0; i < selecciones.size(); i++) {
                for (int j = i + 1; j < selecciones.size(); j++) {
                    Partido partido = new Partido(selecciones.get(i), selecciones.get(j));
                    partido.simular();
                    items.add(partido.toString());
                }
            }
            items.add(""); // Línea en blanco entre grupos
        }

        listaResultadosGrupos.setItems(items); // ← Importante: usa listaResultadosGrupos
    }

    @FXML
    private void simularFaseFinal() {
        if (gruposSorteados == null) {
            gruposSorteados = sorteo.realizarSorteo();
        }

        // Simulamos fase de grupos solo para obtener clasificados
        SimuladorFaseGrupos simGrupos = new SimuladorFaseGrupos();
        List<Partido> partidos = simGrupos.simularFaseGrupos(gruposSorteados);
        List<Seleccion> clasificados = simGrupos.obtenerClasificados(gruposSorteados, partidos);

        // Simulamos fase final
        SimuladorFaseFinal simFinal = new SimuladorFaseFinal();
        List<String> resultados = simFinal.simularFaseFinal(clasificados);

        ObservableList<String> items = FXCollections.observableArrayList(resultados);
        listaResultadosFinal.setItems(items); // ← Usa listaResultadosFinal
    }

}