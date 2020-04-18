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
        playableCharacters.add(new PlayableCharacter(0,1,this, Ranges.MAGIC,"testmage.png","testmageacted.png",PlayableCharacter.setStats(8,5,4,5,4,1,4,3,80),PlayableCharacter.setStats(100,100,100,100,100,100,100,0,0)));
    }

    private void addEnemyCharacters(){
        enemyCharacters.add(new EnemyCharacter(7,3,this, Ranges.MELEE, "testenemy.png",EnemyCharacter.setStats(8,6,4,2,3,1,0,3,0)));
        enemyCharacters.add(new EnemyCharacter(6,2,this, Ranges.MELEE, "testenemy.png",EnemyCharacter.setStats(9,7,4,2,3,1,0,3,0)));
        enemyCharacters.add(new EnemyCharacter(9,3,this, Ranges.MAGIC, "testenemymage.png",EnemyCharacter.setStats(10,7,5,3,1,1,0,1,0)));
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
            try{
                TimeUnit.MILLISECONDS.sleep(700);
            } catch (Exception e){
                e.printStackTrace();
            }
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
