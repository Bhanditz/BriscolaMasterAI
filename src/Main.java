import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Gianlu
 */
public class Main implements Game.Listener {
    private final Scanner sc = new Scanner(System.in);
    private final Game game;

    public Main() {
        game = new Game(this);
        game.start();
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void gameStarted(@NotNull Card trump) {
        System.out.println("GAME STARTED!!");
        System.out.println("TRUMP: " + trump);
        System.out.println("---------------------------");
    }

    @Override
    public void turnOf(@NotNull Player player) {
        System.out.println("---------------------------");
        System.out.println("Turn of " + player);
        System.out.println("Current table is: " + Arrays.toString(game.table));
        System.out.print("Index of card to play: ");
        if (sc.hasNextLine()) {
            int index = Integer.parseInt(sc.nextLine());
            game.playCard(player, player.hand[index]);
        } else {
            throw new IllegalStateException("You must play a card!!");
        }
    }

    @Override
    public void playerWonRound(@NotNull Player player) {
        System.out.println("---------------------------");
        System.out.println("ROUND WINNER: " + player);
    }

    @Override
    public void gameEnded(@Nullable Player winner, int winnerPoints, int otherPoints) {
        System.out.println("---------------------------");
        System.out.println("GAME ENDED!!");
        System.out.println("WINNER: " + winner);
        System.out.println("POINTS: " + winnerPoints + " - " + otherPoints);
        System.out.println("---------------------------");
        System.exit(0);
    }
}
