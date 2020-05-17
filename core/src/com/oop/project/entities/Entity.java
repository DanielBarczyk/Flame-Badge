package com.oop.project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oop.project.Equipment.Item;
import com.oop.project.Equipment.Weapon;
import com.oop.project.battles.Ranges;
import com.oop.project.map.GameMap;
import com.oop.project.screens.FightScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Entity {

    Vector2 pos;
    private EntityType type;
    GameMap map;
    HashMap<Stats,Integer> unitStats;
    HashMap<Stats,Integer> growths;
    private ArrayList<Item> inventory;
    int currentHp;
    Texture image;
    private Ranges range;
    Weapon currentlyEquipped;
    private String longname;
    private String shortname;

    private ArrayList<Button> inventoryButtons;

    public Entity(String longname, String shortname, int x,int y, EntityType type, GameMap map,Ranges range) {
        this.longname = longname;
        this.shortname = shortname;
        this.pos = new Vector2(x,y);
        this.type = type;
        this.map = map;
        this.range = range;
        this.inventory = new ArrayList<>();
        this.inventoryButtons = new ArrayList<>();
    }

    public Entity(String longname, String shortname, EntityType type, Ranges range) {
        this.longname = longname;
        this.shortname = shortname;
        this.type = type;
        this.range = range;
        this.inventory = new ArrayList<>();
        this.inventoryButtons = new ArrayList<>();
    }

    public void update(float delta){

    }

    boolean moveUp(){//need to add actual map boundaries, getting them from the loaded map somehow
        if(pos.y<4&&map.isTileEmpty((int)pos.x,(int)pos.y+1)) {
            pos.y += 1;
            return true;
        }
        return false;
    }

    boolean moveDown(){
        if(pos.y>0&&map.isTileEmpty((int)pos.x,(int)pos.y-1)) {
            pos.y -= 1;
            return true;
        }
        return false;
    }

    boolean moveRight(){//need to add actual map boundaries, getting them from the loaded map somehow
        if(pos.x<9&&map.isTileEmpty((int)pos.x+1,(int)pos.y)) {
            pos.x += 1;
            return true;
        }
        return false;
    }

    boolean moveLeft(){
        if(pos.x>0&&map.isTileEmpty((int)pos.x-1,(int)pos.y)) {
            pos.x -= 1;
            return true;
        }
        return false;
    }

    public abstract void render(SpriteBatch batch);

    public Vector2 getPos() {
        return pos;
    }

    int getWidth(){
        return type.getWidth();
    }

    int getHeight(){
        return type.getHeight();
    }

    public static HashMap<Stats,Integer> setStats(int... bases){
        HashMap<Stats,Integer> hashMap=new HashMap<>();
        for(int i=0;i<Stats.values().length;i++){
            hashMap.put(Stats.values()[i],bases[i]);
        }
        return hashMap;
    }

    public static HashMap<Stats,Integer> setStats(String... bases){
        HashMap<Stats,Integer> hashMap=new HashMap<>();
        for(int i=0;i<Stats.values().length;i++){
            hashMap.put(Stats.values()[i], Integer.parseInt(bases[i]));
        }
        return hashMap;
    }




    public void takeDamage(int damage) {
        System.out.println(currentHp+ " "+damage);
        if(damage>0)
        currentHp -= damage;

    }

    public int getCurrentHp(){
        return currentHp;
    }

    public Ranges getRange() {
        return range;
    }

    public HashMap<Stats, Integer> getUnitStats() {
        return unitStats;
    }

    public EntityType getType() {
        return type;
    }

    public int getMove(){
        return unitStats.get(Stats.MOV);
    }

    public int getDistance(Entity b){
        return (int)Math.abs(b.pos.x-pos.x)+(int)Math.abs(b.pos.y-pos.y);
    }

    public String getShortname() {return this.shortname;}

    public String getLongname() {return this.longname;}

    public void moveTowards(Entity b){
        while(getDistance(b)>range.getMax()){
            if(pos.x>b.pos.x&&map.isTileEmpty((int)pos.x-1,(int)pos.y)) {
                moveLeft();
            } else if(pos.x<b.pos.x&&map.isTileEmpty((int)pos.x+1,(int)pos.y)){
                moveRight();
            } else if(pos.y>b.pos.y&&map.isTileEmpty((int)pos.x,(int)pos.y-1)){
                moveDown();
            } else if(pos.y<b.pos.y&&map.isTileEmpty((int)pos.x,(int)pos.y+1)){
                moveUp();
            }
        }
    }

    public void printStats(){
        for(int i=0;i<Stats.values().length;i++){
            System.out.println(Stats.values()[i].getId()+": "+unitStats.get(Stats.values()[i]));
        }
    }

    private String statsString(){
        String result="Current hp: "+currentHp+"\n";
        for(int i=0;i<Stats.values().length;i++){
            result=result.concat(Stats.values()[i].getId()+": "+unitStats.get(Stats.values()[i])+"\n");
        }
        result=result.substring(0,result.length()-1);
        return result;
    }

    private static String statsExplanationString(){
        return "HP: If it goes below or equal to zero, the unit dies\n"
                + "ATK: How much damage an unit deals without a weapon\n" +
                "SPD: How fast an unit is - if faster than enemy, unit attacks twice during combat\n" +
                "SKILL: How accurate an unit is - a point of skill is equal to 2% extra hit chance \n" +
                "LUCK: Improves the unit's chance to crit, as well as crit avoid\n" +
                "DEF: Defense against physical attacks - reduces damage taken from melee enemies\n"+
                "RES: Resistance against magical attacks - reduces damage taken from magical enemies\n" +
                "MOV: How far an unit can move in a single turn\n" +
                "EXP: How close the unit is to levelling up\n"+
                "LVL: The unit's level - determines how much exp the unit gets from battle";
    }

    public void printStatsButton(Stage stage, Skin skin){
        final TextButton statsButton = new TextButton(statsString(), skin);
        if(this instanceof PlayableCharacter){
            statsButton.setPosition((float) Gdx.graphics.getWidth()/3 - statsButton.getWidth()/2,
                (float)Gdx.graphics.getHeight()/2 + statsButton.getHeight()/2 + 10);
        }
        else{
            statsButton.setPosition(2*(float) Gdx.graphics.getWidth()/3 - statsButton.getWidth()/2,
                    (float)Gdx.graphics.getHeight()/2 + statsButton.getHeight()/2 + 10);
        }

        stage.addActor(statsButton);
        statsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statsButton.remove();
            }
        });
    }

    public static void printStatsExplanationButton(Stage stage, Skin skin){
        final TextButton statsButton = new TextButton(statsExplanationString(), skin);
        statsButton.setPosition((float)Gdx.graphics.getWidth()/2 - statsButton.getWidth()/2,
                3*(float)Gdx.graphics.getHeight()/4 - statsButton.getHeight()-20);
        stage.addActor(statsButton);
        statsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statsButton.remove();
            }
        });
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public ArrayList<Button> getInventoryButtons(){
        return inventoryButtons;
    }

    void addItem(Item item){
        inventory.add(item);
    }

    public Weapon getCurrentlyEquipped(){
        return currentlyEquipped;
    }

    public void setCurrentlyEquipped(Weapon w) {
        currentlyEquipped=w;
    }
}
