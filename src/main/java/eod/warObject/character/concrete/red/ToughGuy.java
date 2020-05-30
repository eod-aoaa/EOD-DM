package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.red.ToughGuySummon;
import eod.effect.EffectExecutor;
import eod.event.AfterObjectDamageEvent;
import eod.event.Event;
import eod.event.relay.EventReceiver;
import eod.param.PointParam;
import eod.warObject.Status;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.*;

public class ToughGuy extends Fighter {
    public ToughGuy(Player player) {
        super(player, 6, 3, Party.RED);
        new OwnedAbilities();
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new ToughGuySummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "蠻勇的巨漢";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        executor.tryToExecute(
            RequestRegionalAttack(attack).from(this).to(getAttackRange())
        );

        afterAttack();
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }

    private class OwnedAbilities implements EventReceiver {

        public OwnedAbilities() {
            ToughGuy.this.registerReceiver(AfterObjectDamageEvent.class, this);
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(ToughGuy.this.hasStatus(Status.NO_EFFECT)) {
                return;
            }
            if(event instanceof AfterObjectDamageEvent) {
                AfterObjectDamageEvent e = (AfterObjectDamageEvent) event;
                if(e.getVictim() == ToughGuy.this) {
                    Player owner = ToughGuy.this.getPlayer();
                    owner.tryToExecute(
                            IncreaseAttack(2).to(ToughGuy.this)
                    );
                    owner.tryToExecute(
                            IncreaseHealth(2).to(ToughGuy.this)
                    );
                }
            }
        }

        @Override
        public void teardown() {
            ToughGuy.this.unregisterReceiver(AfterObjectDamageEvent.class, this);
        }
    }
}
