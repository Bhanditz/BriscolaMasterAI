package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

/**
 * @author Gianlu
 */
public class ConsolePlayer extends BasePlayer {
    private final Scanner scanner = new Scanner(System.in);

    public ConsolePlayer(String name) {
        super(name);
    }

    @Override
    @NotNull
    public Card selectCardToPlay(@NotNull Game.PublicInfo info) {
        System.out.print("Select a card to play: ");
        if (scanner.hasNextLine()) {
            int index = Integer.parseInt(scanner.nextLine());
            return hand[index];
        }

        throw new IllegalStateException("You must play a card!");
    }
}
