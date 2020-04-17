package com.oop.project.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.project.entities.EnemyCharacter;
import com.oop.project.entities.Entity;
import com.oop.project.entities.PlayableCharacter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public abstract class GameMap {

    public ArrayList<PlayableCharacter> playableCharacters;
    public ArrayList<EnemyCharacter> enemyCharacters;

    public PlayableCharacter activeCharacter;

    public GameMap(){
        playableCharacters = new ArrayList<>();
        enemyCharacters = new ArrayList<>();
        playableCharacters.add(new PlayableCharacter(1,2,this,"testchar.png"));
        playableCharacters.add(new PlayableCharacter(0,3,this,"testchar2.png"));
        enemyCharacters.add(new EnemyCharacter(7,3,this,"testenemy.png"));
        enemyCharacters.add(new EnemyCharacter(6,2,this,"testenemy.png"));
        activeCharacter=playableCharacters.get(0);
    }
    public void render(OrthographicCamera camera, SpriteBatch spriteBatch){
        for(Entity entity: playableCharacters){
            entity.render(spriteBatch);
        }
        for(Entity entity: enemyCharacters){
            entity.render(spriteBatch);
        }
    }

    public void udpate(float delta){
        for(Entity entity: playableCharacters){
            if(entity==activeCharacter)
            entity.update(delta);
        }
    }

    public abstract void dispose();

    public abstract TileType getTileTypeByCoordinate(int layer,int col, int row);


    public TileType getTileTypeByLocation(int layer,float x, float y){
        return this.getTileTypeByCoordinate(layer,(int)x/TileType.TILE_SIZE,(int)y/TileType.TILE_SIZE);
    }

    public boolean isTileOccupied(int x,int y){
        Vector2 targetTile=new Vector2(x,y);
        for (Entity entity: playableCharacters){
            if(entity.getPos().equals(targetTile)) return true;
        }
        for (Entity entity: enemyCharacters){
            if(entity.getPos().equals(targetTile)) return true;
        }
        return false;
    }
    public boolean isTileOccupiedByPlayable(int x,int y){
        Vector2 targetTile=new Vector2(x,y);
        for (Entity entity: playableCharacters){
            if(entity.getPos().equals(targetTile)) return true;
        }
        return false;
    }
    public PlayableCharacter playableOnTile(int x,int y){
        Vector2 targetTile=new Vector2(x,y);
        for (PlayableCharacter entity: playableCharacters){
            if(entity.getPos().equals(targetTile)) return entity;
        }
        return null;
    }
    public EnemyCharacter enemyOnTile(int x,int y){
        Vector2 targetTile=new Vector2(x,y);
        for (EnemyCharacter entity: enemyCharacters){
            if(entity.getPos().equals(targetTile)) return entity;
        }
        return null;
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();
}
