package connectfour;

public class MediumAI implements AIPlayer {

    @Override
    public int chooseMove(Board board, char aiToken, char opponentToken) {

        for (int c = 0; c < board.getCols(); c++) {
            if (!board.isColumnFull(c)) {
                Board temp = new Board(board);
                int r = temp.drop(c, aiToken);
                if (temp.isWinningMove(r, c))
                    return c;
            }
        }

        for (int c = 0; c < board.getCols(); c++) {
            if (!board.isColumnFull(c)) {
                Board temp = new Board(board);
                int r = temp.drop(c, opponentToken);
                if (temp.isWinningMove(r, c))
                    return c;
            }
        }

        return new EasyAI().chooseMove(board, aiToken, opponentToken);
    }
}
