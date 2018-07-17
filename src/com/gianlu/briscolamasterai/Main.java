package com.gianlu.briscolamasterai;

import com.gianlu.briscolamasterai.Players.BasePlayer;
import com.gianlu.briscolamasterai.Players.RandomPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * @author Gianlu
 */
public class Main implements Game.Listener {
    private final Game game;

    public Main() {
        game = new Game(new RandomPlayer("Random one"), new RandomPlayer("Random two"), this);
        game.start();
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void gameStarted(@NotNull Card trump) {
        System.out.println("GAME STARTED!!");
        System.out.println("TRUMP: " + trump);
        System.out.println("---------------------------");
    }

    @Override
    public void turnOf(@NotNull BasePlayer player) {
        System.out.println("---------------------------");
        System.out.println("Turn of " + player);
        System.out.println("Current table is: " + Arrays.toString(game.table));
    }

    @Override
    public void playerWonRound(@NotNull BasePlayer player) {
        System.out.println("---------------------------");
        System.out.println("Round table: " + Arrays.toString(game.table));
        System.out.println("ROUND WINNER: " + player);
    }

    @Override
    public void gameEnded(@Nullable BasePlayer winner, int winnerPoints, int otherPoints) {
        System.out.println("---------------------------");
        System.out.println("GAME ENDED!!");
        System.out.println("WINNER: " + winner);
        System.out.println("POINTS: " + winnerPoints + " - " + otherPoints);
        System.out.println("---------------------------");
        System.exit(0);
    }
}
