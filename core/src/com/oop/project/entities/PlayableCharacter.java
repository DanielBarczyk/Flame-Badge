package com.oop.project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.project.Equipment.Weapon;
import com.oop.project.battles.Combat;
import com.oop.project.battles.Ranges;
import com.oop.project.map.GameMap;
import com.oop.project.map.Position;
import com.oop.project.map.TileType;

import java.util.HashMap;

public class PlayableCharacter extends Entity {
    private boolean active;
    private int moveLeft;

    public PlayableCharacter() {
        super();
    }

    private PlayableCharacter(String longname, String shortname, int x, int y, GameMap map, Ranges range, HashMap<Stats, Integer> unitStats, HashMap<Stats, Integer> growths) {
        super(longname, shortname, x, y, EntityType.PLAYER_UNIT, map,range);
        this.unitStats = unitStats;
        this.growths = growths;
        moveLeft = unitStats.get(Stats.MOV);
        currentHp = unitStats.get(Stats.MAXHP);
        active = true;
    }

    public PlayableCharacter(String longname, String shortname, Ranges range, HashMap<Stats, Integer> unitStats, HashMap<Stats, Integer> growths) {
        super(longname, shortname, EntityType.PLAYER_UNIT, range);
        this.unitStats = unitStats;
        this.growths = growths;
    }



    @Override
    public void render(SpriteBatch batch) {
        if(active)
        batch.draw(new Texture(this.getShortname()+"/default.png"),pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE,getWidth(),getHeight());
        else
        batch.draw(new Texture(this.getShortname()+"/acted.png"),pos.x* TileType.TILE_SIZE,pos.y*TileType.TILE_SIZE);
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

    private boolean noEnemiesInRange() {
        for(EnemyCharacter e:map.getEnemyCharacters()) {
            if(Combat.canTheyFight(map.activeCharacter,e)) return false;
        }
        return true;
    }

    private void levelUp() {
        System.out.println("Level Up; Gained:");
        for(int i=0;i<Stats.values().length;i++) {
            if(growths.get(Stats.values()[i])>(int)(Math.random()*100)){
                updateStat(Stats.values()[i],1);
                System.out.println(1+" "+Stats.values()[i]);
            }
        }
        updateStat(Stats.LVL,1);
    }

    public void gainExp(Entity enemy){
        int value=31-unitStats.get(Stats.LVL);
        if(enemy.getCurrentHp()<0){
            value+=20+(Math.max(0,enemy.getUnitStats().get(Stats.LVL)-unitStats.get(Stats.LVL))*5);
        }
        value=Math.min(value,99);
        Integer currentExp=unitStats.get(Stats.EXP);
        unitStats.remove(Stats.EXP);
        currentExp+=value;
        if(currentExp>=100){
            levelUp();
            currentExp-=100;
        }
        unitStats.put(Stats.EXP,currentExp);
    }
    private void updateStat(Stats stat,int value){
        Integer a=unitStats.get(stat);
        unitStats.remove(stat);
        a+=value;
        unitStats.put(stat,a);
    }

    public static PlayableCharacter makeSwordLord(){
        PlayableCharacter swordLord=new PlayableCharacter("SwordLord", "TCA", Ranges.MELEE,PlayableCharacter.setStats(15,5,6,7,8,5,3,3,0,2),PlayableCharacter.setStats(70,40,60,60,55,20,30,0,0,0));
        swordLord.addItem(new Weapon("Iron Sword",200,3,70,0));
        swordLord.addItem(new Weapon("Rapier",400,3,70,10));
        swordLord.currentlyEquipped=(Weapon)swordLord.getInventory().get(0);
        return swordLord;
    }

    public static PlayableCharacter makeArcher(){
        PlayableCharacter archer= new PlayableCharacter("Archer", "TCB", Ranges.BOWS, PlayableCharacter.setStats(12,3,8,6,3,3,7,3,80,2),PlayableCharacter.setStats(35,45,55,50,25,20,30,0,0,0));
        archer.addItem(new Weapon("Iron Bow",200,3,70,0));
        archer.addItem(new Weapon("Killer Bow",200,5,50,30));
        archer.currentlyEquipped=(Weapon)archer.getInventory().get(0);
        return archer;
    }

    public static PlayableCharacter makeMage(){
        PlayableCharacter mage=new PlayableCharacter("Mage", "TCC", Ranges.MAGIC, PlayableCharacter.setStats(8,5,4,5,4,1,4,3,80,1),PlayableCharacter.setStats(45,75,35,20,50,30,10,0,0,0));
        mage.addItem(new Weapon("Fire",200,4,70,0));
        mage.addItem(new Weapon("Thunder",200,5,70,15));
        mage.currentlyEquipped=(Weapon)mage.getInventory().get(0);
        return mage;
    }

    public static PlayableCharacter makeLanceCav(){
        PlayableCharacter lanceCav=new PlayableCharacter("LanceCav", "TCD", Ranges.MELEE, PlayableCharacter.setStats(11,7,5,6,7,5,4,5,20,3),PlayableCharacter.setStats(55,55,40,30,30,40,30,0,0,0));
        lanceCav.addItem(new Weapon("Iron Lance", 300, 5,75,0));
        lanceCav.addItem(new Weapon("Killer Lance", 300, 4,55,30));
        lanceCav.currentlyEquipped=(Weapon)lanceCav.getInventory().get(0);
        return lanceCav;
    }

    public PlayableCharacter setPos(Position pos){
        this.pos=pos.getPos();
        this.map=pos.getMap();
        this.active=true;
        moveLeft = unitStats.get(Stats.MOV);
        currentHp = unitStats.get(Stats.MAXHP);
        return this;
    }
}
