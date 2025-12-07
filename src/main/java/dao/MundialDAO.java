package dao;

import dominio.Seleccion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MundialDAO {

    public static List<List<Seleccion>> crearBombos() {
        List<Seleccion> todas = cargarSeleccionesDesdeExcel();
        List<List<Seleccion>> bombos = new ArrayList<>();

        // 4 bombos de 12 selecciones (0-11, 12-23, 24-35, 36-47)
        for (int bombo = 0; bombo < 4; bombo++) {
            List<Seleccion> grupo = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                grupo.add(todas.get(bombo * 12 + i));
            }
            bombos.add(grupo);
        }

        return bombos;
    }

    public static List<Seleccion> cargarSeleccionesDesdeExcel() {
        List<Seleccion> selecciones = new ArrayList<>();
        String nombreArchivo = "Selecciones y Probabilidad.xlsx";

        try (InputStream is = MundialDAO.class.getClassLoader().getResourceAsStream(nombreArchivo);
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheet("Hoja2");
            if (sheet == null) {
                throw new IllegalStateException("No se encontró la hoja 'Hoja2' en el Excel.");
            }

            // Comenzamos desde la fila 1 (la 0 es encabezado)
            for (int i = 1; i <= 48; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Columnas: A=Ranking (ignoramos), B=Selección, C=Confederación, D=Probabilidad
                String nombre = row.getCell(1).getStringCellValue().trim();
                String confederacion = row.getCell(2).getStringCellValue().trim();
                double probabilidad = row.getCell(3).getNumericCellValue();

                selecciones.add(new Seleccion(nombre, confederacion, probabilidad));
            }

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el archivo Excel: " + e.getMessage());
        }

        if (selecciones.size() != 48) {
            throw new IllegalStateException("Se esperaban 48 selecciones, pero se cargaron " + selecciones.size());
        }

        return selecciones;
    }
}