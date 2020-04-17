package com.oop.project.battles;

public enum Ranges {
    MELEE("melee"),
    BOWS("bows"),
    MAGIC("magic");

    private String id;

    Ranges(String id) {
        this.id = id;
    }

}
