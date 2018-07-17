package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Gianlu
 */
public class AiPlayer extends BasePlayer {
    private final Random random = new Random(System.nanoTime());

    public AiPlayer(String name) {
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
            return RandomPlayer.randomCard(random, hand); // FIXME: That's the weak point
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
