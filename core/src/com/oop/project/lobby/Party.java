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
    private HashMap<String, Integer> map;

    public Party() {}

    public Party(String saveName) {
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

    public void createCharacter() {
        String longname;
        String shortname;
        Ranges range;
        HashMap<Stats, Integer> unitStats;
        HashMap<Stats, Integer> growths;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Longname: ");
        longname = scanner.nextLine();
        System.out.println();

        System.out.print("Shortname: ");
        shortname = scanner.nextLine();
        System.out.println();

        System.out.println("HP, ATK, SPD, SKILL, LUCK, DEF, RES, MOV, EXP, LVL");
        unitStats = PlayableCharacter.setStats(scanner.nextLine().split(" "));
        System.out.println();

        System.out.println("HP, ATK, SPD, SKILL, LUCK, DEF, RES, MOV, EXP, LVL");
        growths = PlayableCharacter.setStats(scanner.nextLine().split(" "));
        System.out.println();

        System.out.println("1 - Melee, 2 - Bow, 3 - Magic");
        int a = scanner.nextInt();
        if(a == 1) range = Ranges.MELEE;
        else if(a == 2) range = Ranges.BOWS;
        else range = Ranges.MAGIC;

        characters.add(new PlayableCharacter(longname, shortname, range, unitStats, growths));
    }

    public ArrayList<PlayableCharacter> getCharacters() {
        return characters;
    }

    public ArrayList<PlayableCharacter> getSelected(){ return selected;}


}
