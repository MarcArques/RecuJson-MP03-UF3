package com.project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Programa principal amb menú textual per gestionar el joc.
 */
public class Main {
    private static Game game;
    private static String loadedFilename = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> jsonFiles = findJsonFiles();
        if (jsonFiles.isEmpty()) {
            System.out.println("No s'ha trobat cap fitxer JSON. Es crearà un joc nou.");
            game = new Game("Nou Joc", new ArrayList<>());
        } else {
            System.out.println("Fitxers JSON trobats:");
            System.out.println("0. Crear un joc nou");
            for (int i = 0; i < jsonFiles.size(); i++) {
                System.out.println((i + 1) + ". " + jsonFiles.get(i));
            }

            System.out.print("Selecciona el número del fitxer a carregar (enter): ");
            String input = scanner.nextLine();
            try {
                int selectedIndex = Integer.parseInt(input);
                if (selectedIndex == 0) {
                    game = new Game("Nou Joc", new ArrayList<>());
                } else if (selectedIndex >= 1 && selectedIndex <= jsonFiles.size()) {
                    String selectedFile = jsonFiles.get(selectedIndex - 1);
                    JSONObject json = JsonUtils.readJsonFromFile(selectedFile);
                    game = parseGameFromJson(json);
                    loadedFilename = selectedFile;
                    System.out.println("Fitxer '" + selectedFile + "' carregat correctament.");
                } else {
                    throw new NumberFormatException();
                }
            } catch (Exception e) {
                System.out.println("Entrada no vàlida. Es crearà un joc nou.");
                game = new Game("Nou Joc", new ArrayList<>());
            }
        }

        int option = -1;
        do {
            clearConsole();

            System.out.println("\n=== MENÚ DEL JOC ===");
            System.out.println("1. Veure nivells");
            System.out.println("2. Afegir nivell");
            System.out.println("3. Esborrar nivell");
            System.out.println("4. Guardar a JSON");
            System.out.println("5. Sortir");
            System.out.print("Opció: ");

            String input = scanner.nextLine();
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                option = -1;
            }

            switch (option) {
                case 1:
                    showLevels();
                    break;
                case 2:
                    addLevel(scanner);
                    break;
                case 3:
                    deleteLevel(scanner);
                    break;
                case 4:
                    saveGame(scanner);
                    break;
                case 5:
                    System.out.println("Sortint del programa...");
                    break;
                default:
                    System.out.println("Opció no vàlida.");
            }

