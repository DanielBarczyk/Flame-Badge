package com.oop.project.map;

import java.util.HashMap;

public enum TileType {

    GRASS(1,"grass",0,0),
    FOREST(2,"forest",1,30),
    MOUNTAIN(3,"mountain",2,30);

    /*GRASS(1,"grass"),
    DIRT(2,"dirt"),
    SKY(3,"sky"),
    LAVA(4,"lava"),
    CLOUD(5,"cloud"),
    STONE(6,"stone");*/

    public static final int TILE_SIZE=128;


    private int id;
    private String name;
    private int defBonus;
    private int avoBonus;

    TileType(int id, String name,int defBonus,int avoBonus){
        this.id=id;
        this.name=name;
        this.defBonus=defBonus;
        this.avoBonus=avoBonus;
    }

    public int getId() {
        return id;
    }

    public int getDefBonus(){
        return defBonus;
    }

    public int getAvoBonus(){
        return avoBonus;
    }

    public String getName() {
        return name;
    }

    private static HashMap<Integer, TileType> tileMap;

    static {
        tileMap=new HashMap<>();
        for (TileType tileType: TileType.values()){
            tileMap.put(tileType.getId(),tileType);
        }
    }

    public static TileType getTileTypeById(int id){
        return tileMap.get(id);
    }
}
