package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import com.gianlu.briscolamasterai.Game.GameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Queue;

/**
 * @author Gianlu
 */
public class AiPlayer extends BasePlayer {

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
    public @NotNull Card selectCardToPlay(@NotNull Game.PublicInfo info) {
        if (info.playingFirst()) {
            Card bestPlay = findBestPlay(info.trump, hand, info.getUnusedCards());
            if (bestPlay == null) return hand[0];
            else return bestPlay;
        } else {
            Card bestPlay = PseudoAiPlayer.findBestPlay(info.trump, hand, info.table[0]);
            if (bestPlay == null) return hand[0];
            else return bestPlay;
        }
    }
}
