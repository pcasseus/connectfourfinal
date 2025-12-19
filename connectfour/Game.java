package connectfour;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * High-level game controller for a console-based Connect-Four session.
 *
 * Responsibilities:
 * <ul>
 * <li>Initialize game components (board, turn queue, undo stack)</li>
 * <li>Read and interpret user commands from standard input</li>
 * <li>Perform drops/undoes/restarts and detect end-of-game conditions</li>
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
    private PlayerManager playerManager;
    private Tournament currentTournament;

    private boolean vsAI = false;
    private char aiToken;
    private char humanToken;
    private char turnToken;
    private AIPlayer aiPlayer;

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
        this.playerManager = new PlayerManager();

        // Two players by default
        turnQueue.enqueue(new Player("Player 1", 'X'));
        turnQueue.enqueue(new Player("Player 2", 'O'));
    }

    /**
     * Print the interactive command help to standard output.
     */
    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("  [0-" + (board.getCols() - 1) + "]                       -> drop a piece in that column");
        System.out.println("  move <col>                   -> drop a piece in that column");
        System.out.println("  undo                         -> undo the last move");
        System.out.println("  board                        -> reprint the current board");
        System.out.println("  restart                      -> clear the board and start over");
        System.out.println("  help                         -> show this help menu");
        System.out.println("  quit                         -> exit the game");
        System.out.println();
        System.out.println("Player Accounts");
        System.out.println("  register <your name>         -> create player");
        System.out.println("  login <your name>            -> login as player");
        System.out.println("  logout                       -> log out current user");
        System.out.println("  whoami                       -> show current user");
        System.out.println("  profile <name>               -> show player stats");
        System.out.println("  leaderboard top <N>          -> show top N players");
        System.out.println("  hint                         -> show a suggested move");
        System.out.println();
        System.out.println("Game Modes Available");
        System.out.println("  game start human [X O]       -> start you vs another person");
        System.out.println("  game start ai <level>        -> start you vs AI (random|med|hard)");
        System.out.println();
        System.out.println("Tournament Commands");
        System.out
                .println("  tournament create <id> <p1>...      -> Create a tournament group (Ex: 't1 Patrick Dante')");
        System.out.println("  tournament start <id>               -> Finalize players and build the match schedule");
        System.out.println("  next                                -> Simulate the next match in the schedule");
        System.out.println("  tournament standings <id>           -> View the tournament leaderboard");
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
            try {
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
                String[] parts = line.split("\\s+");
                String cmd = parts[0].toLowerCase();

                if (cmd.matches("[0-6]")) {
                    handleDrop(Integer.parseInt(cmd));
                    continue;
                }

                if (cmd.equals("move") && parts.length == 2) {
                    try {
                        int col = Integer.parseInt(parts[1]);
                        handleDrop(col);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid column number.");
                    }
                    continue;
                }

                if (cmd.equals("register") && parts.length == 2) {
                    playerManager.register(parts[1]);

                } else if (cmd.equals("login") && parts.length == 2) {
                    String name = parts[1];
                    playerManager.login(name);

                } else if (cmd.equals("logout")) {
                    playerManager.logout();

                } else if (cmd.equals("whoami")) {
                    playerManager.whoami();

                } else if (cmd.equals("profile") && parts.length == 2) {
                    playerManager.profile(parts[1]);

                } else if (cmd.equals("leaderboard") && parts.length == 3 && parts[1].equals("top")) {
                    try {
                        int topN = Integer.parseInt(parts[2]);
                        playerManager.leaderboard(topN);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number for leaderboard.");
                    }

                } else if (cmd.equals("game") && parts.length >= 2 && parts[1].equals("start")) {
                    if (parts.length >= 3 && parts[2].equals("human")) {
                        vsAI = false;
                        turnQueue.clear();
                        String p1Name = "Player 1";
                        String p2Name = "Player 2";
                        if (parts.length >= 4)
                            p1Name = parts[3];
                        if (parts.length >= 5)
                            p2Name = parts[4];
                        turnQueue.enqueue(new Player(p1Name, 'X'));
                        turnQueue.enqueue(new Player(p2Name, 'O'));
                        restart();
                    } else if (parts.length >= 3 && parts[2].equals("ai")) {
                        String level = (parts.length >= 4) ? parts[3] : "med";
                        startAI(level, 'X');
                    } else {
                        System.out.println("Invalid game command.");
                    }

                } else if (cmd.equals("startai") && parts.length == 3) {
                    String level = parts[1].toLowerCase();
                    char token = Character.toUpperCase(parts[2].charAt(0));

                    if (token != 'X' && token != 'O') {
                        System.out.println("Token must be X or O.");
                    } else {
                        startAI(level, token);
                    }

                } else if (cmd.equals("tournament")) {
                    if (parts.length >= 4 && parts[1].equals("create")) {
                        String id = parts[2];
                        List<String> players = new ArrayList<>();
                        for (int i = 3; i < parts.length; i++) {
                            players.add(parts[i].replace(",", ""));
                        }
                        currentTournament = new Tournament(id, players);
                        System.out.println("Tournament " + id + " created.");
                    } else if (parts.length == 3 && parts[1].equals("start")) {
                        if (currentTournament != null && currentTournament.getId().equals(parts[2])) {
                            currentTournament.scheduleMatches();
                            System.out.println("A tournament is now scheduled.");
                        } else {
                            System.out.println("Either there's no tournament or ID mismatch.");
                        }
                    } else if (parts.length == 3 && parts[1].equals("standings")) {
                        if (currentTournament != null && currentTournament.getId().equals(parts[2])) {
                            currentTournament.printStandings();
                        } else {
                            System.out.println("There's no tournament.");
                        }
                    }

                } else if (cmd.equals("next")) {
                    if (currentTournament != null && currentTournament.hasNextMatch()) {
                        Match m = currentTournament.getNextMatch();
                        System.out.println("Simulating match: " + m);
                        String winner = simulateMatch(m);
                        System.out.println("Winner: " + winner);
                        String loser = winner.equals(m.player1) ? m.player2 : m.player1;
                        currentTournament.recordMatchResult(winner, loser);
                    } else {
                        System.out.println("Currently no scheduled matches.");
                    }

                } else if (cmd.equals("help")) {
                    printHelp();

                } else if (cmd.equals("board")) {
                    board.print();

                } else if (cmd.equals("restart")) {
                    restart();

                } else if (cmd.equals("undo")) {
                    handleUndo();

                } else if (cmd.equals("hint")) {
                    Player currentPlayer = turnQueue.peek();
                    turnQueue.rotate();
                    Player opponent = turnQueue.peek();
                    turnQueue.rotate();
                    new Hint(board, currentPlayer.token(), opponent.token()).print();

                } else if (cmd.equals("quit")) {
                    keepPlaying = false;
                    System.out.println("Goodbye!");

                } else {
                    System.out.println("Unknown command. Type 'help'.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }

    private String simulateMatch(Match m) {
        Board simBoard = new Board(ROWS, COLS, CONNECT);
        AIPlayer p1AI = new HardAI();
        AIPlayer p2AI = new HardAI();
        char p1Token = 'X';
        char p2Token = 'O';
        int maxMoves = ROWS * COLS;

        for (int i = 0; i < maxMoves; i++) {
            // Player 1
            int c1 = p1AI.chooseMove(simBoard, p1Token, p2Token);
            int r1 = simBoard.drop(c1, p1Token);
            if (simBoard.isWinningMove(r1, c1))
                return m.player1;
            if (simBoard.isFull())
                break;

            // Player 2
            int c2 = p2AI.chooseMove(simBoard, p2Token, p1Token);
            int r2 = simBoard.drop(c2, p2Token);
            if (simBoard.isWinningMove(r2, c2))
                return m.player2;
            if (simBoard.isFull())
                break;
        }
        return Math.random() < 0.5 ? m.player1 : m.player2;
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
            if (playerManager.getCurrentPlayer() != null) {
                if (vsAI) {
                    playerManager.getCurrentPlayer().recordResult(true, 1);
                } else {
                    playerManager.getCurrentPlayer().recordResult(false, 1);
                }
                playerManager.save();
            }
            keepPlaying = false;
        } else if (board.isFull()) {
            System.out.println("It's a draw");
            if (playerManager.getCurrentPlayer() != null) {
                playerManager.getCurrentPlayer().recordResult(vsAI, 0);
                playerManager.save();
            }
            keepPlaying = false;
            return;
        }

        if (vsAI && current.token() == humanToken && keepPlaying) {
            int aiMove = aiPlayer.chooseMove(board, aiToken, humanToken);
            int aiRow = board.drop(aiMove, aiToken);
            undoStack.push(new Move(aiRow, aiMove, aiToken));
            System.out.println("The AI moved at column " + aiMove);
            board.print();

            if (board.isWinningMove(aiRow, aiMove)) {
                System.out.println("The AI wins! The robots will take over.");
                if (playerManager.getCurrentPlayer() != null) {
                    playerManager.getCurrentPlayer().recordResult(true, -1);
                    playerManager.save();
                }
                keepPlaying = false;
            } else if (board.isFull()) {
                System.out.println("It's a draw. Very impressive!");
                if (playerManager.getCurrentPlayer() != null) {
                    playerManager.getCurrentPlayer().recordResult(true, 0);
                    playerManager.save();
                }
                keepPlaying = false;
            }
        } else if (keepPlaying) {
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

        if (vsAI && !undoStack.isEmpty()) {
            Move aiLast = undoStack.pop();
            board.undo(aiLast.row(), aiLast.col());
        }

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
        System.out.println("The board is now cleared. New game started!");
        board.print();
        keepPlaying = true;
    }

    public void startAI(String level, char playerToken) {
        vsAI = true;
        humanToken = playerToken;
        aiToken = (playerToken == 'X') ? 'O' : 'X';
        turnToken = 'X';
        turnQueue.clear();
        turnQueue.enqueue(new Player("You (Human)", humanToken));
        turnQueue.enqueue(new Player("AI", aiToken));
        restart();

        switch (level.toLowerCase()) {
            case "random":
                aiPlayer = new EasyAI();
                break;
            case "med":
                aiPlayer = new MediumAI();
                break;
            case "hard":
                aiPlayer = new HardAI();
                break;
            default:
                System.out.println("Unknown AI level: " + level);
                vsAI = false;
                return;
        }

        System.out.println("Starting game vs AI (" + level + ")");

        if (turnToken == aiToken) {
            int aiMove = aiPlayer.chooseMove(board, aiToken, humanToken);
            int aiRow = board.drop(aiMove, aiToken);
            undoStack.push(new Move(aiRow, aiMove, aiToken));
            System.out.println("THe AI moved at column " + aiMove);

            if (board.isWinningMove(aiRow, aiMove)) {
                System.out.println("The AI wins!");
                keepPlaying = false;
            } else if (board.isFull()) {
                System.out.println("It's a draw.");
                keepPlaying = false;
            }

            turnToken = humanToken;
        }
    }
}