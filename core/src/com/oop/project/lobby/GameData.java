package com.oop.project.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class GameData {
    private String saveName;
    private Party party;
    private FileHandle fileHandle;

    private static HashMap<String, GameData> saveGames;
    static {
        saveGames = new HashMap<>();
    }

    public GameData(String saveName) {
        if(saveGames.containsKey(saveName))
            throw new RuntimeException();

        this.saveName = saveName;
        this.party = new Party(saveName);
        this.fileHandle = Gdx.files.local("saves/"+saveName+".json");
        saveGames.put(saveName, this);
    }

    public static GameData loadGame(String name) {
        if(!saveGames.containsKey(name))
            throw new RuntimeException();
        return json.fromJson(GameData.class, Base64Coder.decodeString(saveGames.get(name).fileHandle.readString()));
    }

    public void saveGame() {
        fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(this)), false);
    }

    public static Collection<GameData> getSaves() {
        return saveGames.values();
    }

    public Party getParty() {
        return party;
    }

    @Override
    public String toString() {
        return saveName;
    }
}
