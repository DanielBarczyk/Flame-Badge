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
    private static FileHandle fileHandle;

    static {
        fileHandle = new FileHandle("saves/default_characters.json");
    }

    public Party(String saveName) {
        this.characters = new ArrayList<>();
        createDefaultCharacters();
    }

    public void createDefaultCharacters() {
        characters.add(new PlayableCharacter("Archer", "TCB", Ranges.BOWS, PlayableCharacter.setStats(12,3,8,6,3,3,7,3,80,2),PlayableCharacter.setStats(35,45,55,50,25,20,30,0,0,0)));
        characters.add(new PlayableCharacter("SwordLord", "TCA", Ranges.MELEE,PlayableCharacter.setStats(15,5,6,7,8,5,3,3,0,2),PlayableCharacter.setStats(70,40,60,60,55,20,30,0,0,0)));
        characters.add(new PlayableCharacter("Mage", "TCC", Ranges.MAGIC, PlayableCharacter.setStats(8,5,4,5,4,1,4,3,80,1),PlayableCharacter.setStats(45,75,35,20,50,30,10,0,0,0)));
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


}
