package connectfour;

/**
 * Simple immutable representation of a player.
 *
 * Holds a display name and the single-character token used on the board.
 */
public record Player(String name, char token) {
    /**
     * Get the player's display name.
     *
     * @return player's name
     */
    public String name() {
        return name;
    }

    /**
     * Get the player's token character.
     *
     * @return single-character token
     */
    public char token() {
        return token;
    }
}
