package com.oop.project.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.oop.project.entities.PlayableCharacter;
import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class GameData {
    private String saveName;
    private final Party party;
    private int currentLevel;
    // The JSON library requires a default constructor, so this method is necessary.
    public GameData() {
        party = new Party();
    }

    public GameData(String saveName) {
        if(SaveGame.exists(saveName))
            throw new RuntimeException();
        this.saveName = saveName;
        this.party = new Party();
        currentLevel=1;
    }

    public static GameData loadGame(String name) throws InvalidSaveException {
        try {
            FileHandle fileHandle = Gdx.files.local("saves/"+name+".json");
            return json.fromJson(GameData.class, fileHandle.readString());
        } catch (Exception e) {
            throw new InvalidSaveException();
        }

    }

    public void saveGame() {
        for(PlayableCharacter character : party.getCharacters())
            character.fixDesync();
        party.getSelected().clear();
        FileHandle fileHandle = Gdx.files.local("saves/"+saveName+".json");
        fileHandle.writeString(json.toJson(this), false);
        SaveGame.getSaveGames().put(saveName, this);
        SaveGame.saveToJSON();
    }

    public Party getParty() {
        return party;
    }

    @Override
    public String toString() {
        return saveName;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void incrementCurrentLevel() {
        currentLevel++;
    }

    public static class InvalidSaveException extends Exception {
        @Override
        public String getMessage() {
            return "The save could not be loaded properly.";
        }
    }


}
