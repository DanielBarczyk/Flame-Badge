package com.oop.project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.project.map.GameMap;
import com.oop.project.map.TileType;

public class PlayableCharacter extends Entity {

    Texture image;

    public PlayableCharacter(int x, int y, GameMap map) {
        super(x, y, EntityType.CHARACTER, map);
        image = new Texture("testchar.png");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image,pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE,getWidth(),getHeight());
    }

    @Override
    public void update(float delta) {
        System.out.println("D");
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            super.moveUp();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            System.out.println("D");
            super.moveRight();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            super.moveDown();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            super.moveLeft();
        }
    }
}
