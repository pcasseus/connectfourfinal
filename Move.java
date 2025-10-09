package connectfour;

/**
 * Immutable record describing a single move.
 */
public record Move(int row, int col, char token) {
    public Move(int row, int col) {
        this(row, col, " ");
    }
}
