package com.project;

import org.json.JSONObject;

/**
 * Representa una zona (por ejemplo, agua o piedra) en el mapa.
 */
public class Zone implements JsonSerializable {
    public String type; // Tipo de zona
    public String color; // Color de la zona
    public int x, y, width, height; // Posición y tamaño

    public Zone(String type, String color, int x, int y, int width, int height) {
        this.type = type;
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Convierte la zona a formato JSON.
     */
    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("color", color);
        json.put("x", x);
        json.put("y", y);
        json.put("width", width);
        json.put("height", height);
        return json.toString(4);
    }
}
