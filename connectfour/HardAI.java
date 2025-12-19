package connectfour;

public class HardAI implements AIPlayer {

    private static final int DEPTH = 5;

    @Override
    public int chooseMove(Board board, char aiToken, char opponentToken) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = 0;

        for (int c = 0; c < board.getCols(); c++) {
            if (!board.isColumnFull(c)) {
                Board temp = new Board(board);
                int r = temp.drop(c, aiToken);
                int score = minimax(temp, DEPTH - 1, false, aiToken, opponentToken);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = c;
                }
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, boolean max,
            char ai, char opp) {

        if (depth == 0 || board.isFull())
            return 0;

        if (max) {
            int best = Integer.MIN_VALUE;
            for (int c = 0; c < board.getCols(); c++) {
                if (!board.isColumnFull(c)) {
                    Board temp = new Board(board);
                    int r = temp.drop(c, ai);
                    if (temp.isWinningMove(r, c))
                        return 1000;
                    best = Math.max(best,
                            minimax(temp, depth - 1, false, ai, opp));
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int c = 0; c < board.getCols(); c++) {
                if (!board.isColumnFull(c)) {
                    Board temp = new Board(board);
                    int r = temp.drop(c, opp);
                    if (temp.isWinningMove(r, c))
                        return -1000;
                    best = Math.min(best,
                            minimax(temp, depth - 1, true, ai, opp));
                }
            }
            return best;
        }
    }
}
