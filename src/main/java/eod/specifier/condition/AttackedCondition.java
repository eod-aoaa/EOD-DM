package eod.specifier.condition;

import eod.Character;

import java.util.Arrays;

public class AttackedCondition implements Condition {

    @Override
    public Character[] filter(Character[] characters) {
        return Arrays.stream(characters)
                .filter(character -> character.isAttacked)
                .toArray(Character[]::new);
    }
}