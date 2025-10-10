package connectfour;

/**
 * Simple circular queue used to manage player turns.
 *
 * Players are enqueued once during game setup and then rotated so the
 * {@link #peek()} method returns the current player.
 */
public class TurnQueue {
    private Player[] data;
    private int head = 0; // index of current front
    private int tail = 0; // index just after the last element
    private int size = 0; // number of stored players

    /**
     * Create a turn queue with the given capacity.
     *
     * @param capacity maximum number of players supported
     */
    public TurnQueue(int capacity) {
        this.data = new Player[capacity];
    }

    /**
     * Return the number of players currently in the queue.
     *
     * @return queue size
     */
    public int size() {
        return size;
    }

    /**
     * Check whether the queue contains no players.
     *
     * @return true when empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check whether the queue has reached its configured capacity.
     *
     * @return true when full
     */
    public boolean isFull() {
        return size == data.length;
    }

    /**
     * Enqueue a player at the tail of the queue. If the queue is full a
     * message is printed and the player is not added.
     *
     * @param p player to add
     */
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
     * Return the player at the head of the queue without removing them.
     *
     * @return current player or null when the queue is empty
     */
    public Player peek() {
        if (isEmpty()) {
            return null;
        }
        return data[head];
    }

    /**
     * Remove and return the player at the head of the queue.
     *
     * @return dequeued player or null when empty
     */
    public Player dequeue() {
        if (isEmpty()) {
            return null;
        }
        Player p = data[head];
        head = (head + 1) % data.length;
        size--;
        return p;
    }

    /**
     * Rotate the queue by dequeuing the head player and enqueuing them at
     * the tail. If the queue is empty this is a no-op.
     */
    public void rotate() {
        if (isEmpty()) {
            return;
        }
        Player p = dequeue();
        enqueue(p);
    }

    /**
     * Explanation of rotation semantics:
     *
     * - The queue models player turn order where {@link #peek()} returns the
     *   player whose turn it currently is. After the current player completes
     *   their move the queue should advance so the next player becomes the
     *   current one. The simplest and side-effect free way to implement this
     *   is to dequeue the head element and immediately enqueue it at the
     *   tail. This preserves the relative order of players while advancing
     *   the head pointer by one.
     */

    /**
     * Clear the queue and reset internal indices.
     */
    public void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }
}
