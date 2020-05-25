package eod.card.abstraction.action;

import eod.Player;
import eod.effect.Attack;
import eod.effect.EffectExecutor;

public abstract class AttackCard extends ActionCard {
    public AttackCard(int cost) {
        super(cost);
    }
    public Player rival;

    public abstract Attack attack();

    @Override
    public void setPlayer(Player p) {
        super.setPlayer(p);
        rival = p.rival();
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        executor.tryToExecute(attack());
    }
}
