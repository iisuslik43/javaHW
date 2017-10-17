package ru.iisuslik.set;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Some tests to class Set
 */
public class SetTest {

    /**
     * Tries to create sets with different T
     * @throws Exception
     */
    @Test
    public void manySets() throws Exception {
        Set<Integer> s1 = new Set<Integer>();
        Set<String> s2 = new Set<String>();
        Set<Double> s3 = new Set<Double>();
    }

    /**
     * Tries to add values to sets
     * @throws Exception
     */
    @Test
    public void addToManySets() throws Exception {
        Set<Integer> s1 = new Set<Integer>();
        Set<String> s2 = new Set<String>();
        Set<Double> s3 = new Set<Double>();
        assertTrue(s1.add(43));
        assertTrue(s2.add("STRING"));
        assertTrue(s3.add(2.2));
    }

    /**
     * Tests function "contains" with different T
     * @throws Exception
     */
    @Test
    public void addAndCheckContains() throws Exception {
        Set<Integer> s1 = new Set<Integer>();
        Set<String> s2 = new Set<String>();
        Set<Double> s3 = new Set<Double>();
        assertTrue(s1.add(43));
        assertTrue(s2.add("STRING"));
        assertTrue(s3.add(2.2));
        assertTrue(s1.contains(43));
        assertTrue(s2.contains("STRING"));
        assertTrue(s3.contains(2.2));
    }

    /**
     * Tries to add 2 elements
     * @throws Exception
     */
    @Test
    public void addAgain() throws Exception {
        Set<Integer> s = new Set<Integer>();
        assertTrue(s.add(43));
        assertFalse(s.add(43));
    }

    /**
     * Tries to add many elements and check size
     * @throws Exception
     */
    @Test
    public void addMultipleTimes() throws Exception {
        Set<Integer> s = new Set<Integer>();
        assertEquals(0, s.size());
        for (int i = 1; i < 43; i++) {
            assertTrue(s.add(i));
            assertEquals(i, s.size());
        }
        assertFalse(s.add(42));
        for (int i = 1; i < 43; i++) {
            assertTrue(s.contains(i));
        }
        assertFalse(s.contains(43));
        assertFalse(s.contains(0));
    }

}