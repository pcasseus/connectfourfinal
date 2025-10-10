package connectfour;

/**
 * Provides a simple hint engine for the current board state.
 *
 * The Hint object inspects each legal drop for the current player and
 * simulates whether the opponent would have a winning reply. Columns are
 * classified as safe or unsafe and a recommended column may be provided.
 */
public class Hint {
    private MyLinkedList<Integer> safeCols;
    private MyLinkedList<Integer> unsafeCols;
    private int recommendedCol = -1;

    /**
     * Analyze the board and compute safe/unsafe column suggestions.
     *
     * @param board         the board to analyze (will be temporarily modified
     *                      during simulation but restored)
     * @param currentPlayer token representing the current player
     * @param opponent      token representing the opponent
     */
    public Hint(Board board, char currentPlayer, char opponent) {
        safeCols = new MyLinkedList<>();
        unsafeCols = new MyLinkedList<>();

        /*
         * Explanation of hint simulation:
         *
         * For each non-full column we simulate dropping the current player's
         * token and then check whether the opponent has any immediate winning
         * reply. If the opponent can win on their subsequent move the
         * original column is classified as "unsafe". Otherwise it is
         * classified as "safe" and may be recommended.
         *
         * Implementation details:
         * - The method temporarily modifies the provided Board by calling
         *   drop(...) and must undo every simulated drop with undo(...)
         *   to restore the original board state for the next simulation.
         * - When simulating the opponent's replies we iterate over all
         *   columns and simulate a drop for the opponent. If any single
         *   reply results in a win for the opponent we mark the original
         *   column as unsafe and stop further checks for that column.
         * - This is a shallow two-ply simulation: it detects immediate
         *   opponent wins but does not perform deeper minimax search. That
         *   keeps complexity manageable while avoiding obvious blunders.
         */

        for (int col = 0; col < board.getCols(); col++) {
            if (board.isColumnFull(col)) {
                continue;
            }

            int row = board.drop(col, currentPlayer);
            boolean opponentCanWin = false;

            for (int oppCol = 0; oppCol < board.getCols(); oppCol++) {
                if (board.isColumnFull(oppCol))
                    continue;

                int oppRow = board.drop(oppCol, opponent);
                if (board.isWinningMove(oppRow, oppCol)) {
                    opponentCanWin = true;
                }
                board.undo(oppRow, oppCol);
                if (opponentCanWin)
                    break;
            }

            if (opponentCanWin) {
                unsafeCols.add(col);
            } else {
                safeCols.add(col);
                if (recommendedCol == -1) {
                    recommendedCol = col;
                }
            }

            board.undo(row, col);
        }
    }

    /**
     * @return list of safe column indices
     */
    public MyLinkedList<Integer> getSafeCols() {
        return safeCols;
    }

    /**
     * @return list of unsafe column indices
     */
    public MyLinkedList<Integer> getUnsafeCols() {
        return unsafeCols;
    }

    /**
     * @return recommended column index or -1 if none
     */
    public int getRecommendedCol() {
        return recommendedCol;
    }

    /**
     * Print a textual summary of the hint suggestions to standard output.
     */
    public void print() {
        if (recommendedCol != -1) {
            System.out.println("Hint: Try column " + recommendedCol + ".");
        } else {
            System.out.println("Hint: No safe moves. Choose carefully!");
        }

        System.out.println("Safe moves: " + safeCols);
        System.out.println("Unsafe moves: " + unsafeCols);
    }
}
