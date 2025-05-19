package com.project;

/**
 * Interfaz que define el método toJson() para convertir objetos a JSON.
 */
public interface JsonSerializable {
    /**
     * Convierte el objeto actual en una cadena JSON.
     * @return Cadena en formato JSON.
     */
    String toJson();
}
