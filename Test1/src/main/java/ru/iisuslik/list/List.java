package ru.iisuslik.list;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/**
 * Список для хэш таблицы
 */
public class List<K, V> {

    public class Iter implements Iterator<Entry<K, V>> {

        Node<K,V> now;
        public Iter() {
            now = head;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return now.next != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Entry<K, V> next() {
            return null;
        }
    }

    /***Вершина списка*/
    private static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> next = null;
        public Node<K, V> prev = null;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    public K getHeadKey() {
        if (head == null) {
            return null;
        }
        return head.key;
    }
    public V getHeadValue(){
        if (head == null) {
            return null;
        }
        return head.value;
    }

    /**Вершина начала списка*/
    private Node<K, V> head = null;

    /**Вершина конца списка*/
    private Node<K, V> tail = null;

    /**
     * Добавляет в список новый ключ, если такой ключ уже был, обновляет значение по ключу
     * @param key - ключ
     * @param value - значение по ключу
     * @return предыдущее значаение по ключу, если такой ключ уже был в списке
     */
    public V add(K key, V value) {
        Node<K, V> newNode = new Node<K, V>(key, value);
        Node<K, V> inside = find(key);
        if (inside != null) {
            V previousValue = inside.value;
            inside.value = value;
            return previousValue;
        }
        if (head == null) {
            head = tail = newNode;
            return null;
        }
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        return null;

    }

    /**
     * Ищет ключ в списке
     * @param key - ключ
     * @return null, если такого ключа нет, и вершину с этим ключом иначе
     */
    public V findKey(K key) {
        Node<K, V> found = find(key);
        if (found == null) {
            return null;
        }
        return found.value;
    }

    private Node<K, V> find(K key) {
        Node<K, V> go = head;
        while (go != null) {
            if (go.key.equals(key)) {
                return go;
            }
            go = go.next;
        }
        return null;
    }

    /**
     * Удаляет ключ из списка
     * @param key - ключ
     * @return null, если такого ключа не было, и значение по ключу иначе
     */
    public V delete(K key) {
        Node<K, V> inside = find(key);
        if (inside == null) {
            return null;
        }
        if (inside == head) {
            head = inside.next;
        }
        if (inside == tail) {
            tail = inside.prev;
        }
        if (inside.prev != null) {
            inside.prev.next = inside.next;
        }
        if (inside.next != null) {
            inside.next.prev = inside.prev;
        }
        return inside.value;
    }

    /** Удаляет все вершины из списка*/
    public void clear(){
        head = tail = null;
    }
}
