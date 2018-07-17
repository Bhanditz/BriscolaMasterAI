package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Gianlu
 */
public class PseudoAiPlayer extends BasePlayer {
    private static final int ADDITIONAL_VALUE_OF_TRUMP = 8;
    private final Random random = new Random(System.nanoTime());

    public PseudoAiPlayer(String name) {
        super(name);
    }

    private static int assignWeight(Card[] table, boolean winner) {
        int points = 0;
        for (Card card : table) points += card.points;
        return points * (winner ? 1 : -1);
    }

    @Override
    public @NotNull Card selectCardToPlay(@NotNull Game game) {
        if (game.table[0] == null) {
            int lessHarmfulWeight = Integer.MAX_VALUE;
            Card lessHarmful = null;
            for (Card card : hand) {
                if (card == null) continue;
                int possibleHarmfulWeight = (card.points + card.value) + (game.trump.suit == card.suit ? ADDITIONAL_VALUE_OF_TRUMP : 0);
                if (possibleHarmfulWeight < lessHarmfulWeight) {
                    lessHarmfulWeight = possibleHarmfulWeight;
                    lessHarmful = card;
                }
            }

            if (lessHarmful != null) return lessHarmful;
            else return RandomPlayer.randomCard(random, hand);
        } else {
            int bestPlayWeight = Integer.MIN_VALUE;
            Card bestPlay = null;
            for (Card card : hand) {
                if (card == null) continue;

                Card[] possibleTable = new Card[]{game.table[0], card};
                boolean winner = Game.evaluateTable(game.trump, possibleTable) == 1;
                int possibleWeight = assignWeight(possibleTable, winner);
                if (possibleWeight > bestPlayWeight) {
                    bestPlayWeight = possibleWeight;
                    bestPlay = card;
                }
            }

            if (bestPlay != null) return bestPlay;
            else return RandomPlayer.randomCard(random, hand);
        }
    }
}
