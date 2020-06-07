package com.oop.project.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.project.FlameBadge;
import com.oop.project.battles.Combat;
import com.oop.project.entities.EnemyCharacter;
import com.oop.project.entities.Entity;
import com.oop.project.entities.PlayableCharacter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class GameMap {
    ArrayList<PlayableCharacter> playableCharacters;
    private ArrayList<EnemyCharacter> enemyCharacters;

    public PlayableCharacter activeCharacter;
    FlameBadge game;
    int mapMaxX;
    int mapMaxY;

    GameMap() {
        playableCharacters = new ArrayList<>();
        enemyCharacters = new ArrayList<>();
    }

    void addPlayableCharactersMap1(){
        if(game.currentGame.getParty().getSelected()==null||game.currentGame.getParty().getSelected().size()==0)
            game.currentGame.getParty().selectDefaultCharacters();
        loadPlayableCharacters(game.currentGame.getParty().getSelected(), getMap1Positions());
    }

    void addEnemyCharactersMap1(){
        enemyCharacters.add(EnemyCharacter.makeFirstBrigand().setPos(7,2,this));
        enemyCharacters.add(EnemyCharacter.makeSecondBrigand().setPos(7,4,this));
        enemyCharacters.add(EnemyCharacter.makeMage().setPos(9,3,this));
    }

    void addEnemyCharactersMap2(){
        enemyCharacters.add(EnemyCharacter.makeFirstBrigand().setPos(10,2,this));
        enemyCharacters.add(EnemyCharacter.makeSecondBrigand().setPos(8,4,this));
        enemyCharacters.add(EnemyCharacter.makeMage().setPos(7,3,this));
        enemyCharacters.add(EnemyCharacter.makeFirstBrigand().setPos( 10,8,this));
        enemyCharacters.add(EnemyCharacter.makeSecondBrigand().setPos(10,6,this));
        enemyCharacters.add(EnemyCharacter.makeMage().setPos(3,6,this));
        enemyCharacters.add(EnemyCharacter.makeFirstBrigand().setPos(3,4,this));
        enemyCharacters.add(EnemyCharacter.makeSecondBrigand().setPos(4, 4,this));
        enemyCharacters.add(EnemyCharacter.makeMage().setPos(12,3,this));
        enemyCharacters.add(EnemyCharacter.makeSecondBrigand().setPos(2,8,this));
    }

    private void loadPlayableCharacters(ArrayList<PlayableCharacter> lista,ArrayList<Position> positions){
        for(int i=0;i<lista.size();i++){
            playableCharacters.add(lista.get(i).setPos(positions.get(i)));
        }
    }

    private void defaultSelection(){
        playableCharacters.add(PlayableCharacter.makeSwordLord().setPos(new Position(1,2,this)));
        playableCharacters.add(PlayableCharacter.makeArcher().setPos(new Position(0,3,this)));
        playableCharacters.add(PlayableCharacter.makeMage().setPos(new Position(0,0,this)));
    }

    private ArrayList<Position> getMap1Positions(){
        ArrayList<Position> result=new ArrayList<>();
        result.add(new Position(1,2,this));
        result.add(new Position(0,3,this));
        result.add(new Position(0,0,this));
        result.add(new Position(2,1,this));
        return result;
    }

    void addPlayableCharactersMap2(){
        if(game.currentGame.getParty().getCharacters().size()==3) {
            game.currentGame.getParty().getCharacters().add(PlayableCharacter.makeLanceCav());
            game.currentGame.getParty().getSelected().add(game.currentGame.getParty().getCharacters().get(3));
        }
        if(game.currentGame.getParty().getSelected()==null||game.currentGame.getParty().getSelected().size()==0)
            game.currentGame.getParty().selectDefaultCharacters();
        loadPlayableCharacters(game.currentGame.getParty().getSelected(), getMap2Positions());
    }

    private ArrayList<Position> getMap2Positions(){
        ArrayList<Position> result=new ArrayList<>();
        result.add(new Position(2,3,this));
        result.add(new Position(3,2,this));
        result.add(new Position(1,1,this));
        result.add(new Position(2,2,this));
        return result;
    }

    public void render(OrthographicCamera camera, SpriteBatch spriteBatch){
        for(Entity entity: playableCharacters){
            entity.render(spriteBatch);
        }
        for(Entity entity: enemyCharacters){
            entity.render(spriteBatch);
        }
        if(activeCharacter != null)
            spriteBatch.draw(new Texture("charframe.png"), activeCharacter.getPos().x * TileType.TILE_SIZE, activeCharacter.getPos().y * TileType.TILE_SIZE);
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

    public boolean isTileTraversible(int x,int y){
        return getTileTypeByCoordinate(0,x,y).isTraversible();
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
        if(enemyCharacters.size()==0){
            endMap();
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
        int[][] bfsResult= enemyCharacter.bfs();
        /*for(int i=0;i<bfsResult.length;i++){
            for(int j=0;j<bfsResult[i].length;j++){
                System.out.print(bfsResult[i][j]);
            }
            System.out.println();
        }
        System.out.println();*/

        for (PlayableCharacter p:playableCharacters
             ) {
            if((enemyCharacter.getDistance(p)==enemyCharacter.getRange().getMax())||bfsResult[(int)p.getPos().x][(int)p.getPos().y]<=enemyCharacter.getMove()+enemyCharacter.getRange().getMax()){
                target=p;
                enemyCharacter.moveTowards(bfsResult,p);
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


    public int getMapMaxX(){
        return mapMaxX;
    }

    public int getMapMaxY(){
        return mapMaxY;
    }

    private void endMap(){
        game.gameMap = new TiledGameMap(game,2);
    }
}
