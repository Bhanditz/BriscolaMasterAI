package com.gianlu.briscolamasterai.Game;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Gianlu
 */
public class Deck extends LinkedList<Card> {

    private Deck() {
    }

    private Deck(@NotNull Queue<Card> deck) {
        super(deck);
    }

    @NotNull
    public static Deck newDeck() {
        Deck deck = new Deck();
        for (Suit suit : Suit.values()) deck.addAll(Card.getOneSuit(suit));
        Collections.shuffle(deck, new SecureRandom());
        return deck;
    }

    @NotNull
    public static Deck from(@NotNull Queue<Card> queue) {
        return new Deck(queue);
    }
}
