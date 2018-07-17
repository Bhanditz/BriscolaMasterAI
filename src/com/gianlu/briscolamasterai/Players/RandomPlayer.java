package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Card;
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
    @Override
    public Card selectCardToPlay() {
        int availableIndexesSize = 0;
        int[] availableIndexes = new int[3];
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] != null) {
                availableIndexes[availableIndexesSize] = i;
                availableIndexesSize++;
            }
        }

        Card card = hand[availableIndexes[random.nextInt(availableIndexesSize)]];
        System.out.println("Played " + card);
        return card;
    }
}
