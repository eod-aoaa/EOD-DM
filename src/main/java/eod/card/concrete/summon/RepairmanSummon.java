package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SapperSummon;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.blue.Repairman;

import static eod.effect.EffectFunctions.Summon;

public class RepairmanSummon extends SapperSummon {
    public RepairmanSummon() {
        super(2, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new Repairman(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new RepairmanSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 維修技師";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }
}
