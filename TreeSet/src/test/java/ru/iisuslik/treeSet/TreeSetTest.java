package ru.iisuslik.treeSet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;


/**
 * Some Test to TreeSet
 */
public class TreeSetTest {

    /**
     * Try to check if all elements from ArrayList contains in set
     */
    @Test
    public void containsAll() throws Exception {
        ArrayList<Integer> l = new ArrayList<>();
        l.add(2);
        l.add(3);
        assertTrue(sInt.containsAll(l));
        l.add(7);
        assertFalse(sInt.containsAll(l));
    }

    /**
     * Try to add all elements from ArrayList
     */
    @Test
    public void addAll() throws Exception {
        TreeSet<Integer> check = new TreeSet<>();
        ArrayList<Integer> l = new ArrayList<>();
        l.add(7);
        l.add(9);
        check.addAll(l);
        assertTrue(check.containsAll(l));
    }

    /**
     * Try to retain all elements from ArrayList
     */
    @Test
    public void retainAll() throws Exception {
        TreeSet<Integer> check = new TreeSet<>();
        check.addAll(sInt);
        ArrayList<Integer> l = new ArrayList<>();
        l.add(4);
        l.add(2);
        check.retainAll(l);
        assertTrue(check.containsAll(l));
        assertFalse(check.contains(1));
        assertFalse(check.contains(3));
        assertFalse(check.contains(5));

    }

    /**
     * Try to remove all elements from ArrayList
     */
    @Test
    public void removeAll() throws Exception {

        TreeSet<Integer> check = new TreeSet<>();
        check.addAll(sInt);
        ArrayList<Integer> l = new ArrayList<>();
        l.add(4);
        l.add(2);
        check.removeAll(l);
        assertFalse(check.containsAll(l));
        assertFalse(check.contains(2));
        assertFalse(check.contains(4));
        assertTrue(check.contains(1));
        assertTrue(check.contains(3));
        assertTrue(check.contains(5));
    }


    private TreeSet<Integer> sInt = new TreeSet<>();
    private TreeSet<Integer> sIntRev = new TreeSet<>(Comparator.reverseOrder());
    private TreeSet<String> sStr = new TreeSet<>();

    public TreeSetTest() {
        sInt.add(5);
        sInt.add(2);
        sInt.add(3);
        sInt.add(4);
        sInt.add(1);
        sIntRev.addAll(sInt);
        sStr.add("kek");
        sStr.add("lol");
        sStr.add("lel");
    }

    /**
     * Check that descending iterator goes through set from last to first
     */
    @Test
    public void descendingIterator() throws Exception {
        Iterator<Integer> it = sInt.descendingIterator();
        int[] arr = {5, 4, 3, 2, 1};
        int[] res = new int[5];
        int i = 0;
        while (it.hasNext()) {
            res[i++] = it.next();
        }
        assertArrayEquals(arr, res);
    }

    /**
     * Check that if you modify old set, descending set will be modified too
     */
    @Test
    public void descendingSet() throws Exception {
        TreeSet<Integer> kek = new TreeSet<>();
        kek.addAll(sInt);
        TreeSet<Integer> check = (TreeSet<Integer>) kek.descendingSet();
        check.add(43);
        assertTrue(check.contains(43));
        assertTrue(kek.contains(43));
        assertTrue(kek.remove(43));
        assertFalse(check.contains(43));
        assertFalse(kek.contains(43));
    }

    /**
     * Check that descending set has correct iterator
     */
    @Test
    public void descendingSetIterator() throws Exception {
        TreeSet<Integer> kek = new TreeSet<>();
        kek.addAll(sInt);
        TreeSet<Integer> check = (TreeSet<Integer>) kek.descendingSet();
        Object[] arr1 = kek.toArray();
        Object[] arr2 = check.toArray();
        for (int i = 0; i < arr1.length; i++) {
            assertEquals(arr1[i], arr2[arr1.length - 1 - i]);
        }
    }

    /**
     * Try to add 1 element to Integer set
     */
    @Test
    public void addWorks() throws Exception {
        TreeSet<Integer> s = new TreeSet<>();
        assertTrue(s.add(1));
        assertTrue(s.contains(1));
        assertFalse(s.add(1));
    }

    /**
     * Try to add 1 element to String set
     */
    @Test
    public void addToStringSet() throws Exception {
        TreeSet<String> s = new TreeSet<>();
        assertTrue(s.add("kek"));
        assertTrue(s.contains("kek"));
    }

    /**
     * Try to add all elements from ArrayList
     */
    @Test
    public void addAllWorks() throws Exception {
        TreeSet<Integer> s = new TreeSet<>();
        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(2);
        array.add(3);
        s.addAll(array);
        assertTrue(s.contains(1));
        assertTrue(s.contains(2));
        assertTrue(s.contains(3));
    }

    /**
     * Try to add all elements from TreeSet
     */
    @Test
    public void addAllFromSet() throws Exception {
        TreeSet<Integer> s = new TreeSet<>();
        s.addAll(sInt);
        assertTrue(s.contains(1));
        assertTrue(s.contains(2));
        assertTrue(s.contains(3));
        assertTrue(s.contains(4));
        assertTrue(s.contains(5));
    }

    /**
     * Try to add all elements from TreeSet<String>
     */
    @Test
    public void addAllFromSetWithStrings() throws Exception {
        TreeSet<String> s = new TreeSet<>();
        s.addAll(sStr);
        assertTrue(s.contains("kek"));
        assertTrue(s.contains("lel"));
        assertTrue(s.contains("lol"));
    }

