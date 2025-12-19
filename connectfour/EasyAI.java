package connectfour;

public class EasyAI implements AIPlayer {

    @Override
    public int chooseMove(Board board, char aiToken, char opponentToken) {
        int col;
        do {
            col = (int) (Math.random() * board.getCols());
        } while (board.isColumnFull(col));
        return col;
    }
}
