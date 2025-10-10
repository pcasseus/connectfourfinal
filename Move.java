package connectfour;

/**
 * Immutable data carrier representing a single move on the board.
 *
 * Instances hold the landed row and column as well as the token character
 * that was placed.
 *
 * @param row   row index where the token landed
 * @param col   column index where the token was dropped
 * @param token the player's token character
 */
public record Move(int row, int col, char token) {
    /**
     * Convenience constructor used when token is not specified.
     *
     * @param row landing row
     * @param col landing column
     */
    public Move(int row, int col) {
        this(row, col, ' ');
    }
}
