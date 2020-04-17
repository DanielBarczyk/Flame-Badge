package com.oop.project.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.project.entities.Entity;
import com.oop.project.entities.PlayableCharacter;

import java.util.ArrayList;

public abstract class GameMap {

    public ArrayList<Entity> entities;

    public GameMap(){
        entities = new ArrayList<>();
        entities.add(new PlayableCharacter(1,2,this));
    }
    public void render(OrthographicCamera camera, SpriteBatch spriteBatch){
        for(Entity entity: entities){
            entity.render(spriteBatch);
        }
    }

    public void udpate(float delta){
        System.out.println("D");
        for(Entity entity: entities){
            entity.update(delta);
        }
    }

    public abstract void dispose();

    public abstract TileType getTileTypeByCoordinate(int layer,int col, int row);


    public TileType getTileTypeByLocation(int layer,float x, float y){
        return this.getTileTypeByCoordinate(layer,(int)x/TileType.TILE_SIZE,(int)y/TileType.TILE_SIZE);
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();
}
