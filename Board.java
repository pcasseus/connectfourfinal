package connectfour;

/**
 * Represents the Connect-Four game board.
 * <p>
 * This class stores the grid of tokens and provides operations to drop and
 * undo moves, inspect board status (full/column full), and check for
 * winning moves (connect-N). The board also offers utility accessors and a
 * simple ASCII print for console display.
 * </p>
 */
public class Board {
    private int rows;
    private int cols;
    private int connect; // number in a row needed to win
    private char[][] grid;

    /**
     * Create a new Board with the given dimensions and required connect length.
     *
     * @param rows    number of rows (height)
     * @param cols    number of columns (width)
     * @param connect number of aligned tokens required to win
     */
    public Board(int rows, int cols, int connect) {
        this.rows = rows;
        this.cols = cols;
        this.connect = connect;
        this.grid = new char[rows][cols];
        clear();
    }

    /**
     * Clear the board, setting every cell to an empty space character.
     * This resets the board state for a new game.
     */
    public void clear() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = ' ';
            }
        }
    }

    /**
     * Returns the number of rows in this board.
     *
     * @return row count
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in this board.
     *
     * @return column count
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns the number of tokens in a row required to win (connect-N).
     *
     * @return connect length
     */
    public int getConnect() {
        return connect;
    }

    /**
     * Check whether the specified column is full (no empty cells available to
     * drop a token).
     *
     * @param col column index to check
     * @return true if the top cell in the column is occupied; false otherwise
     */
    public boolean isColumnFull(int col) {
        return grid[0][col] != ' ';
    }

    /**
     * Drop a token into the provided column. The token "falls" to the lowest
     * available row in that column.
     *
     * @param col   column index where the token is dropped (0-based)
     * @param token the character token to place (for example 'X' or 'O')
     * @return the row index where the token landed or -1 if the column is full
     */
    public int drop(int col, char token) {
        for (int r = rows - 1; r >= 0; r--) {
            if (grid[r][col] == ' ') {
                grid[r][col] = token;
                return r;
            }
        }
        return -1; // column full
    }

    /**
     * Undo the move at the specified coordinates by clearing that cell.
     *
     * @param row row index of the cell to clear
     * @param col column index of the cell to clear
     */
    public void undo(int row, int col) {
        grid[row][col] = ' ';
    }

    /**
     * Check whether the board is completely full (no empty spaces remain).
     *
     * @return true when every cell contains a token; false otherwise
     */
    public boolean isFull() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determine whether the move placed at the given coordinates results in a win
     * for the token currently at that position. The method checks horizontal,
     * vertical and both diagonal directions for a connect-N sequence.
     *
     * @param row row index of the last move
     * @param col column index of the last move
     * @return true if that move produced a connect-N; false otherwise
     */
    public boolean isWinningMove(int row, int col) {
        char token = grid[row][col];

        int[][] directions = {
                { 0, 1 },
                { 1, 0 },
                { 1, 1 },
                { 1, -1 }
        };

        for (int[] dir : directions) {
            int count = countDirection(row, col, dir[0], dir[1], token)
                    + countDirection(row, col, -dir[0], -dir[1], token) - 1;
            if (count >= connect) {
                return true;
            }
        }
        return false;
    }

    /**
     * Explanation of the winning-check algorithm:
     *
     * - We consider four direction vectors that represent unique lines through
     *   a cell: horizontal (0,1), vertical (1,0), diagonal down-right (1,1)
     *   and diagonal down-left (1,-1). For each direction we count how many
     *   consecutive matching tokens exist starting at the last-placed cell in
     *   both the forward and backward direction. The counts from both sides
     *   include the origin cell, so we subtract 1 to avoid double-counting.
     *
     * - The helper {@link #countDirection(int,int,int,int,char)} recursively
     *   walks in a straight line while the grid contains the same token. The
     *   recursion ends when it reaches out-of-bounds or a different token.
     */

    /**
     * Recursively count contiguous tokens matching {@code token} starting at
     * (r,c) and following the direction (dr,dc). Used by {@link
     * #isWinningMove(int,int)}.
     *
     * @param r     starting row
     * @param c     starting column
     * @param dr    row delta per step
     * @param dc    column delta per step
     * @param token token to match
     * @return number of matching tokens in the given direction
     */
    private int countDirection(int r, int c, int dr, int dc, char token) {
        if (r < 0 || r >= rows || c < 0 || c >= cols)
            return 0;
        if (grid[r][c] != token)
            return 0;
        return 1 + countDirection(r + dr, c + dc, dr, dc, token);
    }

    /**
     * Print the current board to standard output in a simple ASCII layout.
     * Column indices are shown above the grid and row indices to the left.
     */
    public void print() {
        System.out.println(" ");
        for (int c = 0; c < cols; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        System.out.println(" ");
        for (int c = 0; c < cols; c++) {
            System.out.println("--");
        }
        System.out.println();

        for (int r = rows - 1; r >= 0; r--) {
            System.out.printf("%2d ", r);
            for (int c = 0; c < cols; c++) {
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
