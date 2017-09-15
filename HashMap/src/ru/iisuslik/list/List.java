package ru.iisuslik.list;

/**
 * Список для хэш таблицы
 */
public class List{
    /**
     * Вершина списка
     */
    public static class Node{
        public String key, value;
        public Node next, prev;
        public Node(String key, String value){
            this.key = key;
            this.value = value;
            next = prev = null;
        }
    }
    public Node gethead(){return head;}
    public Node getTail(){return tail;}
    /**
     * Вершины начала и конца списка
     */
    public Node head, tail;

    /**
     * Конструктор списка
     */
    public List(){
        head = tail = null;
    }

    /**
     * Добавляет в список новый ключ, если такой ключ уже был, обновляет значение по ключу
     * @param key - ключ
     * @param value - значение по ключу
     * @return предыдущее значаение по ключу, если такой ключ уже был в списке
     */
    public String add(String key, String value){
        Node new_node = new Node(key, value);
        Node inside = find(key);
        if(inside != null){
            String previousValue = inside.value;
            inside.value = value;
            return previousValue;
        }
        if(head == null){
            head = tail = new_node;
            return null;
        }
        tail.next = new_node;
        new_node.prev = tail;
        tail = new_node;
        return null;

    }

    /**
     * Печатает весь список
     */
    public void print(){
        Node go = head;
        while(go != null){
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
    public String find_key(String key){
        Node found = find(key);
        if(found == null){
            return null;
        }
        return found.value;
    }
    private Node find(String key){
        Node go = head;
        while(go != null){
            if(go.key.equals(key)){
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
    public String delete(String key){
        Node inside = find(key);
        if(inside == null){
            return null;
        }
        if(inside == head){
            head = inside.next;
        }
        if(inside == tail){
            tail = inside.prev;
        }
        if(inside.prev != null){
            inside.prev.next = inside.next;
        }
        if(inside.next != null){
            inside.next.prev = inside.prev;
        }
        return inside.value;
    }

    /**
     * Удаляет все вершины из списка
     */
    public void clear(){
        head = tail = null;
    }
}
