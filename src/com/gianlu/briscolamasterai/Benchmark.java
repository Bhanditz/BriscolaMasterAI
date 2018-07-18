package com.gianlu.briscolamasterai;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import com.gianlu.briscolamasterai.Players.AiPlayer;
import com.gianlu.briscolamasterai.Players.MiniMaxPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gianlu
 */
public class Benchmark implements Game.Listener {
    private final Map<Game.Player, Integer> result;

    private Benchmark(int num) {
        result = new HashMap<>(3);

        for (int i = 0; i < num; i++) {
            Game game = new Game(new AiPlayer("AI one"), new MiniMaxPlayer("MM two"), this);
            game.start();
        }

        System.out.println(result);
    }

    public static void main(String[] args) {
        new Benchmark(20);
    }

    @Override
    public void gameStarted(@NotNull Card trump) {
    }

    @Override
    public void turnOf(@NotNull Game.Player player, int round) {
    }

    @Override
    public void playerWonRound(@NotNull Game.Player player) {
    }

    @Override
    public void gameEnded(@Nullable Game.Player winner, int winnerPoints, int otherPoints) {
        int wins = result.getOrDefault(winner, 0);
        wins++;
        result.put(winner, wins);
    }
}
