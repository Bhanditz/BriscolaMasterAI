package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import com.gianlu.briscolamasterai.Game.GameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Queue;
import java.util.Random;

/**
 * @author Gianlu
 */
public class AiPlayer extends BasePlayer {
    private final Random random = new Random(System.nanoTime());

    public AiPlayer(String name) {
        super(name);
    }

    @Nullable
    public static Card findBestPlay(Card trump, Card[] hand, Queue<Card> unusedCards) {
        Card bestPlay = null;
        int bestPlayGain = Integer.MIN_VALUE;
        for (Card possiblePlay : hand) {
            if (possiblePlay == null) continue;

            int totalPlayGain = 0;
            for (Card otherPossiblePlay : unusedCards) {
                Card[] possibleTable = new Card[]{possiblePlay, otherPossiblePlay};
                boolean winner = GameUtils.evaluateTable(trump, possibleTable) == 1;
                totalPlayGain += GameUtils.calcGain(possibleTable, winner);
            }

            if (totalPlayGain > bestPlayGain) {
                bestPlayGain = totalPlayGain;
                bestPlay = possiblePlay;
            }
        }

        return bestPlay;
    }

    @Override
    public void yourTurn(@NotNull Game.PublicInfo info) {
        Card play;
        if (info.playingFirst()) play = findBestPlay(info.trump, hand, info.getUnusedCards(info.turnOf));
        else play = PseudoAiPlayer.findBestPlay(info.trump, hand, info.table[0]);
        if (play == null) play = RandomPlayer.randomCard(random, hand);
        info.play(play);
    }
}
