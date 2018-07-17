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


    public Game(@NotNull BasePlayer playerOne, @NotNull BasePlayer playerTwo, @NotNull Listener listener) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.listener = listener;
        deck = Deck.newDeck();
        info = new PublicInfo(deck.poll());
        deck.addLast(info.trump);

        handDeal(Player.ONE);
        handDeal(Player.TWO);
    }

    @NotNull
    private static Player opposite(@NotNull Player player) {
        return player == Player.ONE ? Player.TWO : Player.ONE;
    }

    public void start() {
        listener.gameStarted(info.trump);
        changeTurnTo(Player.ONE);
    }

    @NotNull
    private BasePlayer getPlayer(@NotNull Player player) {
        return player == Player.ONE ? playerOne : playerTwo;
    }

    private void playCard(@NotNull Player player, @NotNull Card card) {
        if (info.turnOf == null) throw new IllegalStateException("Game not started or ended!");
        if (info.turnOf != player) throw new IllegalStateException("Not your turn!");
        if (!Utils.contains(getPlayer(player).hand, card))
            throw new IllegalArgumentException(player + " doesn't have that card!");
        Utils.push(info.table, card);
        Utils.push(info.tablePlayedBy, player);
        Utils.remove(getPlayer(player).hand, card);
        checkEveryonePlayed();
    }

    private void changeTurnTo(@NotNull Game.Player set) {
        info.turnOf = set;
        listener.turnOf(set);
        playCard(set, getPlayer(set).selectCardToPlay(info));
    }

    private void checkEveryonePlayed() {
        if (Utils.countNotNull(info.table) == info.table.length) {
            Player winner = evaluateTable();
            listener.playerWonRound(winner);
            Utils.dumpArrayIntoList(info.table, getPlayer(winner).cardsWon);
            Utils.clear(info.tablePlayedBy);

            if (deck.isEmpty() && Utils.isEmpty(playerOne.hand) && Utils.isEmpty(playerTwo.hand)) {
                info.turnOf = null;
                evaluateGame();
            } else {
                handDeal(winner);
                handDeal(opposite(winner));
                changeTurnTo(winner);
            }
        } else {
            changeTurnTo(opposite(info.turnOf));
        }
    }

    @NotNull
    private Game.Player evaluateTable() {
        return info.tablePlayedBy[GameUtils.evaluateTable(info.trump, info.table)];
    }

    private void handDeal(@NotNull Player player) {
        BasePlayer realPlayer = getPlayer(player);
        for (int i = 0; i < realPlayer.hand.length; i++) {
            Card card = realPlayer.hand[i];
            if (card == null) realPlayer.hand[i] = deck.poll();
        }
    }

    private void evaluateGame() {
        int onePoints = playerOne.getPoints();
        int twoPoints = playerTwo.getPoints();
        if (onePoints == twoPoints) listener.gameEnded(null, onePoints, twoPoints);
        else if (onePoints > twoPoints) listener.gameEnded(Player.ONE, onePoints, twoPoints);
        else listener.gameEnded(Player.TWO, twoPoints, onePoints);
    }

    public enum Player {
        ONE,
        TWO
    }

    public interface Listener {
        void gameStarted(@NotNull Card trump);

        void turnOf(@NotNull Player player);

        void playerWonRound(@NotNull Player player);

        void gameEnded(@Nullable Player winner, int winnerPoints, int otherPoints);
    }

    public class PublicInfo {
        public final Card trump;
        public final Card[] table;
        public final Player[] tablePlayedBy;
        public Player turnOf;

        private PublicInfo(Card trump) {
            if (trump == null) throw new IllegalStateException("What the hell?!");

            this.table = new Card[2];
            this.tablePlayedBy = new Player[2];
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
