import com.sun.istack.internal.NotNull;

/**
 * @author Gianlu
 */
public enum Suit {
    COINS("denari"),
    SWORDS("spade"),
    CUPS("coppe"),
    BATONS("bastoni");

    private final String name;

    Suit(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
