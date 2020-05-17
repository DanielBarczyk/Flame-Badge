package com.oop.project.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.oop.project.battles.Ranges;
import com.oop.project.entities.PlayableCharacter;
import com.oop.project.entities.Stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class Party {
    private ArrayList<PlayableCharacter> characters;
    private ArrayList<PlayableCharacter> selected;

    public Party() {
        this.characters = new ArrayList<>();
        this.selected = new ArrayList<>();
        createDefaultCharacters();
    }

    public void createDefaultCharacters() {
        characters.add(PlayableCharacter.makeArcher());
        characters.add(PlayableCharacter.makeSwordLord());
        characters.add(PlayableCharacter.makeMage());
    }

    public void selectDefaultCharacters(){
        for(PlayableCharacter pc: characters)
            selected.add(pc);
    }

    public ArrayList<PlayableCharacter> getCharacters() {
        return characters;
    }

    public ArrayList<PlayableCharacter> getSelected(){ return selected;}


}
