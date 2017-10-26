package ru.iisuslik.trie;

import java.io.*;

/**
 * Trie can store any strings and find count of strings that begin with prefix.
 * Trie implements Serializable so it can be written in file and read from file.
 */
public class Trie implements Serializable {
    /**
     * Checks if string contains in Trie
     * O(n), where n is string size
     *
     * @param element some string tp check
     * @return true if string contains in Trie, false else
     */
    public boolean contains(String element) {
        if (head == null) {
            return false;
        }
        Node now = head;
        for (char c : element.toCharArray()) {
            if (now.nextNodes[c] == null) {
                return false;
            }
            now = now.nextNodes[c];
        }
        return now.theEnd;
    }

    /**
     * Converts Trie to String, it is used in tests
     * O(Trie.size())
     */
    @Override
    public String toString() {
        if (head == null) {
            return "empty";
        }
        StringBuilder buf = new StringBuilder();
        toStr(head, ' ', buf);
        return buf.toString();
    }

    private void toStr(Node now, char c, StringBuilder buf) {
        buf.append(" (|");
        buf.append(c);
        buf.append("|, ");
        buf.append(now.wordsCount);
        buf.append(", ");
        buf.append(now.theEnd);
        for (int i = 0; i < now.nextNodes.length; i++) {
            if (now.nextNodes[i] != null) {
                toStr(now.nextNodes[i], (char) i, buf);
            }
        }
        buf.append(") ");
    }


    /**
     * Adds new string to Trie
     * O(n), where n is string size
     *
     * @param element string you want to add
     * @return false if string was in Trie, true else
     */
    public boolean add(String element) {
        if (contains(element)) {
            return false;
        }
        if (head == null) {
            head = new Node();
        }
        Node now = head;
        for (char c : element.toCharArray()) {
            now.wordsCount++;
            if (now.nextNodes[c] == null) {
                now.nextNodes[c] = new Node();
            }
            now = now.nextNodes[c];
        }
        now.wordsCount++;
        now.theEnd = true;
        return true;
    }

    /**
     * Deletes string from Trie if it's possible
     * O(n), where n is string size
     *
     * @param element string you want to delete
     * @return false if this string doesn't contains in Trie, true else
     */
    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }
        Node now = head;
        for (char c : element.toCharArray()) {
            now.wordsCount--;
            if (now.nextNodes[c].wordsCount == 1) {
                now.nextNodes[c] = null;
                return true;
            }
            now = now.nextNodes[c];
        }
        now.wordsCount--;
        now.theEnd = false;
        return true;
    }


    /**
     * Returns how many strings contains in Trie
     * O(1)
     *
     * @return count of strings in Trie
     */
    public int size() {
        if (head == null) {
            return 0;
        }
        return head.wordsCount;
    }


    /**
     * Returns how many strings begin with prefix
     * O(n), where n is prefix size
     *
     * @param prefix you want to check
     * @return count of strings, or 0, if this prefix doesn't contains in Trie
     */
    public int howManyStartsWithPrefix(String prefix) {
        if (head == null) {
            return 0;
        }
        Node now = head;
        for (char c : prefix.toCharArray()) {
            if (now.nextNodes[c] == null) {
                return 0;
            }
            now = now.nextNodes[c];
        }
        return now.wordsCount;
    }


    /**
     * Serializes function from interface Serializable
     *
     * @param out output stream, where you want to write Trie
     * @throws IOException it throws if there are some problem with output stream
     */
    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream myOut = new ObjectOutputStream(out);
        myOut.writeObject(this);
        myOut.flush();
        myOut.close();
    }

    /**
     * deserialize function from interface Serializable
     *
     * @param in input stream. From this stream Trie will be read
     * @throws IOException            if there are some problem with input stream (file doesn't exists e.t.c)
     * @throws ClassNotFoundException if input trie is incorrect
     */
    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream myIn = new ObjectInputStream(in);
        Trie copy = (Trie) myIn.readObject();
        myIn.close();
        head = copy.head;
    }

    private static class Node implements Serializable {
        private Node[] nextNodes = new Node[(1 << 16)];
        private boolean theEnd = false;
        private int wordsCount = 0;
    }

    /**
     * First Node in Trie
     */
    private Node head;
}