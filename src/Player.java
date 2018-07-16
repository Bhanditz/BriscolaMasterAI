import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gianlu
 */
public class Player {
    public final Card[] hand;
    public final List<Card> cardsWon;
    public final String name;

    public Player(String name) {
        this.name = name;
        this.hand = new Card[3];
        this.cardsWon = new ArrayList<>(20);
    }

    public int getPoints() {
        int points = 0;
        for (Card card : cardsWon) points += card.points;
        return points;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + Arrays.toString(hand) +
                '}';
    }
}
