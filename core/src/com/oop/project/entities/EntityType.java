package com.oop.project.entities;

public enum EntityType {

    PLAYER_UNIT("playable_character",100,110),
    ENEMY_UNIT("enemy_character",100,110);

    private String id;
    private int width,height;

    EntityType(String id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
