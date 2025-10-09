package connectfour;

/**
 * Immutable player descriptor with a display name and a single-character token.
 */
public record Player(String name, char token) {
    public String name() { return name; }
    public char token() { return token; }
}