    /**
     * Check that size of empty set is 0
     */
    @Test
    public void sizeEmpty() throws Exception {
        TreeSet<String> s = new TreeSet<>();
        assertEquals(0, s.size());
    }

    /**
     * Check that size of two sets works correct
     */
    @Test
    public void sizeSets() throws Exception {
        assertEquals(5, sInt.size());
        assertEquals(3, sStr.size());
    }


    /**
     * Check that isEmpty really works
     */
    @Test
    public void isEmpty() throws Exception {
        TreeSet<String> s = new TreeSet<>();
        assertTrue(s.isEmpty());
        assertFalse(sInt.isEmpty());
        assertFalse(sStr.isEmpty());
    }

    /**
     * Check that iterator.next - minimum in set
     */
    @Test
    public void iteratorWorksInFirst() throws Exception {
        Iterator<Integer> it = sInt.iterator();
        assertTrue(it.hasNext());
        assertEquals(1, (int) it.next());
        Iterator<String> it2 = sStr.iterator();
        assertTrue(it2.hasNext());
        assertEquals("kek", it2.next());
    }

    /**
     * Check that iterator can go through all set
     */
    @Test
    public void iteratorWorksGood() throws Exception {
        int[] expected1 = {1, 2, 3, 4, 5};
        int i = 0;
        for (int el : sInt) {
            assertEquals(expected1[i], el);
            i++;
        }
        i = 0;
        String[] expected2 = {"kek", "lel", "lol"};
        for (String el : sStr) {
            assertEquals(expected2[i], el);
            i++;
        }
    }

    /**
     * Check that if remove returns true, size = size-1
     */
    @Test
    public void removeAndSize() throws Exception {
        TreeSet<Integer> s = new TreeSet<>();
        s.addAll(sInt);
        assertTrue(s.remove(2));
        assertEquals(4, s.size());
        assertFalse(s.remove(2));
        assertEquals(4, s.size());
    }

    /**
     * Check that if you removed element, contains(element) = false
     */
    @Test
    public void removeAndContains() throws Exception {
        TreeSet<Integer> s = new TreeSet<>();
        s.addAll(sInt);
        s.remove(2);
        assertFalse(s.contains(2));
    }

    /**
     * Check for each work correctly after remove
     */
    @Test
    public void removeAndForEach() throws Exception {
        TreeSet<Integer> s = new TreeSet<>();
        s.addAll(sInt);
        s.remove(2);
        int[] expected1 = {1, 3, 4, 5};
        int i = 0;
        for (int el : s) {
            assertEquals(expected1[i], el);
            i++;
        }
    }

    /**
     * Check that toArray works correct
     */
    @Test
    public void toArraySimple() throws Exception {
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, sInt.toArray());
        assertArrayEquals(new String[]{"kek", "lel", "lol"}, sStr.toArray());
    }

    /**
     * Check that toArray works correct after adding and removing
     */
    @Test
    public void toArrayModificated() throws Exception {
        TreeSet<String> check = new TreeSet<>();
        check.addAll(sStr);
        assertTrue(check.add("ke"));
        assertArrayEquals(new String[]{"ke", "kek", "lel", "lol"}, check.toArray());
        assertTrue(check.remove("kek"));
        assertArrayEquals(new String[]{"ke", "lel", "lol"}, check.toArray());
    }

    /**
     * Check that clear really delete everything
     */
    @Test
    public void clear() throws Exception {
        TreeSet<String> check = new TreeSet<>();
        check.addAll(sStr);
        check.clear();
        assertTrue(check.isEmpty());
    }

    /**
     * Check that clear empty set do nothing
     */
    @Test
    public void clearEmpty() throws Exception {
        TreeSet<String> check = new TreeSet<>();
        check.clear();
        assertTrue(check.isEmpty());
    }

    /**
     * Test add, remove and toArray in set(comparator)
     */
    @Test
    public void anotherConstructorWorks() throws Exception {
        TreeSet<Integer> check = new TreeSet<>(Comparator.reverseOrder());
        assertTrue(check.add(1));
        assertTrue(check.add(2));
        assertFalse(check.add(2));
        assertArrayEquals(new Integer[]{2, 1}, check.toArray());
        assertTrue(check.add(4));
        assertTrue(check.remove(2));
        assertFalse(check.remove(43));
        assertArrayEquals(new Integer[]{4, 1}, check.toArray());
    }

    /**
     * Check first and last in set(comparator)
     */
    @Test
    public void anotherConstructorMinMax() throws Exception {
        assertEquals(5, (int) sIntRev.first());
        assertEquals(1, (int) sIntRev.last());
    }

    /**
     * Check first and last
     */
    @Test
    public void minMax() throws Exception {
        assertEquals(1, (int) sInt.first());
        assertEquals(5, (int) sInt.last());
        assertEquals("kek", sStr.first());
        assertEquals("lol", sStr.last());
    }

    /**
     * Try to find lower, higher, ceiling, floor of element that contains in set
     */
    @Test
    public void lowerHigherSimple() throws Exception {
        assertEquals(2, (int) sInt.lower(3));
        assertEquals(4, (int) sInt.higher(3));
        assertEquals(3, (int) sInt.ceiling(3));
        assertEquals(3, (int) sInt.floor(3));
    }

    /**
     * Try to find lower, higher, ceiling, floor of element that doesn't contain in set
     */
    @Test
    public void lowerHigherDoNotContains() throws Exception {
        TreeSet<Integer> check = new TreeSet<>();
        check.addAll(sInt);
        check.remove(3);
        assertEquals(2, (int) check.lower(3));
        assertEquals(4, (int) check.higher(3));
        assertEquals(4, (int) check.ceiling(3));
        assertEquals(2, (int) check.floor(3));
    }

}