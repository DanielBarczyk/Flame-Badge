package com.oop.project.battles;

import com.oop.project.entities.*;
import com.oop.project.map.GameMap;

public class Combat {

    public static void battle(Entity pc, Entity ec){
        boolean canCounter=canCounter(pc,ec);
        if(pc.getRange().isMagic()){
            magicAttack(pc,ec);
        }
        else
            meleeAttack(pc,ec);
        if(ec.getCurrentHp()>0&&canCounter&&ec.getCurrentHp()>0){
            if(ec.getRange().isMagic())
                magicAttack(ec,pc);
            else
                meleeAttack(pc,ec);
        }
        if(pc.getCurrentHp()>0&&pc.getUnitStats().get(Stats.SPD)>=ec.getUnitStats().get(Stats.SPD)){
            if(pc.getRange().isMagic()){
                magicAttack(pc,ec);
            }
            else
                meleeAttack(pc,ec);
        }
        if(ec.getCurrentHp()>0&&ec.getUnitStats().get(Stats.SPD)>=pc.getUnitStats().get(Stats.SPD)&&canCounter){
            if(ec.getRange().isMagic())
                magicAttack(ec,pc);
            else
                meleeAttack(pc,ec);
        }
        if(pc.getType()==EntityType.PLAYER_UNIT){
            PlayableCharacter p=(PlayableCharacter)pc;
            p.gainExp(ec);
        }
        if(ec.getType()==EntityType.PLAYER_UNIT){
            PlayableCharacter p=(PlayableCharacter)ec;
            p.gainExp(pc);
        }
    }

    private static void meleeAttack(Entity pc,Entity ec){
        ec.takeDamage(pc.getUnitStats().get(Stats.ATK)-ec.getUnitStats().get(Stats.DEF));
    }

    private static void magicAttack(Entity pc, Entity ec){
        ec.takeDamage(pc.getUnitStats().get(Stats.ATK)-ec.getUnitStats().get(Stats.RES));
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
        if(a==null||b==null) return false;
        int distance=a.getDistance(b);
        if(a.getRange()==Ranges.MELEE&&distance==1) return true;
        if(a.getRange()==Ranges.BOWS&&distance==2) return true;
        return (a.getRange()==Ranges.MAGIC&&(distance==1||distance==2));
    }
}
