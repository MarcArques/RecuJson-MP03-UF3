package com.project;

import org.json.JSONObject;

/**
 * Representa una capa de tiles en un nivel.
 */
public class Layer implements JsonSerializable {
    public String name; // Nombre de la capa
    public int x, y; // Posición de la capa
    public int depth; // Profundidad (orden de dibujo)
    public String tilesSheetFile; // Archivo del tileset
    public int tilesWidth, tilesHeight; // Dimensiones de cada tile
    public int[][] tileMap; // Mapa de tiles

    public Layer(String name, int x, int y, int depth, String tilesSheetFile, int tilesWidth, int tilesHeight, int[][] tileMap) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.tilesSheetFile = tilesSheetFile;
        this.tilesWidth = tilesWidth;
        this.tilesHeight = tilesHeight;
        this.tileMap = tileMap;
    }

    /**
     * Convierte la capa a formato JSON.
     */
    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("x", x);
        json.put("y", y);
        json.put("depth", depth);
        json.put("tilesSheetFile", tilesSheetFile);
        json.put("tilesWidth", tilesWidth);
        json.put("tilesHeight", tilesHeight);
        json.put("tileMap", JsonUtils.intMatrixToJsonArray(tileMap));
        return json.toString(4);
    }
}
