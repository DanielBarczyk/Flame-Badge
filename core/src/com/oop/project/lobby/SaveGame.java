package com.oop.project.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class SaveGame {

    private static final HashMap<String, GameData> saveGames;
    static {
        saveGames = new HashMap<>();
        try {
            loadFromJSON();
        } catch(Exception ignored) {}
    }

    public static boolean exists(String name) {
        return saveGames.containsKey(name);
    }

    public static Collection<GameData> getSaves() {
        return saveGames.values();
    }

    public static HashMap<String, GameData> getSaveGames() {
        return saveGames;
    }

    @SuppressWarnings("unchecked")
    public static void loadFromJSON() {
        FileHandle fileHandle = Gdx.files.local("save-index.json");
        ArrayList<? extends String> saveNames = json.fromJson(ArrayList.class, fileHandle.readString());
        saveGames.clear();
        for(String name : saveNames) {
            try {
                saveGames.put(name, GameData.loadGame(name));
            } catch (GameData.InvalidSaveException e) {
                System.out.println("Loading save game \"" + name +"\" has failed.");
            }

        }
    }

    public static void saveToJSON() {
        System.out.println("Proceeding to save current game files.");
        ArrayList<String> saveNames = new ArrayList<>(saveGames.keySet());
        System.out.println(saveNames);
        FileHandle fileHandle = Gdx.files.local("save-index.json");
        fileHandle.writeString(json.toJson(saveNames), false);
    }
}
