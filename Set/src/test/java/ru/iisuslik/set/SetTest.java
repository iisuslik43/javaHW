package ru.iisuslik.set;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Some tests to class Set
 */
public class SetTest {

    /**
     * Tries to create sets with different T
     */
    @Test
    public void manySets() throws Exception {
        Set<Integer> s1 = new Set<>();
        Set<String> s2 = new Set<>();
        Set<Double> s3 = new Set<>();
        class Kek implements Comparable<Kek> {
            private int value = 43;

            @Override
            public int compareTo(@NotNull Kek o) {
                return Integer.compare(value * value, o.value * o.value * o.value);
            }
        }
        Set<Kek> s4 = new Set<>();
    }

    /**
     * Tries to add values to sets
     */
    @Test
    public void addToManySets() throws Exception {
        Set<Integer> s1 = new Set<>();
        Set<String> s2 = new Set<>();
        Set<Double> s3 = new Set<>();
        assertTrue(s1.add(43));
        assertTrue(s2.add("STRING"));
        assertTrue(s3.add(2.2));
    }

    /**
     * Tests function "contains" with different T
     */
    @Test
    public void addAndCheckContains() throws Exception {
        Set<Integer> s1 = new Set<>();
        Set<String> s2 = new Set<>();
        Set<Double> s3 = new Set<>();
        assertTrue(s1.add(43));
        assertTrue(s2.add("STRING"));
        assertTrue(s3.add(2.2));
        assertTrue(s1.contains(43));
        assertTrue(s2.contains("STRING"));
        assertTrue(s3.contains(2.2));
    }

    /**
     * Tries to add 2 elements
     */
    @Test
    public void addAgain() throws Exception {
        Set<Integer> s = new Set<>();
        assertTrue(s.add(43));
        assertFalse(s.add(43));
    }

    /**
     * Tries to add many elements and check size
     */
    @Test
    public void addMultipleTimes() throws Exception {
        Set<Integer> s = new Set<>();
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

    /**
     * Try to work with class that contains int and has strange comparator
     */
    @Test
    public void testStrangeClass() throws Exception {

        class Kek implements Comparable<Kek> {
            private int value;

            public Kek(int v) {
                value = v;
            }

            @Override
            public int compareTo(@NotNull Kek o) {
                return Integer.compare(value * value, o.value * o.value);
            }
        }
        Set<Kek> s = new Set<>();
        s.add(new Kek(43));
        s.add(new Kek(42));
        assertTrue(s.contains(new Kek(43)));
        assertTrue(s.contains(new Kek(42)));
        assertEquals(2, s.size());
    }
}