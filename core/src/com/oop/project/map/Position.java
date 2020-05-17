package com.oop.project.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

public class Position {
    Vector2 pos;
    GameMap map;

    public Position(int x, int y, GameMap map){
        pos=new Vector2(x,y);
        this.map=map;
    }

    public Vector2 getPos() {
        return pos;
    }

    public GameMap getMap() {
        return map;
    }
}
