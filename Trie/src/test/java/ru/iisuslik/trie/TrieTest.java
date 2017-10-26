package ru.iisuslik.trie;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

/**
 * Class that tests Trie
 */
public class TrieTest {

    /**
     * Check if one added string contains in trie
     */
    @Test
    public void containsAddOne() throws Exception {
        Trie t = new Trie();
        t.add("LOL");
        assertTrue(t.contains("LOL"));
        assertFalse(t.contains("LO"));
        assertFalse(t.contains("LOlo"));
    }


    /**
     * Check if many string contains in trie
     */
    @Test
    public void containsAddFour() throws Exception {
        Trie t = new Trie();
        t.add("LOL");
        t.add("LO");
        assertTrue(t.contains("LO"));
        t.add("LOLO");
        assertTrue(t.contains("LOLO"));
        t.add("KEK");
        assertTrue(t.contains("KEK"));
        assertFalse(t.contains("KE"));
    }

    /**
     * Test add
     */
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

    /**
     * Add and remove one elemenr
     */
    @Test
    public void removeOne() throws Exception {
        Trie t = new Trie();
        assertTrue(t.add("LOL"));
        assertTrue(t.remove("LOL"));
        assertFalse(t.contains("LOL"));
    }

    /**
     * Add many strings and then try to remove them
     */
    @Test
    public void removeMany() throws Exception {
        Trie t = new Trie();
        assertTrue(t.add("LOL"));
        assertTrue(t.add("LO"));
        assertTrue(t.add("LOLO"));
        assertTrue(t.add("KEK"));
        assertTrue(t.remove("LOLO"));
        assertTrue(t.contains("LOL"));
        assertFalse(t.contains("LOLO"));
        assertFalse(t.remove("LOLO"));
        assertTrue(t.remove("LO"));
        assertTrue(t.contains("LOL"));
        assertFalse(t.contains("LO"));
        assertFalse(t.remove("LO"));
    }

    /**
     * Add many strings and then try to remove non-existent
     */
    @Test
    public void removeNonExistent() throws Exception {
        Trie t = new Trie();
        assertTrue(t.add("LOL"));
        assertTrue(t.add("LO"));
        assertTrue(t.add("LOLO"));
        assertTrue(t.add("KEK"));
        assertFalse(t.remove("L"));
        assertFalse(t.remove("K"));
    }

    /**
     * Check that size of empty trie equals to 0
     */
    @Test
    public void sizeEmpty() throws Exception {
        Trie t = new Trie();
        assertEquals(0, t.size());
    }

    /**
     * Add and remove string and check if size is changes correctly
     */
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

    /**
     * Try to add strings and count how many starts with some prefix
     */
    @Test
    public void howManyStartsWithPrefixSimple() throws Exception {
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
    }

    /**
     * Try to check if this function works correct after removing some elements
     */
    @Test
    public void howManyStartsWithPrefixWithRemove() throws Exception {
        Trie t = new Trie();
        t.add("LOL");
        t.add("L");
        t.add("LOLOLOL");
        t.add("KEK");
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

    /**
     * Try to make big Trie
     */
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

    /**
     * Try to serialize and deserialize Trie
     */
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
        byte[] buffer = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        Trie tCopy = new Trie();
        tCopy.add("KEK");
        tCopy.deserialize(in);
        assertEquals(t.toString(), tCopy.toString());
    }
}