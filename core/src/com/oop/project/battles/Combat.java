package com.oop.project.battles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
                meleeAttack(ec,pc);
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
                meleeAttack(ec,pc);
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
        if(doesAttackConnect(pc,ec))
        ec.takeDamage(countMeleeDamage(pc,ec));
    }

    private static void magicAttack(Entity pc, Entity ec){
        if(doesAttackConnect(pc,ec))
        ec.takeDamage(countMagicDamage(pc,ec));
    }

    private static boolean canCounter(Entity a, Entity b){
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
    public static boolean canTheyFight(Entity a, Entity b){
        if(a==null||b==null) return false;
        int distance=a.getDistance(b);
        if(a.getRange()==Ranges.MELEE&&distance==1) return true;
        if(a.getRange()==Ranges.BOWS&&distance==2) return true;
        return (a.getRange()==Ranges.MAGIC&&(distance==1||distance==2));
    }

    private static int countAvo(Entity a){
        return a.getUnitStats().get(Stats.SPD)*2+a.getUnitStats().get(Stats.LUCK);//add terrain bonuses
    }

    private static int countHit(Entity a){
        return a.getUnitStats().get(Stats.SPD)*2+a.getCurrentlyEquipped().getHit();
    }

    private static int countActualHit(Entity a,Entity b){
        return countHit(a)-countAvo(b);
    }

    private static boolean doesAttackConnect(Entity a, Entity b){
        int roll=(int)(Math.random()*100);
        System.out.println(countActualHit(a,b)+"   rolled: "+roll);
        return countActualHit(a,b)>=roll;
    }

    private static int countAtk(Entity a){
        return a.getUnitStats().get(Stats.ATK)+a.getCurrentlyEquipped().getAttack();
    }

    private static int countMeleeDamage(Entity pc, Entity ec){
        return Math.max(countAtk(pc)-ec.getUnitStats().get(Stats.DEF),0);
    }

    private static int countMagicDamage(Entity pc, Entity ec){
        return Math.max(countAtk(pc)-ec.getUnitStats().get(Stats.RES),0);
    }

    private static String combatPrediction(Entity a, Entity b){
        String result="Your Atk: ";
        if(a.getRange().isMagic())
            result+=countMagicDamage(a,b);
        else
            result+=countMeleeDamage(a,b);
        result+="\nYour Hit: "+countActualHit(a,b)+"\nYour Speed: "+a.getUnitStats().get(Stats.SPD);
        if(canCounter(a,b)){
            result+= "\n\nEnemy Atk: ";
            if(b.getRange().isMagic())
                result+=countMagicDamage(b,a);
            else
                result+=countMeleeDamage(a,b);
            result+="\nEnemy Hit: "+countActualHit(b,a);
        }
        result+="\nEnemy Speed: "+b.getUnitStats().get(Stats.SPD);
        if(a.getUnitStats().get(Stats.SPD)>b.getUnitStats().get(Stats.SPD))
            result+="\n You attack twice";
        if(a.getUnitStats().get(Stats.SPD)<b.getUnitStats().get(Stats.SPD))
            result+="\n Enemy attacks twice";
        return result;
    }

    public static void showCombatPrediction(Entity a, Entity b, Stage stage,Skin skin){
        final TextButton combatPrediction = new TextButton(combatPrediction(a,b), skin);
            combatPrediction.setPosition((float) Gdx.graphics.getWidth()/2 - combatPrediction.getWidth()/2,
                    (float)Gdx.graphics.getHeight()/4 + combatPrediction.getHeight()/2 );

        stage.addActor(combatPrediction);
        combatPrediction.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                combatPrediction.remove();
            }
        });

    }
}
