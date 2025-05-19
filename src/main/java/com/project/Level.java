package com.project;

import org.json.JSONObject;
import java.util.List;

/**
 * Representa un nivel del juego.
 */
public class Level implements JsonSerializable {
    public String name; // Nombre del nivel
    public String description; // Descripción del nivel
    public List<Layer> layers; // Lista de capas
    public List<Zone> zones; // Lista de zonas
    public List<Sprite> sprites; // Lista de sprites

    public Level(String name, String description, List<Layer> layers, List<Zone> zones, List<Sprite> sprites) {
        this.name = name;
        this.description = description;
        this.layers = layers;
        this.zones = zones;
        this.sprites = sprites;
    }

    /**
     * Convierte el objeto Level a formato JSON.
     */
    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("layers", JsonUtils.objectListToJsonArray(layers));
        json.put("zones", JsonUtils.objectListToJsonArray(zones));
        json.put("sprites", JsonUtils.objectListToJsonArray(sprites));
        return json.toString(4);
    }
}
