package com.gianlu.briscolamasterai.Game;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Gianlu
 */
public class Deck extends LinkedList<Card> {

    private Deck() {
    }

    @NotNull
    public static Deck newDeck() {
        Deck deck = new Deck();
        for (Suit suit : Suit.values()) deck.addAll(Card.getOneSuit(suit));
        Collections.shuffle(deck, new SecureRandom());
        return deck;
    }
}
