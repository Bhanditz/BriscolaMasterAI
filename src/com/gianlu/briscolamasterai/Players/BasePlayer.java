package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Card;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gianlu
 */
public abstract class BasePlayer {
    public final Card[] hand;
    public final List<Card> cardsWon;
    public final String name;

    public BasePlayer(String name) {
        this.name = name;
        this.hand = new Card[3];
        this.cardsWon = new ArrayList<>(20);
    }

    public final int getPoints() {
        int points = 0;
        for (Card card : cardsWon) points += card.points;
        return points;
    }

    @NotNull
    public abstract Card selectCardToPlay();

    @Override
    public String toString() {
        return "BasePlayer{" +
                "name='" + name + '\'' +
                ", hand=" + Arrays.toString(hand) +
                '}';
    }
}
