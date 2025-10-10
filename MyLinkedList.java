package connectfour;

/**
 * Minimal singly-linked list implementation used by the hint engine.
 *
 * This class provides basic operations: add, contains, get by index, size,
 * clear and a simple text representation.
 */
public class MyLinkedList<T> {
    private Node<T> head;
    private int size = 0;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    /**
     * Append a value to the end of the list.
     *
     * @param value value to add
     */
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
        size++;
    }

    /**
     * Check whether the list contains the provided value.
     *
     * @param value value to search for
     * @return true if found
     */
    public boolean contains(T value) {
        Node<T> curr = head;
        while (curr != null) {
            if (curr.data.equals(value))
                return true;
            curr = curr.next;
        }
        return false;
    }

    /**
     * Return the number of elements in the list.
     *
     * @return list size
     */
    public int size() {
        return size;
    }

    /**
     * Get element at the specified index (0-based).
     *
     * @param index position to retrieve
     * @return element at index
     * @throws IndexOutOfBoundsException when index is invalid
     */
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.data;
    }

    /**
     * Clear the list and reset its size to zero.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Return a simple string representation of the list contents.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null)
                sb.append(", ");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
