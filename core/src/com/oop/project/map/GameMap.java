package com.oop.project.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.project.battles.Ranges;
import com.oop.project.entities.EnemyCharacter;
import com.oop.project.entities.Entity;
import com.oop.project.entities.PlayableCharacter;

import java.util.ArrayList;

public abstract class GameMap {

    private ArrayList<PlayableCharacter> playableCharacters;
    private ArrayList<EnemyCharacter> enemyCharacters;

    public PlayableCharacter activeCharacter;

    GameMap(){
        playableCharacters = new ArrayList<>();
        enemyCharacters = new ArrayList<>();
        addPlayableCharacters();
        addEnemyCharacters();
        activeCharacter=playableCharacters.get(0);
    }

    private void addPlayableCharacters(){
        playableCharacters.add(new PlayableCharacter(1,2,this, Ranges.MELEE,"testchar.png","testcharacted.png",PlayableCharacter.setStats(15,5,6,7,8,5,3,3,0),PlayableCharacter.setStats(100,100,100,100,100,100,100,0,0)));
        playableCharacters.add(new PlayableCharacter(0,3,this, Ranges.BOWS,"testchar2.png","testchar2acted.png",PlayableCharacter.setStats(12,3,8,6,3,3,7,3,80),PlayableCharacter.setStats(100,100,100,100,100,100,100,0,0)));
    }

    private void addEnemyCharacters(){
        enemyCharacters.add(new EnemyCharacter(7,3,this, Ranges.MELEE, "testenemy.png",EnemyCharacter.setStats(8,3,4,2,1,1,0,3,0)));
        enemyCharacters.add(new EnemyCharacter(6,2,this, Ranges.MELEE, "testenemy.png",EnemyCharacter.setStats(9,3,4,2,1,1,0,3,0)));
    }

    public void render(OrthographicCamera camera, SpriteBatch spriteBatch){
        for(Entity entity: playableCharacters){
            entity.render(spriteBatch);
        }
        for(Entity entity: enemyCharacters){
            entity.render(spriteBatch);
        }
    }

    public void update(float delta){
        activeCharacter.update(delta);
        if(!activeCharacter.isActive()){
            activeCharacter=nextPlayableCharacter();
            if(activeCharacter==null)
                endTurn();
        }
    }

    public abstract void dispose();

    public abstract TileType getTileTypeByCoordinate(int layer,int col, int row);


    public TileType getTileTypeByLocation(int layer,float x, float y){
        return this.getTileTypeByCoordinate(layer,(int)x/TileType.TILE_SIZE,(int)y/TileType.TILE_SIZE);
    }

    public boolean isTileEmpty(int x,int y){
        Vector2 targetTile=new Vector2(x,y);
        for (Entity entity: playableCharacters){
            if(entity.getPos().equals(targetTile)) return false;
        }
        for (Entity entity: enemyCharacters){
            if(entity.getPos().equals(targetTile)) return false;
        }
        return true;
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

    private PlayableCharacter nextPlayableCharacter(){
        for(PlayableCharacter entity: playableCharacters){
            if(entity.isActive()) return entity;
        }
        return null;
    }

    private void endTurn(){
        //enemyPhase() <-AI actions once implemented
        for(PlayableCharacter entity: playableCharacters){
            if(!entity.isActive()) entity.makeActive();
            activeCharacter=nextPlayableCharacter();
        }
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();
}
