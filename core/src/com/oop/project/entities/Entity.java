package com.oop.project.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.project.map.GameMap;

import java.util.HashMap;

public abstract class Entity {

    Vector2 pos;
    private EntityType type;
    private GameMap map;
    HashMap<Stats,Integer> unitStats;
    HashMap<Stats,Integer> growths;
    Texture image;

    Entity(int x,int y, EntityType type, GameMap map) {
        this.pos = new Vector2(x,y);
        this.type = type;
        this.map = map;
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

    public EntityType getType() {
        return type;
    }


}
