package connectfour;

/**
 * Simple fixed-capacity LIFO stack for {@link Move} objects.
 *
 * Implemented with an array and an integer top index.
 */
public class MoveStack {
    private Move[] data;
    private int top = -1;

    /**
     * Create a new MoveStack with the provided capacity.
     *
     * @param capacity maximum number of moves that can be stored
     */
    public MoveStack(int capacity) {
        this.data = new Move[capacity];
    }

    /**
     * Push a move onto the stack. If the stack is full, the move is not
     * stored and a message is printed.
     *
     * @param m move to push
     */
    public void push(Move m) {
        if (top + 1 < data.length) {
            top++;
            data[top] = m;
        } else {
            System.out.println("The stack is full & pushing is not possible.");
        }
    }

    /**
     * Pop and return the most recently pushed move. If the stack is empty
     * null is returned and a message is printed.
     *
     * @return the popped Move, or null if the stack was empty
     */
    public Move pop() {
        if (isEmpty()) {
            System.out.println("The stack is empty & there's nothing to pop");
            return null;
        }
        Move m = data[top];
        top--;
        return m;
    }

    /**
     * Check whether the stack contains no moves.
     *
     * @return true when empty
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Remove all entries from the stack.
     */
    public void clear() {
        top = -1;
    }

    /**
     * Return the number of stored moves.
     *
     * @return current size of the stack
     */
    public int size() {
        return top + 1;
    }
}
