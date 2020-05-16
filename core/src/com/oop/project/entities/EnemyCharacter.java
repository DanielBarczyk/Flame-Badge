package com.oop.project.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.project.Equipment.Weapon;
import com.oop.project.battles.Ranges;
import com.oop.project.map.GameMap;
import com.oop.project.map.TileType;

import java.util.HashMap;

public class EnemyCharacter extends Entity {

    private Texture image;


    private EnemyCharacter(int x, int y, GameMap map, Ranges range, String pathToPng, HashMap<Stats, Integer> unitStats) {
        super(x, y, EntityType.ENEMY_UNIT, map, range);
        this.unitStats=unitStats;
        currentHp=unitStats.get(Stats.MAXHP);
        image = new Texture(pathToPng);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image,pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE,getWidth(),getHeight());
    }

    @Override
    public void update(float delta) { }


    public static EnemyCharacter makeFirstBrigand(GameMap map){
        EnemyCharacter firstBrigand=new EnemyCharacter(7,3,map, Ranges.MELEE, "testenemy.png",EnemyCharacter.setStats(8,6,4,2,3,1,0,3,0,1));
        firstBrigand.addItem(new Weapon("Iron Axe",200,5,55,0));
        firstBrigand.currentlyEquipped=(Weapon)firstBrigand.getInventory().get(0);
        return firstBrigand;
    }
    public static EnemyCharacter makeSecondBrigand(GameMap map){
        EnemyCharacter secondBrigand=new EnemyCharacter(6,2,map, Ranges.MELEE, "testenemy.png",EnemyCharacter.setStats(9,7,4,2,3,1,0,3,0,1));
        secondBrigand.addItem(new Weapon("Iron Axe",200,5,55,0));
        secondBrigand.currentlyEquipped=(Weapon)secondBrigand.getInventory().get(0);
        return secondBrigand;
    }
    public static EnemyCharacter makeMage(GameMap map){
        EnemyCharacter mage=new EnemyCharacter(9,3,map, Ranges.MAGIC, "testenemymage.png",EnemyCharacter.setStats(10,7,5,3,1,1,0,1,0,1));
        mage.addItem(new Weapon("Fire",200,4,70,0));
        mage.currentlyEquipped=(Weapon)mage.getInventory().get(0);
        return mage;
    }

}

