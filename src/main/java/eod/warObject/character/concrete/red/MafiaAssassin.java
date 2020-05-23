package eod.warObject.character.concrete.red;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.MafiaAssassinSummon;
import eod.effect.RegionalAttack;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.Damageable;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.assaulter.Assassin;
import eod.warObject.leader.Leader;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class MafiaAssassin extends Assassin {
    public MafiaAssassin(Player player) {
        super(player, 1, 3, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new MafiaAssassinSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "組織暗殺者";
    }

    @Override
    public void attack() {
        super.attack();
        SpecialRegionalAttack a = new SpecialRegionalAttack(player, attack);
        a.from(this).to(getAttackRange());
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().get4Ways(position, param);
    }

    public class SpecialRegionalAttack extends RegionalAttack {
        public SpecialRegionalAttack(Player player, int hp) {
            super(player, hp);
        }

        @Override
        public RegionalAttack to(ArrayList<Point> targets) {
            Gameboard board = player.getBoard();
            for(Point p:targets) {
                try {
                    WarObject target = board.getObjectOn(p.x, p.y);
                    if(target instanceof Leader) {
                        param.hp *= 2;
                    }
                    Damageable t = (Damageable) target;
                    affected.addAll(attacker.attack(t, param));
                } catch (IllegalArgumentException e) {
                    System.out.println("There's no object on ("+p.x+", "+p.y+"). \nSkipping.");
                } catch (NotSupportedException e) {
                    System.out.println("The attacker "+((WarObject)attacker).getName()+" cannot attack a target directly.");
                }
            }
            return this;
        }
    }
}