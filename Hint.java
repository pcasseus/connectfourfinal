package connectfour;

public class Hint {
    private MyLinkedList<Integer> safeCols;
    private MyLinkedList<Integer> unsafeCols;
    private int recommendedCol = -1;

    public Hint(Board board, char currentPlayer, char opponent) {
        safeCols = new MyLinkedList<>();
        unsafeCols = new MyLinkedList<>();

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

    public MyLinkedList<Integer> getSafeCols() {
        return safeCols;
    }

    public MyLinkedList<Integer> getUnsafeCols() {
        return unsafeCols;
    }

    public int getRecommendedCol() {
        return recommendedCol;
    }

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
