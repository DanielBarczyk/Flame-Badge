package com.oop.project.Equipment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oop.project.entities.Entity;

public class Weapon extends Item {
    private int attack;
    private int hit;
    private int crit;

    public Weapon(String name, int cost, int attack, int hit,int crit){
        this.name=name;
        this.cost=cost;
        this.attack=attack;
        this.hit=hit;
        this.crit=crit;
    }

    public int getAttack(){
        return attack;
    }
    public int getHit(){
        return hit;
    }

    public int getCrit(){
        return crit;
    }

    public int getCost(){
        return cost;
    }


    @Override
    public void showItem(Stage stage, Skin skin, int x, int y, final Entity entity){
        final TextButton weaponButton;
        if(this==entity.getCurrentlyEquipped())
            weaponButton = new TextButton("Currently Equipped\n"+weaponStats(), skin);
        else
            weaponButton = new TextButton(weaponStats(), skin);
        weaponButton.setPosition(x,y);
        stage.addActor(weaponButton);
        final Weapon w=this;
        weaponButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                entity.setCurrentlyEquipped(w);
                cleanInventoryButtons(entity);
            }
        });
        entity.getInventoryButtons().add(weaponButton);
    }

    public static void showItems(Stage stage, Skin skin, Entity entity){
        int x1=Gdx.graphics.getWidth()/4,y1=3*Gdx.graphics.getHeight()/4;
        for(Item item: entity.getInventory()){
            item.showItem(stage,skin,x1,y1,entity);
            y1-=80;
        }
    }


    private String weaponStats(){
        return name+"\n"+"Attack: "+getAttack()+" Hit: "+getHit()+" Crit: "+getCrit();
    }

    public static void cleanInventoryButtons(Entity entity){
        for(Button button: entity.getInventoryButtons()){
            button.remove();
        }
        entity.getInventoryButtons().clear();
    }
}
