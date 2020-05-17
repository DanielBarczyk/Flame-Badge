package com.oop.project.entities;

import com.oop.project.map.TileType;

public enum Stats {

    MAXHP("Hp"),
    ATK("Atk"),
    SPD("Spd"),
    SKILL("Skill"),
    LUCK("Luck"),
    DEF("Def"),
    RES("Res"),
    MOV("Mov"),
    EXP("Exp"),
    LVL("Lvl");

    private String id;

    Stats() {}

    Stats(String id) {
        this.id = id;
    }

    String getId(){
        return id;
    }
}
