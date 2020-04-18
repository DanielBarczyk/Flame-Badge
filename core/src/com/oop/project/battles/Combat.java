package com.oop.project.battles;

import com.oop.project.entities.*;
import com.oop.project.map.GameMap;

public class Combat {

    public static void battle(Entity pc, Entity ec){
        boolean canCounter=canCounter(pc,ec);
        ec.takeDamage(pc.getUnitStats().get(Stats.ATK)-ec.getUnitStats().get(Stats.DEF));
        if(ec.getCurrentHp()>0&&canCounter)
            pc.takeDamage(ec.getUnitStats().get(Stats.ATK)-pc.getUnitStats().get(Stats.DEF));
        if(pc.getCurrentHp()>0&&pc.getUnitStats().get(Stats.SPD)>=ec.getUnitStats().get(Stats.SPD))
            ec.takeDamage(pc.getUnitStats().get(Stats.ATK)-ec.getUnitStats().get(Stats.DEF));
        if(ec.getCurrentHp()>0&&ec.getUnitStats().get(Stats.SPD)>=pc.getUnitStats().get(Stats.SPD)&&canCounter)
            pc.takeDamage(ec.getUnitStats().get(Stats.ATK)-pc.getUnitStats().get(Stats.DEF));
    }

    private static boolean canCounter(Entity a, Entity b){
        System.out.println("C");
        if(a.getRange()==b.getRange()) return true;
        System.out.println("C");
        if(b.getRange()==Ranges.MAGIC) return true;
        System.out.println("C");
        if(a.getRange()==Ranges.MAGIC){
            if(a.getDistance(b)==1&&b.getRange()==Ranges.MELEE){
                return true;
            }
            return (a.getDistance(b)==2&&b.getRange()==Ranges.BOWS);
        }
        return false;
    }
    public static boolean canTheyFight(Entity a, Entity b){
        int distance=a.getDistance(b);
        if(a.getRange()==Ranges.MELEE&&distance==1) return true;
        if(a.getRange()==Ranges.BOWS&&distance==2) return true;
        return (a.getRange()==Ranges.MAGIC&&(distance==1||distance==2));
    }

}
