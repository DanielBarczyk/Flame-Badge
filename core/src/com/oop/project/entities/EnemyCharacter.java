package com.oop.project.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.project.Equipment.Weapon;
import com.oop.project.battles.Ranges;
import com.oop.project.map.GameMap;
import com.oop.project.map.TileType;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class EnemyCharacter extends Entity {

    private Texture image;

    private EnemyCharacter(String longname, String shortname, Ranges range, HashMap<Stats, Integer> unitStats) {
        super(longname, shortname, EntityType.ENEMY_UNIT, range);
        this.unitStats=unitStats;
        currentHp=unitStats.get(Stats.MAXHP);
        image = new Texture("idle/"+this.getShortname()+".png");
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
    public static EnemyCharacter makeThirdBrigand(){
        EnemyCharacter thirdBrigand=new EnemyCharacter("ThirdBrigand", "TEA", Ranges.MELEE,EnemyCharacter.setStats(10,8,6,4,5,3,2,3,0,1));
        thirdBrigand.addItem(new Weapon("Iron Axe",200,5,55,0));
        thirdBrigand.currentlyEquipped=(Weapon)thirdBrigand.getInventory().get(0);
        return thirdBrigand;
    }

    public static EnemyCharacter makeMage(){
        EnemyCharacter mage=new EnemyCharacter("EnemyMage", "TEB", Ranges.MAGIC,EnemyCharacter.setStats(10,7,5,3,1,1,0,2,0,1));
        mage.addItem(new Weapon("Fire",200,4,70,0));
        mage.currentlyEquipped=(Weapon)mage.getInventory().get(0);
        return mage;
    }

    public static EnemyCharacter makeSecondMage(){
        EnemyCharacter mage=new EnemyCharacter("EnemyMage", "TEB", Ranges.MAGIC,EnemyCharacter.setStats(12,9,7,5,3,3,2,2,0,1));
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

    public int[][] bfs(){
        int[][] result=new int[map.getMapMaxX()+1][map.getMapMaxY()+1];
        int maxvalue=getMove()+getRange().getMax()+1;
        for(int[] array:result){
            Arrays.fill(array,maxvalue);
        }
        result[(int)pos.x][(int)pos.y]=0;
        ArrayBlockingQueue<Vector2> queue=new ArrayBlockingQueue<>((map.getMapMaxX()+1)*(map.getMapMaxY()+1)+1);
        queue.add(pos);
        while(!queue.isEmpty()){
            Vector2 pole=queue.remove();
            int current_distance=result[(int)pole.x][(int)pole.y];
            if(current_distance<getMove()+getRange().getMax()){
                if(current_distance<getMove()){
                    if(pole.x<map.getMapMaxX()&&(map.isTileEmpty((int)pole.x+1,(int)pole.y)||map.isTileOccupiedByPlayable((int)pole.x+1,(int)pole.y))&&map.isTileTraversible((int)pole.x+1,(int)pole.y)&&result[(int)pole.x+1][(int)pole.y]==maxvalue&&(pole.x+1!=pos.x||pole.y!=pos.y)){
                        result[(int)pole.x+1][(int)pole.y]=current_distance+1;
                        queue.add(new Vector2(pole.x+1,pole.y));
                    }

                    if(pole.x>0&&(map.isTileEmpty((int)pole.x-1,(int)pole.y)||map.isTileOccupiedByPlayable((int)pole.x-1,(int)pole.y))&&map.isTileTraversible((int)pole.x-1,(int)pole.y)&&result[(int)pole.x-1][(int)pole.y]==maxvalue&&(pole.x-1!=pos.x||pole.y!=pos.y)){
                        result[(int)pole.x-1][(int)pole.y]=current_distance+1;
                        queue.add(new Vector2(pole.x-1,pole.y));
                    }

                    if(pole.y<map.getMapMaxY()&&(map.isTileEmpty((int)pole.x,(int)pole.y+1)||map.isTileOccupiedByPlayable((int)pole.x,(int)pole.y+1))&&map.isTileTraversible((int)pole.x,(int)pole.y+1)&&result[(int)pole.x][(int)pole.y+1]==maxvalue&&(pole.x!=pos.x||pole.y+1!=pos.y)){
                        result[(int)pole.x][(int)pole.y+1]=current_distance+1;
                        queue.add(new Vector2(pole.x,pole.y+1));
                    }

                    if(pole.y>0&&(map.isTileEmpty((int)pole.x,(int)pole.y-1)||map.isTileOccupiedByPlayable((int)pole.x,(int)pole.y-1))&&map.isTileTraversible((int)pole.x,(int)pole.y-1)&&result[(int)pole.x][(int)pole.y-1]==maxvalue&&(pole.x!=pos.x||pole.y-1!=pos.y)){
                        result[(int)pole.x][(int)pole.y-1]=current_distance+1;
                        queue.add(new Vector2(pole.x,pole.y-1));
                    }
                }
                else{
                    if(pole.x<map.getMapMaxX()&&result[(int)pole.x+1][(int)pole.y]==maxvalue&&(pole.x+1!=pos.x||pole.y!=pos.y)){
                        result[(int)pole.x+1][(int)pole.y]=current_distance+1;
                        queue.add(new Vector2(pole.x+1,pole.y));
                    }

                    if(pole.x>0&&result[(int)pole.x-1][(int)pole.y]==maxvalue&&(pole.x-1!=pos.x||pole.y!=pos.y)){
                        result[(int)pole.x-1][(int)pole.y]=current_distance+1;
                        queue.add(new Vector2(pole.x-1,pole.y));
                    }

                    if(pole.y<map.getMapMaxY()&&result[(int)pole.x][(int)pole.y+1]==maxvalue&&(pole.x!=pos.x||pole.y+1!=pos.y)){
                        result[(int)pole.x][(int)pole.y+1]=current_distance+1;
                        queue.add(new Vector2(pole.x,pole.y+1));
                    }

                    if(pole.y>0&&result[(int)pole.x][(int)pole.y-1]==maxvalue&&(pole.x!=pos.x||pole.y-1!=pos.y)){
                        result[(int)pole.x][(int)pole.y-1]=current_distance+1;
                        queue.add(new Vector2(pole.x,pole.y-1));
                    }
                }
            }
        }
        result[(int)pos.x][(int)pos.y]=0;
        return result;
    }
}

