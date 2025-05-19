package com.project;

import org.json.JSONObject;
import java.util.List;

/**
 * Representa el juego completo.
 */
public class Game implements JsonSerializable {
    public String name; // Nombre del juego
    public List<Level> levels; // Lista de niveles

    public Game(String name, List<Level> levels) {
        this.name = name;
        this.levels = levels;
    }

    /**
     * Convierte el objeto Game a formato JSON.
     */
    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("levels", JsonUtils.objectListToJsonArray(levels));
        return json.toString(4);
    }
}
