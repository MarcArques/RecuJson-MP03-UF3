package com.project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Clase utilitaria para trabajar con JSON usando org.json.
 */
public class JsonUtils {

    /**
     * Lee un archivo JSON y lo convierte en un JSONObject.
     * @param path Ruta del archivo.
     * @return JSONObject con los datos del archivo.
     * @throws IOException Si hay un error al leer el archivo.
     */
    public static JSONObject readJsonFromFile(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        return new JSONObject(content);
    }

    /**
     * Guarda un JSONObject en un archivo.
     * @param path Ruta del archivo de salida.
     * @param json Objeto JSON a guardar.
     * @throws IOException Si hay un error al escribir el archivo.
     */
    public static void writeJsonToFile(String path, JSONObject json) throws IOException {
        Files.write(Paths.get(path), json.toString(4).getBytes()); // Indentación 4 espacios
    }

    /**
     * Convierte una matriz de enteros a un JSONArray 2D.
     * @param matrix Matriz de enteros.
     * @return JSONArray de arrays.
     */
    public static JSONArray intMatrixToJsonArray(int[][] matrix) {
        JSONArray array = new JSONArray();
        for (int[] row : matrix) {
            JSONArray jsonRow = new JSONArray();
            for (int value : row) {
                jsonRow.put(value);
            }
            array.put(jsonRow);
        }
        return array;
    }

    /**
     * Convierte una lista de objetos que implementan JsonSerializable a JSONArray.
     * @param list Lista de objetos.
     * @return JSONArray con los objetos convertidos.
     */
    public static JSONArray objectListToJsonArray(List<? extends JsonSerializable> list) {
        JSONArray array = new JSONArray();
        for (JsonSerializable obj : list) {
            array.put(new JSONObject(obj.toJson()));
        }
        return array;
    }
}
