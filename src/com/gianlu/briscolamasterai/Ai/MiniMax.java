package com.gianlu.briscolamasterai.Ai;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;

/**
 * @author Gianlu
 */
public class MiniMax {
    private static int maxPly = 6;

    private MiniMax() {
    }

    public static int miniMax(Game.Player player, Game.PublicInfo info, int currentPly) {
        if (currentPly++ == maxPly || info.isEnded()) {
            return info.getPoints(player) - info.getPoints(Game.opposite(player));
        }

        if (info.turnOf == player) {
            return getMax(player, info, currentPly);
        } else {
            return getMin(player, info, currentPly);
        }
    }

    private static int getMax(Game.Player player, Game.PublicInfo info, int currentPly) { // Playing as AI
        double bestScore = Double.NEGATIVE_INFINITY;
        Card bestCard = null;

        for (Card card : info.getHand(player)) {
            if (card == null) continue;

            Game.PublicInfo gameCopy = info.copyWithDummyPlayers();
            gameCopy.play(card);

            int score = miniMax(player, gameCopy, currentPly);
            if (score >= bestScore) {
                bestScore = score;
                bestCard = card;
            }
        }

        if (bestCard == null)
            System.out.println();

        info.play(bestCard);
        return (int) bestScore;
    }

    private static int getMin(Game.Player player, Game.PublicInfo info, int currentPly) { // Playing as other player
        double bestScore = Double.POSITIVE_INFINITY;
        Card bestCard = null;

        for (Card card : info.getUnusedCards(player)) {
            Game.PublicInfo gameCopy = info.copyWithDummyPlayers();
            gameCopy.play(card);

            int score = miniMax(player, gameCopy, currentPly);
            if (score <= bestScore) {
                bestScore = score;
                bestCard = card;
            }
        }

        info.play(bestCard);
        return (int) bestScore;
    }
}
