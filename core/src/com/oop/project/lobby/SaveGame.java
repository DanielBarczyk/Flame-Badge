package com.oop.project.lobby;

import java.util.Collection;
import java.util.HashMap;

public class SaveGame {

    private static HashMap<String, GameData> saveGames;
    static {
        saveGames = new HashMap<>();
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
}
