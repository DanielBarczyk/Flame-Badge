package com.oop.project.battles;

public enum Ranges {
    MELEE("melee",1,1),
    BOWS("bows",2,2),
    MAGIC("magic",1,2);

    private String id;
    private int min;
    private int max;

    Ranges(String id,int min, int max) {
        this.id = id;
        this.min=min;
        this.max=max;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }
}
