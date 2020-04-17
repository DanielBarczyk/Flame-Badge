package com.oop.project.map;

import java.util.HashMap;

public enum TileType {

    GRASS(1,"grass"),
    FOREST(2,"forest");

    /*GRASS(1,"grass"),
    DIRT(2,"dirt"),
    SKY(3,"sky"),
    LAVA(4,"lava"),
    CLOUD(5,"cloud"),
    STONE(6,"stone");*/

    public static final int TILE_SIZE=128;


    private int id;
    private String name;

    TileType(int id, String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
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
