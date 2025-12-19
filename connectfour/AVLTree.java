package connectfour;

import java.util.*;

public class AVLTree<T extends Comparable<T>> {
    private class Node {
        T value;
        Node left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;

    public void insert(T value) {
        root = insert(root, value);
    }

    public void remove(T value) {
        root = remove(root, value);
    }

    public List<T> inOrder() {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private Node insert(Node node, T value) {
        if (node == null)
            return new Node(value);
        int cmp = value.compareTo(node.value);
        if (cmp < 0)
            node.left = insert(node.left, value);
        else if (cmp > 0)
            node.right = insert(node.right, value);
        else
            return node;
        return balance(node);
    }

    private Node remove(Node node, T value) {
        if (node == null)
            return null;
        int cmp = value.compareTo(node.value);
        if (cmp < 0)
            node.left = remove(node.left, value);
        else if (cmp > 0)
            node.right = remove(node.right, value);
        else {
            if (node.left == null || node.right == null) {
                return (node.left != null) ? node.left : node.right;
            }
            Node successor = minValueNode(node.right);
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }
        return balance(node);
    }

    private Node minValueNode(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private void inOrder(Node node, List<T> result) {
        if (node == null)
            return;
        inOrder(node.left, result);
        result.add(node.value);
        inOrder(node.right, result);
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private Node balance(Node node) {
        updateHeight(node);
        int balance = getBalance(node);
        if (balance > 1) {
            if (getBalance(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1) {
            if (getBalance(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }
}