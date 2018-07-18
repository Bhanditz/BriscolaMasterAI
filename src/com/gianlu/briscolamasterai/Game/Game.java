package com.gianlu.briscolamasterai.Game;

import com.gianlu.briscolamasterai.Players.BasePlayer;
import com.gianlu.briscolamasterai.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
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
    private int round = 0;

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

    private Game(@NotNull BasePlayer playerOne, @NotNull BasePlayer playerTwo, Deck deck, Card trump, Card[] ts, Player[] ts1, Player turnOf) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.deck = deck;
        this.listener = null;
        this.info = new PublicInfo(trump, ts, ts1, turnOf);
    }

    @NotNull
    public static Player opposite(@NotNull Player player) {
        return player == Player.ONE ? Player.TWO : Player.ONE;
    }

    @NotNull
    private Game copyForMinimax(Player player) {
        return new Game(new BasePlayer.Dummy(playerOne, player != Player.ONE), new BasePlayer.Dummy(playerTwo, player != Player.TWO), Deck.from(deck), info.trump, Arrays.copyOf(info.table, info.table.length), Arrays.copyOf(info.tablePlayedBy, info.tablePlayedBy.length), info.turnOf);
    }

    public void start() {
        if (listener != null) listener.gameStarted(info.trump);
        changeTurnTo(Player.ONE);
    }

    @NotNull
    private BasePlayer getPlayer(@NotNull Player player) {
        return player == Player.ONE ? playerOne : playerTwo;
    }

    public void playCard(@NotNull Player player, @NotNull Card card) {
        if (info.turnOf == null) throw new IllegalStateException("Game not started or ended!");
        if (info.turnOf != player) throw new IllegalStateException("Not your turn!");
        // if (!Utils.contains(getPlayer(player).hand, card)) throw new IllegalArgumentException(player + " doesn't have that card!");
        Utils.push(info.table, card);
        Utils.push(info.tablePlayedBy, player);
        Utils.remove(getPlayer(player).hand, card);
        checkEveryonePlayed();
    }

    private void changeTurnTo(@NotNull Game.Player set) {
        info.turnOf = set;
        if (listener != null) listener.turnOf(set, round);
        getPlayer(set).yourTurn(info);
    }

    private void checkEveryonePlayed() {
        if (Utils.countNotNull(info.table) == info.table.length) {
            Player winner = evaluateTable();
            if (listener != null) listener.playerWonRound(winner);
            getPlayer(winner).wonCards(info.table);
            Utils.clear(info.tablePlayedBy);

            round++;

            if (info.isEnded()) {
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
        for (int i = 0; i < 3; i++) {
            if (realPlayer.hand[i] == null) realPlayer.pushToHand(i, deck.poll());
        }
    }

    private void evaluateGame() {
        int onePoints = playerOne.getPoints();
        int twoPoints = playerTwo.getPoints();
        if (listener != null) {
            if (onePoints == twoPoints) listener.gameEnded(null, onePoints, twoPoints);
            else if (onePoints > twoPoints) listener.gameEnded(Player.ONE, onePoints, twoPoints);
            else listener.gameEnded(Player.TWO, twoPoints, onePoints);
        }
    }

    public enum Player {
        ONE,
        TWO
    }

    public interface Listener {
        void gameStarted(@NotNull Card trump);

        void turnOf(@NotNull Player player, int round);

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

        private PublicInfo(Card trump, Card[] table, Player[] tablePlayedBy, Player turnOf) {
            this.trump = trump;
            this.table = table;
            this.tablePlayedBy = tablePlayedBy;
            this.turnOf = turnOf;
        }

        public void play(@NotNull Card card) {
            playCard(turnOf, card);
        }

        public boolean playingFirst() {
            return table[0] == null;
        }

        @NotNull
        public Queue<Card> getUnusedCards(Player player) {
            Queue<Card> cards = new LinkedList<>(deck);
            Utils.addNotNullToList(getPlayer(player).hand, cards);
            return cards;
        }

        public final boolean isEnded() {
            return deck.isEmpty() && Utils.isEmpty(playerOne.hand) && Utils.isEmpty(playerTwo.hand);
        }

        @NotNull
        public PublicInfo copyForMinimax(Player player) {
            return Game.this.copyForMinimax(player).info;
        }

        public int getPoints(Player player) {
            return getPlayer(player).getPoints();
        }

        public Card[] getHand(Player player) {
            return getPlayer(player).hand;
        }
    }
}
