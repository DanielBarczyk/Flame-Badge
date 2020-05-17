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

    private static HashMap<String, GameData> saveGames;
    static {
        saveGames = new HashMap<>();
    }

    public GameData() {}

    public GameData(String saveName) {
        if(saveGames.containsKey(saveName))
            throw new RuntimeException();

        this.saveName = saveName;
        this.party = new Party(saveName);
        saveGames.put(saveName, this);
    }

    public static GameData loadGame(String name) {
        if(!saveGames.containsKey(name))
            throw new RuntimeException();
        FileHandle fileHandle = Gdx.files.local("saves/"+name+".json");
        return json.fromJson(GameData.class, Base64Coder.decodeString(fileHandle.readString()));
    }

    public void saveGame() {
        FileHandle fileHandle = Gdx.files.local("saves/"+saveName+".json");
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
