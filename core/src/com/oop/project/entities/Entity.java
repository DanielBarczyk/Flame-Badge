package com.oop.project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oop.project.battles.Ranges;
import com.oop.project.map.GameMap;
import com.oop.project.screens.FightScreen;

import java.util.HashMap;

public abstract class Entity {

    Vector2 pos;
    private EntityType type;
    GameMap map;
    HashMap<Stats,Integer> unitStats;
    HashMap<Stats,Integer> growths;
    int currentHp;
    Texture image;
    private Ranges range;

    Entity(int x,int y, EntityType type, GameMap map,Ranges range) {
        this.pos = new Vector2(x,y);
        this.type = type;
        this.map = map;
        this.range=range;
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
            System.out.println(Stats.values()[i]+": "+unitStats.get(Stats.values()[i]));
        }
    }

    public String statsString(){
        String result="";
        for(int i=0;i<Stats.values().length;i++){
            result=result.concat(Stats.values()[i]+": "+unitStats.get(Stats.values()[i])+"\n");
        }
        return result;
    }

    public void printStatsButton(Stage stage, Skin skin){
        final TextButton statsButton = new TextButton(statsString(), skin);
        statsButton.setPosition((float) Gdx.graphics.getWidth()/2 - statsButton.getWidth()/2,
                (float)Gdx.graphics.getHeight()/2 + statsButton.getHeight()/2 + 10);
        stage.addActor(statsButton);
        statsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statsButton.remove();
            }
        });
    }
}
