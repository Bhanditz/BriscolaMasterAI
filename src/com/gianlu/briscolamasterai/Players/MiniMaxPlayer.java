package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Ai.MiniMax;
import com.gianlu.briscolamasterai.Game.Game;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gianlu
 */
public class MiniMaxPlayer extends BasePlayer {
    public MiniMaxPlayer(int maxPly) {
        super("Minimax player");
        MiniMax.maxPly = maxPly;
    }

    @Override
    public void yourTurn(@NotNull Game.PublicInfo info) {
        long start = System.currentTimeMillis();
        MiniMax.miniMax(info.turnOf, info, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        long time = System.currentTimeMillis() - start;
        System.out.println("MINIMAX TOOK: " + time);
    }
}
