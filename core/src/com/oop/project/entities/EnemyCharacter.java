package com.oop.project.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.project.battles.Ranges;
import com.oop.project.map.GameMap;
import com.oop.project.map.TileType;

import java.util.HashMap;

public class EnemyCharacter extends Entity {

    private Texture image;


    public EnemyCharacter(int x, int y, GameMap map, Ranges range, String pathToPng, HashMap<Stats, Integer> unitStats) {
        super(x, y, EntityType.ENEMY_UNIT, map, range);
        this.unitStats=unitStats;
        currentHp=unitStats.get(Stats.HP);
        image = new Texture(pathToPng);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image,pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE,getWidth(),getHeight());
    }

    @Override
    public void update(float delta) { }
}

