package com.gianlu.briscolamasterai.Ai;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;

/**
 * @author Gianlu
 */
public class MiniMax {
    public static int maxPly = Integer.MAX_VALUE;

    private MiniMax() {
    }

    public static int miniMax(Game.Player player, Game.PublicInfo info, double alpha, double beta, int currentPly) {
        if (currentPly++ == maxPly || info.isEnded())
            return info.getPoints(player) - info.getPoints(Game.opposite(player));

        if (info.turnOf == player) return getMax(player, info, alpha, beta, currentPly);
        else return getMin(player, info, alpha, beta, currentPly);
    }

    private static int getMax(Game.Player player, Game.PublicInfo info, double alpha, double beta, int currentPly) { // Playing as AI
        // double bestScore = Double.NEGATIVE_INFINITY;
        Card bestCard = null;

        for (Card card : info.getHand(player)) {
            if (card == null) continue;

            Game.PublicInfo gameCopy = info.copyForMinimax(player);
            gameCopy.play(card);

            int score = miniMax(player, gameCopy, alpha, beta, currentPly);
            if (score > alpha) {
                alpha = score;
                bestCard = card;
            }

            if (alpha >= beta) break;

            /*
            if (score >= bestScore) {
                bestScore = score;
                bestCard = card;
            }
            */
        }

        if (bestCard != null) info.play(bestCard);
        return (int) alpha;
    }

    private static int getMin(Game.Player player, Game.PublicInfo info, double alpha, double beta, int currentPly) { // Playing as other player
        // double bestScore = Double.POSITIVE_INFINITY;
        Card bestCard = null;

        for (Card card : info.getUnusedCards(player)) {
            Game.PublicInfo gameCopy = info.copyForMinimax(player);
            gameCopy.play(card);

            int score = miniMax(player, gameCopy, alpha, beta, currentPly);
            if (score < beta) {
                beta = score;
                bestCard = card;
            }

            if (alpha >= beta) break;

            /*
            if (score <= bestScore) {
                bestScore = score;
                bestCard = card;
            }
            */
        }

        if (bestCard != null) info.play(bestCard);
        return (int) beta;
    }
}
