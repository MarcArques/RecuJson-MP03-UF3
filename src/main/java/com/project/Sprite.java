package com.project;

import org.json.JSONObject;

/**
 * Representa un sprite (personaje o decorado) en el mapa.
 */
public class Sprite implements JsonSerializable {
    public String type; // Tipo de sprite
    public String imageFile; // Archivo de imagen
    public int x, y; // Posición
    public int spriteWidth, spriteHeight; // Dimensiones

    public Sprite(String type, String imageFile, int x, int y, int spriteWidth, int spriteHeight) {
        this.type = type;
        this.imageFile = imageFile;
        this.x = x;
        this.y = y;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    /**
     * Convierte el sprite a formato JSON.
     */
    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("imageFile", imageFile);
        json.put("x", x);
        json.put("y", y);
        json.put("width", spriteWidth);
        json.put("height", spriteHeight);
        return json.toString(4);
    }
}