            System.out.println("\nPrem Enter per continuar...");
            scanner.nextLine();

        } while (option != 5);

        scanner.close();
    }

    private static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private static void showLevels() {
        if (game.levels.isEmpty()) {
            System.out.println("No hi ha cap nivell.");
        } else {
            System.out.println("Nivells:");
            for (int i = 0; i < game.levels.size(); i++) {
                System.out.println((i + 1) + ". " + game.levels.get(i).name);
            }
        }
    }

    private static void addLevel(Scanner scanner) {
        System.out.print("Nom del nivell: ");
        String name = scanner.nextLine();

        System.out.print("Descripció del nivell: ");
        String desc = scanner.nextLine();

        Level newLevel = new Level(name, desc, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        game.levels.add(newLevel);
        System.out.println("Nivell afegit correctament.");
    }

    private static void deleteLevel(Scanner scanner) {
        showLevels();
        if (game.levels.isEmpty()) return;

        System.out.print("Introdueix el número del nivell a esborrar: ");
        String input = scanner.nextLine();
        int index;

        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no vàlida. Introdueix un número.");
            return;
        }

        if (index >= 1 && index <= game.levels.size()) {
            game.levels.remove(index - 1);
            System.out.println("Nivell esborrat.");
        } else {
            System.out.println("Índex invàlid.");
        }
    }

    private static void saveGame(Scanner scanner) {
        if (loadedFilename != null) {
            try {
                JSONObject json = new JSONObject(game.toJson());
                JsonUtils.writeJsonToFile(loadedFilename, json);
                System.out.println("Joc desat correctament a " + loadedFilename);
            } catch (IOException e) {
                System.out.println("Error al desar el fitxer: " + e.getMessage());
            }
        } else {
            System.out.print("Nom del fitxer (ex: game_updated.json): ");
            String filename = scanner.nextLine().trim();

            if (filename.isEmpty()) {
                System.out.println("Nom de fitxer invàlid.");
                return;
            }

            try {
                JSONObject json = new JSONObject(game.toJson());
                JsonUtils.writeJsonToFile(filename, json);
                loadedFilename = filename;
                System.out.println("Joc desat correctament a " + filename);
            } catch (IOException e) {
                System.out.println("Error al desar el fitxer: " + e.getMessage());
            }
        }
    }

    private static Game parseGameFromJson(JSONObject json) {
        String name = json.getString("name");
        List<Level> levels = new ArrayList<>();

        JSONArray levelsArray = json.optJSONArray("levels");
        if (levelsArray != null) {
            for (Object obj : levelsArray) {
                JSONObject levelJson = (JSONObject) obj;
                String levelName = levelJson.getString("name");
                String description = levelJson.getString("description");

                List<Layer> layers = new ArrayList<>();
                JSONArray layersArray = levelJson.optJSONArray("layers");
                if (layersArray != null) {
                    for (Object layerObj : layersArray) {
                        JSONObject layerJson = (JSONObject) layerObj;
                        String layerName = layerJson.getString("name");
                        int x = layerJson.getInt("x");
                        int y = layerJson.getInt("y");
                        int depth = layerJson.getInt("depth");
                        String tilesSheetFile = layerJson.getString("tilesSheetFile");
                        int tilesWidth = layerJson.getInt("tilesWidth");
                        int tilesHeight = layerJson.getInt("tilesHeight");

                        JSONArray tileMapArray = layerJson.getJSONArray("tileMap");
                        int[][] tileMap = new int[tileMapArray.length()][];
                        for (int i = 0; i < tileMapArray.length(); i++) {
                            JSONArray row = tileMapArray.getJSONArray(i);
                            tileMap[i] = new int[row.length()];
                            for (int j = 0; j < row.length(); j++) {
                                tileMap[i][j] = row.getInt(j);
                            }
                        }

                        layers.add(new Layer(layerName, x, y, depth, tilesSheetFile, tilesWidth, tilesHeight, tileMap));
                    }
                }

                List<Zone> zones = new ArrayList<>();
                JSONArray zonesArray = levelJson.optJSONArray("zones");
                if (zonesArray != null) {
                    for (Object zoneObj : zonesArray) {
                        JSONObject zoneJson = (JSONObject) zoneObj;
                        zones.add(new Zone(
                                zoneJson.getString("type"),
                                zoneJson.getString("color"),
                                zoneJson.getInt("x"),
                                zoneJson.getInt("y"),
                                zoneJson.getInt("width"),
                                zoneJson.getInt("height")
                        ));
                    }
                }

                List<Sprite> sprites = new ArrayList<>();
                JSONArray spritesArray = levelJson.optJSONArray("sprites");
                if (spritesArray != null) {
                    for (Object spriteObj : spritesArray) {
                        JSONObject spriteJson = (JSONObject) spriteObj;
                        sprites.add(new Sprite(
                                spriteJson.getString("type"),
                                spriteJson.getString("imageFile"),
                                spriteJson.getInt("x"),
                                spriteJson.getInt("y"),
                                spriteJson.getInt("width"),
                                spriteJson.getInt("height")
                        ));
                    }
                }

                levels.add(new Level(levelName, description, layers, zones, sprites));
            }
        }

        return new Game(name, levels);
    }

    private static List<String> findJsonFiles() {
        List<String> jsonFiles = new ArrayList<>();
        java.io.File dir = new java.io.File(".");

        for (java.io.File file : dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                jsonFiles.add(file.getName());
            }
        }

        return jsonFiles;
    }
}
