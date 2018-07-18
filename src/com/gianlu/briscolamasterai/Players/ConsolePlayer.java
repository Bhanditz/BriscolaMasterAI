package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Game;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Gianlu
 */
public class ConsolePlayer extends BasePlayer {
    private final Scanner scanner = new Scanner(System.in);

    public ConsolePlayer() {
        super("Console player");
    }

    @Override
    public void yourTurn(@NotNull Game.PublicInfo info) {
        System.out.println("Your hand: " + Arrays.toString(hand));
        System.out.print("Select a card to play: ");
        if (scanner.hasNextLine()) {
            int index = Integer.parseInt(scanner.nextLine());
            info.play(hand[index]);
        }

        throw new IllegalStateException("You must play a card!");
    }
}
