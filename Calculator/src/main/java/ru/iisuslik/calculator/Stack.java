package ru.iisuslik.calculator;

/**
 * Realization of stack that uses list
 *
 * @param <T> type of containing values
 */
public class Stack<T>{
    private Node<T> head, tail;

    /**
     * After this stack contains nothing
     */
    public void clear() {
        head = tail = null;
    }

    private int size = 0;

    /**
     * get count of values in stack
     *
     * @return stack size
     */
    public int size() {
        return size;
    }

    /**
     * Add new value to stack
     *
     * @param value value to add
     */
    public void push(T value) {
        size++;
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
            return;
        }
        newNode.prev = tail;
        tail = newNode;
    }

    /** Remove first element from stack
     * @return removed element or null if stack was empty
     */
    public T pop() {
        if(size == 0)
            return null;
        T result = tail.value;
        if(tail == head)
            tail = head = null;
        else
            tail = tail.prev;
        size--;
        return result;
    }

    /**
     * get first element in stack
     * @return first element
     */
    public T top() {
        if(size == 0)
            return null;
        return tail.value;
    }

    private static class Node<T> {
        private Node<T> prev = null;
        private T value;

        private Node(T value) {
            this.value = value;
        }
    }
}
