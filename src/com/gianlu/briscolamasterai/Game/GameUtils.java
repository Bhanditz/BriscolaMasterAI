package com.gianlu.briscolamasterai.Game;

import com.gianlu.briscolamasterai.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gianlu
 */
public class GameUtils {

    public static int evaluateTable(@NotNull Card trump, @NotNull Card[] table) {
        Card leading = table[0];
        for (int i = 1; i < table.length; i++) {
            Card current = table[i];
            if (current.suit == trump.suit) {
                if (leading.suit == trump.suit) {
                    if (current.value > leading.value)
                        leading = current;
                } else {
                    leading = current;
                }
            } else if (current.suit == leading.suit) {
                if (current.value > leading.value)
                    leading = current;
            }
        }

        return Utils.indexOf(table, leading);
    }

    public static int calcGain(Card[] table, boolean winner) {
        int points = 0;
        for (Card card : table) points += card.points;
        return points * (winner ? 1 : -1);
    }
}
