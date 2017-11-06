package ru.iisuslik.set;


import org.jetbrains.annotations.NotNull;

/**
 * Realization of set with binary search tree with methods add, contains and size
 *
 * @param <T> Some type that can be compared with another one
 */
public class Set<T extends Comparable<? super T>> {

    private Node<T> head = null;

    private int size = 0;

    /**
     * Function to check that value contains in set
     *
     * @param value value you want to check if it contains in set
     * @return true if value contains and false else
     */
    public boolean contains(@NotNull T value) {
        Node<T> next = head;
        while (next != null) {
            int compareResult = next.value.compareTo(value);
            if (compareResult == 0) {
                return true;
            } else if (compareResult < 0) {
                next = next.r;
            } else {
                next = next.l;
            }
        }
        return false;
    }

    /**
     * Function to add some value in set
     *
     * @param value value you want to add
     * @return false if this value contained in set and true else
     */
    public boolean add(@NotNull T value) {
        if (contains(value)) {
            return false;
        }
        size++;
        if (head == null) {
            head = new Node<T>(value);
            return true;
        }
        Node<T> next = head;
        while (true) {
            int compareResult = next.value.compareTo(value);
            if (compareResult < 0) {
                if (next.r == null) {
                    next.r = new Node<>(value);
                    return true;
                }
                next = next.r;
            } else {
                if (next.l == null) {
                    next.l = new Node<>(value);
                    return true;
                }
                next = next.l;
            }
        }

    }

    /**
     * Function to get set's size
     *
     * @return count of values that was added in set
     */
    public int size() {
        return size;
    }

    private static class Node<T> {
        private Node<T> l = null;
        private Node<T> r = null;
        private T value;

        private Node(T value) {
            this.value = value;
        }
    }
}
