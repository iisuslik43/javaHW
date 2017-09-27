package ru.iisuslik.list;

/**
 * Список для хэш таблицы
 */
public class List {

    /***Вершина списка*/
    private static class Node {
        public String key, value;
        public Node next = null;
        public Node prev = null;
        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


    public String getHeadKey() {
        if (head == null) {
            return null;
        }
        return head.key;
    }
    public String getHeadValue(){
        if (head == null) {
            return null;
        }
        return head.value;
    }

    /**Вершина начала списка*/
    private Node head = null;

    /**Вершина конца списка*/
    private Node tail = null;

    /**
     * Добавляет в список новый ключ, если такой ключ уже был, обновляет значение по ключу
     * @param key - ключ
     * @param value - значение по ключу
     * @return предыдущее значаение по ключу, если такой ключ уже был в списке
     */
    public String add(String key, String value) {
        Node newNode = new Node(key, value);
        Node inside = find(key);
        if (inside != null) {
            String previousValue = inside.value;
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

    /**Печатает весь список*/
    public void print() {
        Node go = head;
        while (go != null) {
            System.out.printf("%s: %s\n", go.key, go.value);
            go = go.next;
        }
        System.out.println("---------");

    }

    /**
     * Ищет ключ в списке
     * @param key - ключ
     * @return null, если такого ключа нет, и вершину с этим ключом иначе
     */
    public String findKey(String key) {
        Node found = find(key);
        if (found == null) {
            return null;
        }
        return found.value;
    }

    private Node find(String key) {
        Node go = head;
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
    public String delete(String key) {
        Node inside = find(key);
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
