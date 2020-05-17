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

    private EnemyCharacter(String longname, String shortname, Ranges range, HashMap<Stats, Integer> unitStats) {
        super(longname, shortname, EntityType.ENEMY_UNIT, range);
        this.unitStats=unitStats;
        currentHp=unitStats.get(Stats.MAXHP);
        image = new Texture(this.getShortname()+"/default.png");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image,pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE,getWidth(),getHeight());
    }

    @Override
    public void update(float delta) { }


    public static EnemyCharacter makeFirstBrigand(){
        EnemyCharacter firstBrigand=new EnemyCharacter("FirstBrigand", "TEA", Ranges.MELEE,EnemyCharacter.setStats(8,6,4,2,3,1,0,3,0,1));
        firstBrigand.addItem(new Weapon("Iron Axe",200,5,55,0));
        firstBrigand.currentlyEquipped=(Weapon)firstBrigand.getInventory().get(0);
        return firstBrigand;
    }
    public static EnemyCharacter makeSecondBrigand(){
        EnemyCharacter secondBrigand=new EnemyCharacter("SecondBrigand", "TEA",Ranges.MELEE,EnemyCharacter.setStats(9,7,4,2,3,1,0,3,0,1));
        secondBrigand.addItem(new Weapon("Iron Axe",200,5,55,0));
        secondBrigand.currentlyEquipped=(Weapon)secondBrigand.getInventory().get(0);
        return secondBrigand;
    }
    public static EnemyCharacter makeMage(){
        EnemyCharacter mage=new EnemyCharacter("EnemyMage", "TEB", Ranges.MAGIC,EnemyCharacter.setStats(10,7,5,3,1,1,0,1,0,1));
        mage.addItem(new Weapon("Fire",200,4,70,0));
        mage.currentlyEquipped=(Weapon)mage.getInventory().get(0);
        return mage;
    }

    public EnemyCharacter setPos(int x, int y,GameMap map){
        this.pos.x=x;
        this.pos.y=y;
        this.map=map;
        return this;
    }
}

