package com.gianlu.briscolamasterai;

import com.gianlu.briscolamasterai.Players.BasePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Gianlu
 */
public class Game {
    public final BasePlayer playerOne;
    public final BasePlayer playerTwo;
    public final Deck deck;
    public final Card trump;
    public final Card[] table;
    public final BasePlayer[] tablePlayedBy;
    private final Listener listener;
    public BasePlayer turnOf;

    public Game(@NotNull BasePlayer playerOne, @NotNull BasePlayer playerTwo, @NotNull Listener listener) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.listener = listener;
        deck = Deck.newDeck();
        table = new Card[2]; // Number of players
        tablePlayedBy = new BasePlayer[2]; // Number of players

        handDeal(playerOne);
        handDeal(playerTwo);
        trump = deck.poll();
        deck.addLast(trump);
    }

    public void start() {
        listener.gameStarted(trump);
        changeTurnTo(playerOne);
    }

    public void playCard(@NotNull BasePlayer player, @NotNull Card card) {
        if (turnOf == null) throw new IllegalStateException("Game not started!");
        if (turnOf != player) throw new IllegalStateException("Not your turn!");
        if (!Utils.contains(player.hand, card)) throw new IllegalArgumentException(player + " doesn't have that card!");
        Utils.push(table, card);
        Utils.push(tablePlayedBy, player);
        Utils.remove(player.hand, card);
        checkEveryonePlayed();
    }

    private void changeTurnTo(@NotNull BasePlayer player) {
        turnOf = player;
        listener.turnOf(player);
        playCard(turnOf, turnOf.selectCardToPlay());
    }

    private void checkEveryonePlayed() {
        if (Utils.countNotNull(table) == table.length) {
            BasePlayer winner = evaluateTable();
            listener.playerWonRound(winner);
            Utils.dumpArrayIntoList(table, winner.cardsWon);
            Utils.clear(tablePlayedBy);

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
        Card leading = table[0];
        for (int i = 1; i < table.length; i++) {
            Card current = table[i];
            if (current.suit == trump.suit) {
                if (leading.suit == trump.suit) {
                    if (current.value > leading.value)
                        leading = current;
                } else {
                    leading = current;
                }
            } else if (current.suit == leading.suit) {
                if (current.value > leading.value)
                    leading = current;
            }
        }

        return tablePlayedBy[Utils.indexOf(table, leading)];
    }

    public void handDeal(@NotNull BasePlayer player) {
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
}
