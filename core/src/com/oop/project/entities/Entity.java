package com.oop.project.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.project.map.GameMap;

public abstract class Entity {

    protected Vector2 pos;
    protected EntityType type;
    protected GameMap map;

    public Entity(int x,int y, EntityType type, GameMap map) {
        this.pos = new Vector2(x,y);
        this.type = type;
        this.map = map;
    }

    public void update(float delta){

    }

    protected void moveUp(){//need to add actual map boundaries, getting them from the loaded map somehow
        if(pos.y<4&&!map.isTileOccupied((int)pos.x,(int)pos.y+1)) {
            pos.y += 1;
        }
    }

    protected void moveDown(){
        if(pos.y>0&&!map.isTileOccupied((int)pos.x,(int)pos.y-1)) {
            pos.y -= 1;
        }
    }

    protected void moveRight(){//need to add actual map boundaries, getting them from the loaded map somehow
        if(pos.x<9&&!map.isTileOccupied((int)pos.x+1,(int)pos.y)) {
            pos.x += 1;
        }
    }

    protected void moveLeft(){
        if(pos.x>0&&!map.isTileOccupied((int)pos.x-1,(int)pos.y)) {
            pos.x -= 1;
        }
    }

    public abstract void render(SpriteBatch batch);

    public Vector2 getPos() {
        return pos;
    }

    public int getWidth(){
        return type.getWidth();
    }

    public int getHeight(){
        return type.getHeight();
    }


    public EntityType getType() {
        return type;
    }


}
