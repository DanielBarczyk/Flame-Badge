package com.oop.project.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.project.battles.Combat;
import com.oop.project.battles.Ranges;
import com.oop.project.entities.EnemyCharacter;
import com.oop.project.entities.Entity;
import com.oop.project.entities.PlayableCharacter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class GameMap {
    private ArrayList<PlayableCharacter> playableCharacters;
    private ArrayList<EnemyCharacter> enemyCharacters;

    public PlayableCharacter activeCharacter;

    GameMap() {
        playableCharacters = new ArrayList<>();
        enemyCharacters = new ArrayList<>();
        addPlayableCharacters();
        addEnemyCharacters();
        activeCharacter = playableCharacters.get(0);
    }

    private void addPlayableCharacters(){
        playableCharacters.add(PlayableCharacter.makeSwordLord(this));
        playableCharacters.add(PlayableCharacter.makeArcher(this));
        playableCharacters.add(PlayableCharacter.makeMage(this));
    }

    private void addEnemyCharacters(){
        enemyCharacters.add(EnemyCharacter.makeFirstBrigand(this));
        enemyCharacters.add(EnemyCharacter.makeSecondBrigand(this));
        enemyCharacters.add(EnemyCharacter.makeMage(this));
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
        if(activeCharacter!=null) {
            activeCharacter.update(delta);
            if (!activeCharacter.isActive()) {
                activeCharacter = nextPlayableCharacter();
                //if(activeCharacter==null)
                //endTurn();
            }
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

    public boolean isTileOccupiedByEnemy(int x,int y){
        Vector2 targetTile=new Vector2(x,y);
        for (Entity entity: enemyCharacters){
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


    public void endTurn(){
        enemyPhase();
        try{
            TimeUnit.MILLISECONDS.sleep(700);
        } catch (Exception e){
            e.printStackTrace();
        }
        for(PlayableCharacter entity: playableCharacters){
            entity.makeActive();
        }
        activeCharacter=nextPlayableCharacter();
    }

    private void enemyPhase(){
        ArrayList<EnemyCharacter> hasDied=new ArrayList<>();
        for(EnemyCharacter enemy:enemyCharacters){
            moveEnemy(enemy);
            if(enemy.getCurrentHp()<=0)
                hasDied.add(enemy);
        }
        for(EnemyCharacter enemy:hasDied){
            enemyCharacters.remove(enemy);
        }
    }

    public void kill(PlayableCharacter pc){
        playableCharacters.remove(pc);
    }

    public void kill(EnemyCharacter ec){
        enemyCharacters.remove(ec);
    }

    public ArrayList<EnemyCharacter> getEnemyCharacters(){
        return enemyCharacters;
    }

    private void moveEnemy(EnemyCharacter enemyCharacter){
        PlayableCharacter target=null;
        for (PlayableCharacter p:playableCharacters
             ) {
            if(enemyCharacter.getDistance(p)<=enemyCharacter.getMove()+enemyCharacter.getRange().getMax()){
                target=p;
                enemyCharacter.moveTowards(p);
                Combat.battle(enemyCharacter,p);
                break;
            }
        }
        if(target!=null) {
            if (target.getCurrentHp() <= 0) {
                playableCharacters.remove(target);
            }
        }
    }
}
