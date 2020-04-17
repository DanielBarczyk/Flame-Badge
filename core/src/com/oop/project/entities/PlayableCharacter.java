package com.oop.project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.project.battles.Combat;
import com.oop.project.battles.Ranges;
import com.oop.project.map.GameMap;
import com.oop.project.map.TileType;

import java.awt.*;
import java.util.HashMap;

public class PlayableCharacter extends Entity {

    private Texture actedImage;
    private boolean active;
    private int moveLeft;

    public PlayableCharacter(int x, int y, GameMap map, Ranges range, String pathToPng, String pathToActedImage, HashMap<Stats, Integer> unitStats, HashMap<Stats, Integer> growths) {
        super(x, y, EntityType.PLAYER_UNIT, map,range);
        this.unitStats=unitStats;
        this.growths=growths;
        moveLeft=unitStats.get(Stats.MOV);
        currentHp=unitStats.get(Stats.HP);
        image = new Texture(pathToPng);
        actedImage = new Texture(pathToActedImage);
        active=true;
    }



    @Override
    public void render(SpriteBatch batch) {
        if(active)
        batch.draw(image,pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE,getWidth(),getHeight());
        else
        batch.draw(actedImage,pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE);
    }


    @Override
    public void update(float delta) {
        if(active) {
            if(moveLeft>0){
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                    if (super.moveUp()) {
                        moveLeft--;
                    }
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    if (super.moveRight()) {
                        moveLeft--;
                    }
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                    if (super.moveDown()) {
                        moveLeft--;
                    }
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    if (super.moveLeft()) {
                        moveLeft--;
                    }
                }
            }
            else{
                if(noEnemiesInRange()) active=false;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.E))
                active=false;
        }
    }

    public boolean isActive(){
        return active;
    }

    public void makeActive(){
        active=true;
        moveLeft=unitStats.get(Stats.MOV);
    }
    public void makeInactive(){
        active=false;
        moveLeft=0;
    }

    private boolean noEnemiesInRange(){
        System.out.println("C");
        for (EnemyCharacter e:map.getEnemyCharacters()
        ) {
            if(Combat.canTheyFight(map.activeCharacter,e)) return false;
        }
        System.out.println("D");
        return true;
    }

}
