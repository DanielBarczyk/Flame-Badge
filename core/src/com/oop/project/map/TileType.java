package com.oop.project.map;

import java.util.HashMap;

public enum TileType {

    GRASS(1,"grass",0,0,true),
    FOREST(2,"forest",1,30,true),
    MOUNTAIN(3,"mountain",2,30,true),
    WATER(4,"water",0,0,false),
    BRIDGE(5,"bridge",1,0,true);


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
    private boolean isTraversible;


    TileType(int id, String name,int defBonus,int avoBonus, boolean isTraversible){
        this.id=id;
        this.name=name;
        this.defBonus=defBonus;
        this.avoBonus=avoBonus;
        this.isTraversible=isTraversible;
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

    public boolean isTraversible(){
        return isTraversible;
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
