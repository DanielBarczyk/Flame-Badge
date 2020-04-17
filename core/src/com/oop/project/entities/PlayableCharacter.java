package com.oop.project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.project.map.GameMap;
import com.oop.project.map.TileType;

public class PlayableCharacter extends Entity {

    Texture image;

    private boolean hasacted;

    public PlayableCharacter(int x, int y, GameMap map,String pathToPng) {
        super(x, y, EntityType.PLAYER_UNIT, map);
        image = new Texture(pathToPng);
        hasacted=false;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image,pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE,getWidth(),getHeight());
    }

    @Override
    public void update(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            super.moveUp();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
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
