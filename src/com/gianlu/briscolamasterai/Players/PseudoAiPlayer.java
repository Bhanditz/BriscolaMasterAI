package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import com.gianlu.briscolamasterai.Game.GameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Nullable
    public static Card findBestPlay(Card trump, Card[] hand, Card firstPlayedCard) {
        int bestPlayGain = Integer.MIN_VALUE;
        Card bestPlay = null;
        for (Card card : hand) {
            if (card == null) continue;

            Card[] possibleTable = new Card[]{firstPlayedCard, card};
            boolean winner = GameUtils.evaluateTable(trump, possibleTable) == 1;
            int possibleGain = GameUtils.calcGain(possibleTable, winner);
            if (possibleGain > bestPlayGain) {
                bestPlayGain = possibleGain;
                bestPlay = card;
            }
        }

        return bestPlay;
    }

    @Override
    public void yourTurn(@NotNull Game.PublicInfo info) {
        Card play;
        if (info.playingFirst()) {
            int lessHarmfulWeight = Integer.MAX_VALUE;
            Card lessHarmful = null;
            for (Card card : hand) {
                if (card == null) continue;
                int possibleHarmfulWeight = (card.points + card.value) + (info.trump.suit == card.suit ? ADDITIONAL_VALUE_OF_TRUMP : 0);
                if (possibleHarmfulWeight < lessHarmfulWeight) {
                    lessHarmfulWeight = possibleHarmfulWeight;
                    lessHarmful = card;
                }
            }

            play = lessHarmful;
        } else {
            play = findBestPlay(info.trump, hand, info.table[0]);
        }

        if (play == null) play = RandomPlayer.randomCard(random, hand);
        info.play(play);
    }
}
