package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Gianlu
 */
public class RandomPlayer extends BasePlayer {
    private final Random random = new Random(System.nanoTime());

    public RandomPlayer(String name) {
        super(name);
    }

    @NotNull
    public static Card randomCard(@NotNull Random random, Card[] hand) {
        int availableIndexesSize = 0;
        int[] availableIndexes = new int[3];
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] != null) {
                availableIndexes[availableIndexesSize] = i;
                availableIndexesSize++;
            }
        }

        if (availableIndexesSize == 0) throw new IllegalArgumentException("Hand is empty!!");
        return hand[availableIndexes[random.nextInt(availableIndexesSize)]];
    }

    @NotNull
    @Override
    public Card selectCardToPlay(@NotNull Game game) {
        Card card = randomCard(random, hand);
        System.out.println("Played " + card);
        return card;
    }
}
