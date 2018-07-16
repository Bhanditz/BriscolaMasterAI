import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gianlu
 */
public class Card {
    public final int value;
    public final int points;
    public final Suit suit;
    private final String name;

    private Card(@NotNull Mapped mapped, @NotNull Suit suit) {
        this.name = mapped.name;
        this.value = mapped.value;
        this.points = mapped.points;
        this.suit = suit;
    }

    @NotNull
    public static List<Card> getOneSuit(@NotNull Suit suit) {
        List<Card> cards = new ArrayList<>();
        for (Mapped mapped : Mapped.values()) cards.add(new Card(mapped, suit));
        return cards;
    }

    @NotNull
    public String getName() {
        return name + " " + suit.getName();
    }

    @Override
    public String toString() {
        return "Card{" + getName() + '}';
    }

    @SuppressWarnings("unused")
    private enum Mapped {
        ACE("Asso", 9, 11),
        THREE("Tre", 8, 10),
        KING("Re", 7, 4),
        KNIGHT("Cavallo", 6, 3),
        JACK("Fante", 5, 2),
        SEVEN("Sette", 4, 0),
        SIX("Sei", 3, 0),
        FIVE("Cinque", 2, 0),
        FOUR("Quattro", 1, 0),
        TWO("Due", 0, 0);

        private final String name;
        private final int value;
        private final int points;

        Mapped(String name, int value, int points) {
            this.name = name;
            this.value = value;
            this.points = points;
        }
    }
}
