package com.oop.project.battles;

public enum Ranges {
    MELEE("melee",1,1,false),
    BOWS("bows",2,2,false),
    MAGIC("magic",1,2,true),
    HEALER("healer",0,0,true);

    private String id;
    private int min;
    private int max;
    private boolean isMagic;

    Ranges(String id,int min, int max,boolean isMagic) {
        this.id = id;
        this.min=min;
        this.max=max;
        this.isMagic=isMagic;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }

    public boolean isMagic(){ return isMagic; }
}
