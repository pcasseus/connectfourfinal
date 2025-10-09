package connectfour;

/**
 * Circular queue for Player objects with safe rotate (dequeue + enqueue).
 */
public class TurnQueue {
    private Player[] data;
    private int head = 0; // index of current front
    private int tail = 0; // index just after the last element
    private int size = 0; // number of stored players

    public TurnQueue(int capacity) {
        this.data = new Player[capacity];
    }

    /**
     * TODO: You need to implement the size, isEmpty, isFull methods.
     * The return values will need to be fixed.
     * 
     * @return
     */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == data.length;
    }

    public void enqueue(Player p) {
        if (isFull()) {
            System.out.println("The queue is full. Nothing can be queued.");
            return;
        }
        data[tail] = p;
        tail = (tail + 1) % data.length;
        size++;
    }

    /**
     * TODO: Uncomment and implement the peek and dequeue methods.
     */
    // public Player peek() {}
    public Player peek() {
        if (isEmpty()) {
            return null;
        }
        return data[head];
    }

    // public Player dequeue() {}
    public Player dequeue() {
        if (isEmpty()) {
            return null;
        }
        Player p = data[head];
        head = (head + 1) % data.length;
        size--;
        return p;
    }

    /** TODO: Implement the rotate and clear methods */
    public void rotate() {
        if (isEmpty()) {
            return;
        }
        Player p = dequeue();
        enqueue(p);
    }

    public void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }
}
