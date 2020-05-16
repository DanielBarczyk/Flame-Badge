package com.oop.project.Equipment;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.oop.project.entities.Entity;

public class Item {
    String name;
    int cost;
    public String getName(){
        return name;
    }

    public int getCost(){
        return cost;
    }

    public void addItem(Entity entity){
        entity.getInventory().add(this);
    }

    public void showItem(Stage stage, Skin skin,int x, int y,Entity entity){}

    public static void showItems(Stage stage, Skin skin, Entity entity){}
}
