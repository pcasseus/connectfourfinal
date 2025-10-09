package connectfour;

/**
 * Board holds the 2D grid state and board-level operations.
 * It provides drop/undo, win/draw checks, and ASCII printing.
 */
public class Board {
    private int rows;
    private int cols;
    private int connect; // number in a row needed to win
    private char[][] grid;

    /**
     * TODO: Initialize the board with the specified dimensions and connect-N.
     * 
     * @param rows
     * @param cols
     * @param connect
     */
    public Board(int rows, int cols, int connect) {
        this.rows = rows;
        this.cols = cols;
        this.connect = connect;
        this.grid = new char[rows][cols];
        clear();
    }

    /** TODO: Clear the board to all spaces. */
    public void clear() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = ' ';
            }
        }
    }

    /** TODO: You will need to fix the return values of these methods */
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getConnect() {
        return connect;
    }

    /**
     * TODO: You need to implement the logic to see if a column is full
     * 
     * @return true if the specified column is full (i.e., top cell is not empty).
     */
    public boolean isColumnFull(int col) {
        return grid[0][col] != ' ';
    }

    /**
     * Drop a token into a column (falls to the lowest empty cell).
     * 
     * @return row index where the token landed, or -1 if the column is full.
     * 
     *         TODO: finish the for loop
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

    /** TODO: Undo a move at (row, col) by clearing the cell. */
    public void undo(int row, int col) {
        grid[row][col] = ' ';
    }

    /**
     * TODO: You will need to implement this function. @return true if the board has
     * no empty cells left.
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
     * Check if the last move at (row, col) created a connect-N in any direction.
     * It counts in four direction pairs and subtracts 1 to avoid double counting
     * the origin.
     * TODO: check if the last move is a winning move
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

    // TODO: recursion to count in a direction
    private int countDirection(int r, int c, int dr, int dc, char token) {
        if (r < 0 || r >= rows || c < 0 || c >= cols)
            return 0;
        if (grid[r][c] != token)
            return 0;
        return 1 + countDirection(r + dr, c + dc, dr, dc, token);
    }

    /** TODO: Print the board in ASCII with row/column headers. */
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
