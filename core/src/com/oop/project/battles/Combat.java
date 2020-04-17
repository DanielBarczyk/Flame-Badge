package com.oop.project.battles;

import com.oop.project.entities.EnemyCharacter;
import com.oop.project.entities.Entity;
import com.oop.project.entities.PlayableCharacter;
import com.oop.project.entities.Stats;

public class Combat {

    private void battlePlayerLead(PlayableCharacter pc, EnemyCharacter ec){
        boolean canCounter=canCounter(pc,ec);
        ec.takeDamage(pc.getUnitStats().get(Stats.ATK)-ec.getUnitStats().get(Stats.DEF));
        if(ec.getCurrentHp()>0&&canCounter)
            pc.takeDamage(ec.getUnitStats().get(Stats.ATK)-pc.getUnitStats().get(Stats.DEF));
        if(pc.getCurrentHp()>0&&pc.getUnitStats().get(Stats.SPD)>=ec.getUnitStats().get(Stats.SPD))
            ec.takeDamage(pc.getUnitStats().get(Stats.ATK)-ec.getUnitStats().get(Stats.DEF));
        if(ec.getCurrentHp()>0&&ec.getUnitStats().get(Stats.SPD)>=pc.getUnitStats().get(Stats.SPD))
            pc.takeDamage(ec.getUnitStats().get(Stats.ATK)-pc.getUnitStats().get(Stats.DEF));
    }


    private void battlePlayerLead(EnemyCharacter ec,PlayableCharacter pc){
        boolean canCounter=canCounter(pc,ec);
        pc.takeDamage(ec.getUnitStats().get(Stats.ATK)-pc.getUnitStats().get(Stats.DEF));
        if(pc.getCurrentHp()>0&&canCounter)
            ec.takeDamage(pc.getUnitStats().get(Stats.ATK)-ec.getUnitStats().get(Stats.DEF));
        if(ec.getCurrentHp()>0&&ec.getUnitStats().get(Stats.SPD)>=pc.getUnitStats().get(Stats.SPD))
            pc.takeDamage(ec.getUnitStats().get(Stats.ATK)-pc.getUnitStats().get(Stats.DEF));
        if(pc.getCurrentHp()>0&&pc.getUnitStats().get(Stats.SPD)>=ec.getUnitStats().get(Stats.SPD))
            ec.takeDamage(pc.getUnitStats().get(Stats.ATK)-ec.getUnitStats().get(Stats.DEF));
    }

    private boolean canCounter(Entity a, Entity b){
        if(a.getRange()==b.getRange()) return true;
        if(b.getRange()==Ranges.MAGIC) return true;
        if(a.getRange()==Ranges.MAGIC){
            if(a.getDistance(b)==1&&b.getRange()==Ranges.MELEE){
                return true;
            }
            return (a.getDistance(b)==2&&b.getRange()==Ranges.BOWS);
        }
        return false;
    }
}
