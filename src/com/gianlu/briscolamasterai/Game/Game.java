package com.gianlu.briscolamasterai.Game;

import com.gianlu.briscolamasterai.Players.BasePlayer;
import com.gianlu.briscolamasterai.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Gianlu
 */
public class Game {
    public final PublicInfo info;
    private final BasePlayer playerOne;
    private final BasePlayer playerTwo;
    private final Deck deck;
    private final Listener listener;
    private BasePlayer turnOf;

    public Game(@NotNull BasePlayer playerOne, @NotNull BasePlayer playerTwo, @NotNull Listener listener) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.listener = listener;
        deck = Deck.newDeck();
        info = new PublicInfo(deck.poll());
        deck.addLast(info.trump);

        handDeal(playerOne);
        handDeal(playerTwo);
    }

    public void start() {
        listener.gameStarted(info.trump);
        changeTurnTo(playerOne);
    }

    private void playCard(@NotNull BasePlayer player, @NotNull Card card) {
        if (turnOf == null) throw new IllegalStateException("Game not started or ended!");
        if (turnOf != player) throw new IllegalStateException("Not your turn!");
        if (!Utils.contains(player.hand, card)) throw new IllegalArgumentException(player + " doesn't have that card!");
        Utils.push(info.table, card);
        Utils.push(info.tablePlayedBy, player);
        Utils.remove(player.hand, card);
        checkEveryonePlayed();
    }

    private void changeTurnTo(@NotNull BasePlayer player) {
        turnOf = player;
        listener.turnOf(player);
        playCard(turnOf, turnOf.selectCardToPlay(info));
    }

    private void checkEveryonePlayed() {
        if (Utils.countNotNull(info.table) == info.table.length) {
            BasePlayer winner = evaluateTable();
            listener.playerWonRound(winner);
            Utils.dumpArrayIntoList(info.table, winner.cardsWon);
            Utils.clear(info.tablePlayedBy);

            if (deck.isEmpty() && Utils.isEmpty(playerOne.hand) && Utils.isEmpty(playerTwo.hand)) {
                turnOf = null;
                evaluateGame();
            } else {
                handDeal(winner);
                handDeal(winner == playerOne ? playerTwo : playerOne);
                changeTurnTo(winner);
            }
        } else {
            if (turnOf == playerOne) changeTurnTo(playerTwo);
            else if (turnOf == playerTwo) changeTurnTo(playerOne);
            else throw new IllegalStateException("Where did this guy come from?! " + turnOf);
        }
    }

    @NotNull
    private BasePlayer evaluateTable() {
        return info.tablePlayedBy[GameUtils.evaluateTable(info.trump, info.table)];
    }

    private void handDeal(@NotNull BasePlayer player) {
        for (int i = 0; i < player.hand.length; i++) {
            Card card = player.hand[i];
            if (card == null) player.hand[i] = deck.poll();
        }
    }

    private void evaluateGame() {
        int onePoints = playerOne.getPoints();
        int twoPoints = playerTwo.getPoints();
        if (onePoints == twoPoints) listener.gameEnded(null, onePoints, twoPoints);
        else if (onePoints > twoPoints) listener.gameEnded(playerOne, onePoints, twoPoints);
        else listener.gameEnded(playerTwo, twoPoints, onePoints);
    }

    public interface Listener {
        void gameStarted(@NotNull Card trump);

        void turnOf(@NotNull BasePlayer player);

        void playerWonRound(@NotNull BasePlayer player);

        void gameEnded(@Nullable BasePlayer winner, int winnerPoints, int otherPoints);
    }

    public class PublicInfo {
        public final Card trump;
        public final Card[] table;
        public final BasePlayer[] tablePlayedBy;

        private PublicInfo(Card trump) {
            if (trump == null) throw new IllegalStateException("What the hell?!");

            this.table = new Card[2];
            this.tablePlayedBy = new BasePlayer[2];
            this.trump = trump;
        }

        public boolean playingFirst() {
            return table[0] == null;
        }

        @NotNull
        public Queue<Card> getUnusedCards() {
            return new LinkedList<>(deck);
        }
    }
}
