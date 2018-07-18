package com.gianlu.briscolamasterai.Players;

import com.gianlu.briscolamasterai.Game.Card;
import com.gianlu.briscolamasterai.Game.Game;
import com.gianlu.briscolamasterai.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gianlu
 */
public abstract class BasePlayer {
    public final Card[] hand;
    public final String name;
    protected final List<Card> cardsWon;

    public BasePlayer(String name) {
        this.name = name;
        this.hand = new Card[3];
        this.cardsWon = new ArrayList<>(20);
    }

    public void pushToHand(int pos, Card card) {
        hand[pos] = card;
    }

    public final int getPoints() {
        int points = 0;
        for (Card card : cardsWon) points += card.points;
        return points;
    }

    public void wonCards(Card[] cards) {
        Utils.dumpArrayIntoList(cards, cardsWon);
    }

    public abstract void yourTurn(@NotNull Game.PublicInfo info);

    @Override
    public String toString() {
        return "BasePlayer{" +
                "name='" + name + '\'' +
                ", hand=" + Arrays.toString(hand) +
                '}';
    }

    public static class Dummy extends BasePlayer {
        private final boolean emptyHand;

        public Dummy(BasePlayer player, boolean emptyHand) {
            super(player instanceof Dummy ? player.name : ("Dummy " + player.name));
            this.emptyHand = emptyHand;
            cardsWon.addAll(player.cardsWon);
            if (!emptyHand) System.arraycopy(player.hand, 0, hand, 0, 3);
        }

        @Override
        public void pushToHand(int pos, Card card) {
            if (!emptyHand) super.pushToHand(pos, card);
        }

        @Override
        public void yourTurn(Game.@NotNull PublicInfo info) {
        }
    }
}
