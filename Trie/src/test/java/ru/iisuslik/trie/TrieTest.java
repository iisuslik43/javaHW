package ru.iisuslik.trie;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

/**Class that tests Trie*/
public class TrieTest {

    /**Test contains*/
    @Test
    public void contains() throws Exception {
        Trie t = new Trie();
        t.add("LOL");
        assertTrue(t.contains("LOL"));
        assertFalse(t.contains("LO"));
        assertFalse(t.contains("LOlo"));
        t.add("LO");
        assertTrue(t.contains("LO"));
        t.add("LOLO");
        assertTrue(t.contains("LOLO"));
        t.remove("LO");
        assertFalse(t.contains("LO"));
    }

    /**Test add*/
    @Test
    public void add() throws Exception {
        Trie t = new Trie();
        assertTrue(t.add("LOL"));
        assertTrue(t.contains("LOL"));
        assertTrue(t.add("LO"));
        assertTrue(t.contains("LO"));
        assertTrue(t.add("LOLO"));
        assertTrue(t.contains("LOLO"));
    }

    /**Test remove*/
    @Test
    public void remove() throws Exception {
        Trie t = new Trie();
        assertTrue(t.add("LOL"));
        assertTrue(t.add("LO"));
        assertTrue(t.add("LOLO"));
        assertTrue(t.add("KEK"));
        assertFalse(t.remove("L"));
        assertFalse(t.remove("K"));
        assertTrue(t.remove("LOLO"));
        assertTrue(t.contains("LOL"));
        assertFalse(t.contains("LOLO"));
        assertFalse(t.remove("LOLO"));
        assertTrue(t.remove("LO"));
        assertTrue(t.contains("LOL"));
        assertFalse(t.contains("LO"));
        assertFalse(t.remove("LO"));
    }

    /**Test size*/
    @Test
    public void size() throws Exception {
        Trie t = new Trie();
        t.add("LOL");
        assertEquals(1, t.size());
        t.add("LO");
        assertEquals(2, t.size());
        t.add("LOLO");
        assertEquals(3, t.size());
        t.remove("LEL");
        assertEquals(3, t.size());
        t.remove("LOL");
        assertEquals(2, t.size());
    }

    /**Test howManyStartsWithPrefix*/
    @Test
    public void howManyStartsWithPrefix() throws Exception {
        Trie t = new Trie();
        t.add("LOL");
        t.add("L");
        t.add("LOLOLOL");
        t.add("KEK");
        assertEquals(3, t.howManyStartsWithPrefix("L"));
        assertEquals(2, t.howManyStartsWithPrefix("LO"));
        assertEquals(2, t.howManyStartsWithPrefix("LOL"));
        assertEquals(1, t.howManyStartsWithPrefix("LOLOL"));
        assertEquals(1, t.howManyStartsWithPrefix("LOLOLOL"));
        assertEquals(1, t.howManyStartsWithPrefix("K"));
        t.remove("LOL");
        assertEquals(2, t.howManyStartsWithPrefix("L"));
        assertEquals(1, t.howManyStartsWithPrefix("LO"));
        assertEquals(1, t.howManyStartsWithPrefix("LOL"));
        assertEquals(1, t.howManyStartsWithPrefix("LOLO"));
        assertEquals(0, t.howManyStartsWithPrefix("S"));
        assertEquals(0, t.howManyStartsWithPrefix("KEKS"));
        t.remove("LOLOLOL");
        assertEquals(0, t.howManyStartsWithPrefix("LO"));
        assertEquals(0, t.howManyStartsWithPrefix("LOL"));
        assertEquals(1, t.howManyStartsWithPrefix("L"));
    }

    /**Try to make big Trie*/
    @Test
    public void addHardTrie() throws Exception {
        Trie t = new Trie();
        t.add("L");
        t.add("LOL");
        t.add("LOKOL");
        t.add("LOLOL");
        t.add("LOLOK");
        assertTrue(t.contains("L"));
        assertTrue(t.contains("LOL"));
        assertTrue(t.contains("LOKOL"));
        assertTrue(t.contains("LOLOL"));
        assertTrue(t.contains("LOLOK"));
        assertFalse(t.contains("LO"));
        assertEquals(5, t.howManyStartsWithPrefix("L"));
        assertEquals(4, t.howManyStartsWithPrefix("LO"));
        assertEquals(3, t.howManyStartsWithPrefix("LOL"));
        assertEquals(1, t.howManyStartsWithPrefix("LOK"));
    }

    /**Try to serialize and deserialize Trie*/
    @Test
    public void readingAndWriting() throws Exception {
        Trie t = new Trie();
        t.add("L");
        t.add("LOL");
        t.add("LOKOL");
        t.add("LOLOL");
        t.add("LOLOK");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        t.serialize(out);
        byte [] buffer = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        Trie tCopy = new Trie();
        tCopy.add("KEK");
        tCopy.deserialize(in);
        assertEquals(t.toStringTrie(), tCopy.toStringTrie());
    }
}