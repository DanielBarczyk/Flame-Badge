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

    public GameData() {
        party = new Party();
    }

    public GameData(String saveName) {
        if(SaveGame.exists(saveName))
            throw new RuntimeException();
        this.saveName = saveName;
        this.party = new Party();
    }

    public static GameData loadGame(String name) {
        if(!SaveGame.exists(name))
            throw new RuntimeException();
        System.out.println("Loading saves/"+name+".json");
        FileHandle fileHandle = Gdx.files.local("saves/"+name+".json");
        return json.fromJson(GameData.class, fileHandle.readString());
    }

    public void saveGame() {
        party.getSelected().clear();
        FileHandle fileHandle = Gdx.files.local("saves/"+saveName+".json");
        fileHandle.writeString(json.toJson(this), false);
    }

    public Party getParty() {
        return party;
    }

    @Override
    public String toString() {
        return saveName;
    }
}
