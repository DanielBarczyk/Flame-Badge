package com.oop.project.entities;

import com.oop.project.map.TileType;

public enum Stats {

    HP("hp"),
    ATK("atk"),
    SPD("spd"),
    SKILL("skill"),
    LUCK("luck"),
    DEF("def"),
    RES("res"),
    MOV("mov"),
    EXP("exp"),
    LVL("lvl");

    private String id;

    Stats(String id) {
        this.id = id;
    }
}
