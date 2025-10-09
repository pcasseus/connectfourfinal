package connectfour;

/**
 * TODO:
 * Array backed stack for Move objects (LIFO). Implement from scratch
 */
public class MoveStack {
    private Move[] data;
    private int top = -1;

    public MoveStack(int capacity) {
        this.data = new Move[capacity];
    }

    /**
     * TODO: Implement the pushing of the stack
     * 
     * @param m
     */
    public void push(Move m) {
        if (top + 1 < data.length) {
            top++;
            data[top] = m;
        } else {
            System.out.println("The stack is full & pushing is not possible.");
        }
    }

    /** TODO: Uncomment this method and implement the popping of the stack */
    // public Move pop() {
    // return m;
    // }
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
     * TODO: You need to implement the isEmpty method
     * 
     * @return
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * TODO: Implement the clear method
     */
    public void clear() {
        top = -1;
    }

    /**
     * TODO: You need to implement the size method
     * 
     * @return
     */
    public int size() {
        return top + 1;
    }
}
