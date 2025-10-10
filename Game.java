package connectfour;

import java.util.Scanner;

/**
 * High-level game controller for a console-based Connect-Four session.
 *
 * Responsibilities:
 * <ul>
 *   <li>Initialize game components (board, turn queue, undo stack)</li>
 *   <li>Read and interpret user commands from standard input</li>
 *   <li>Perform drops/undoes/restarts and detect end-of-game conditions</li>
 * </ul>
 */
public class Game {

    private static int ROWS = 6;
    private static int COLS = 7;
    private static int CONNECT = 4;

    private Board board;
    private MoveStack undoStack;
    private TurnQueue turnQueue;
    private Scanner scanner;

    private boolean keepPlaying = true;

    public Game() {
        /**
         * TODO: Initialize the board, undoStack, turnQueue with the correct
         * parameters/values
         */
        this.board = new Board(ROWS, COLS, CONNECT);
        this.undoStack = new MoveStack(ROWS * COLS);
        this.turnQueue = new TurnQueue(2); // capacity for up to 4 players
        this.scanner = new Scanner(System.in);

        // Two players by default
        turnQueue.enqueue(new Player("Player 1", 'X'));
        turnQueue.enqueue(new Player("Player 2", 'O'));
    }

    /**
     * Print the interactive command help to standard output.
     */
    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("  [0-" + (board.getCols() - 1) + "]  -> drop a piece in that column");
        System.out.println("  undo        -> undo the last move");
        System.out.println("  board       -> reprint the current board");
        System.out.println("  restart     -> clear the board and start over");
        System.out.println("  help        -> show this help menu");
        System.out.println("  quit        -> exit the game");
        System.out.println();
    }

    /**
     * Start the main game loop which listens for user input and processes
     * commands until the user quits or a game-ending condition occurs.
     */
    public void run() {
        System.out.println("=== Connect-Four Mini (Terminal) ===");
        System.out.println("Goal: connect " + board.getConnect() + " in a row.");
        System.out.println();
        printHelp();
        board.print();

        while (keepPlaying) {
            /**
             * TODO:
             * Uncomment these lines when the functions have been implemented
             */
            // Player current = turnQueue.peek();
            // System.out.print(current.name() + " (" + current.token() + "), enter command:
            // ");
            Player current = turnQueue.peek();
            System.out.println();
            System.out.println("---------------------------------");
            System.out.print(current.name() + " (" + current.token() + ") > ");

            if (!scanner.hasNextLine())
                break;
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("quit")) {
                keepPlaying = false;
                System.out.println("Goodbye!");
            } else if (line.equalsIgnoreCase("help")) {
                printHelp();
            } else if (line.equalsIgnoreCase("board")) {
                board.print();
            } else if (line.equalsIgnoreCase("restart")) {
                restart();
            } else if (line.equalsIgnoreCase("undo")) {
                handleUndo();
            } else if (line.equalsIgnoreCase("hint")) {
                Player currentPlayer = turnQueue.peek();

                turnQueue.rotate();
                Player opponent = turnQueue.peek();
                turnQueue.rotate();
                Hint hint = new Hint(board, currentPlayer.token(), opponent.token());
                hint.print();
            } else {

                /**
                 * TODO: Try to parse the input as a column number and drop a piece there.
                 * Also handle implement error handling
                 */
                try {
                    int col = Integer.parseInt(line);

                    if (col < 0 || col >= board.getCols()) {
                        System.out.println("The column is out of range");
                        continue;
                    }

                    if (board.isColumnFull(col)) {
                        System.out.println("The column is full");
                        continue;
                    }

                    int row = board.drop(col, current.token());
                    undoStack.push(new Move(row, col, current.token()));
                    board.print();

                    if (board.isWinningMove(row, col)) {
                        System.out.println(current.name() + " wins!");
                        keepPlaying = false;
                    } else if (board.isFull()) {
                        System.out.println("We have a draw.");
                        keepPlaying = false;
                    } else {
                        turnQueue.rotate();
                    }

                } catch (NumberFormatException nfe) {
                    System.out.println("Invalid command. Type a column number, or try 'help'.");
                }
            }
        }
    }

    /**
     * Handle a drop action for the current player into the specified
     * column. Validates the column and updates the board, undo stack and
     * turn queue accordingly. If the move ends the game it will set
     * {@link #keepPlaying} to false.
     *
     * @param col target column index for the drop
     */
    private void handleDrop(int col) {
        if (col < 0 || col >= board.getCols()) {
            System.out.println("The column is out of range.");
            return;
        }

        if (board.isColumnFull(col)) {
            System.out.println("The column is full.");
            return;
        }

        Player current = turnQueue.peek();
        int row = board.drop(col, current.token());
        undoStack.push(new Move(row, col, current.token()));
        board.print();

        if (board.isWinningMove(row, col)) {
            System.out.println(current.name() + " wins!");
            keepPlaying = false;
        } else if (board.isFull()) {
            System.out.println("It's a draw");
            keepPlaying = false;
        } else {
            turnQueue.rotate();
        }
    }

    /**
     * Undo the last move by popping the undo stack, clearing the board
     * cell and rotating the turn queue so the undone player may play again.
     */
    private void handleUndo() {
        if (undoStack.isEmpty()) {
            System.out.println("There's nothing to undo.");
            return;
        }

        Move last = undoStack.pop();
        board.undo(last.row(), last.col());
        turnQueue.rotate();
        board.print();
    }

    /**
     * Restart the currently running game by clearing the board and undo
     * history. Players remain in the turn queue and play resumes from the
     * current player.
     */

    private void restart() {
        board.clear();
        undoStack.clear();
        System.out.println("The board is now cleared. New game Started!");
        board.print();
    }
}
