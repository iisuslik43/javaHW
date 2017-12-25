package ru.iisuslik.test3;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.*;

public class SmartListTest {

    /**
     * Try to clear list
     */
    @Test
    public void clearTest() throws Exception {
        SmartList<Integer> sl = new SmartList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertEquals(6, sl.size());
        sl.clear();
        assertEquals(0, sl.size());

    }

    /**
     * try make new SmartLists with Integers and Strings
     */
    @Test
    public void simpleConstructor() throws Exception {
        SmartList<Integer> slInt = new SmartList<>();
        SmartList<String> slStr = new SmartList<>();
        assertEquals(0, slInt.size());
        assertEquals(0, slStr.size());
    }

    /**
     * try to remove element not by index from 4 size list
     */
    @Test
    public void removeObjectArray() throws Exception {
        SmartList<Integer> sl = new SmartList<>(Arrays.asList(1, 2, 3, 4));
        assertEquals(sl.remove((Integer) 2), true);
        assertArrayEquals(new Integer[]{1, 3, 4}, sl.toArray());
    }

    /**
     * try to remove element not by index from 1 size list
     */
    @Test
    public void removeObjectOne() throws Exception {
        SmartList<Integer> sl = new SmartList<>(Collections.singletonList(1));
        assertFalse(sl.remove((Integer) 2));
        assertEquals(1, (int) sl.get(0));
        assertTrue(sl.remove((Integer) 1));
        assertEquals(0, sl.size());
    }

    /**
     * try to remove element not by index from 7 size list
     */
    @Test
    public void removeObjectArrayList() throws Exception {
        SmartList<Integer> sl = new SmartList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertEquals(sl.remove((Integer) 2), true);
        assertArrayEquals(sl.toArray(), new Integer[]{1, 3, 4, 5, 6});
    }


    /**
     * Try to add new element before i element
     */
    @Test
    public void addIndex() throws Exception {
        SmartList<Integer> sl = new SmartList<>(Arrays.asList(1, 2, 3, 4));
        sl.add(1, 5);
        assertArrayEquals(new Integer[]{1, 5, 2, 3, 4}, sl.toArray());
    }

    /**
     * Try to add new element before i element in 1 size list
     */
    @Test
    public void addIndexOne() throws Exception {
        SmartList<Integer> sl = new SmartList<>(Arrays.asList(1));
        sl.add(1, 5);
        assertArrayEquals(new Integer[]{1, 5}, sl.toArray());
        sl = new SmartList<>();
        sl.add(0, 2);
        assertArrayEquals(new Integer[]{2}, sl.toArray());
    }

    @Test
    public void testSimple() {
        List<Integer> list = newList();

        assertEquals(Collections.<Integer>emptyList(), list);

        list.add(1);
        assertEquals(Collections.singletonList(1), list);

        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);
    }

    @Test
    public void testGetSet() {
        List<Object> list = newList();

        list.add(1);

        assertEquals(1, list.get(0));
        assertEquals(1, list.set(0, 2));
        assertEquals(2, list.get(0));
        assertEquals(2, list.set(0, 1));

        list.add(2);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));

        assertEquals(1, list.set(0, 2));

        assertEquals(Arrays.asList(2, 2), list);
    }

    @Test
    public void testRemove() throws Exception {
        List<Object> list = newList();

        list.add(1);
        list.remove(0);
        assertEquals(Collections.emptyList(), list);

        list.add(2);
        list.remove((Object) 2);
        assertEquals(Collections.emptyList(), list);

        list.add(1);
        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);

        list.remove(0);
        assertEquals(Collections.singletonList(2), list);

        list.remove(0);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void testIteratorRemove() throws Exception {
        List<Object> list = newList();
        assertFalse(list.iterator().hasNext());

        list.add(1);

        Iterator<Object> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);

        list.addAll(Arrays.asList(1, 2));

        iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertTrue(iterator.hasNext());
        assertEquals(Collections.singletonList(2), list);
        assertEquals(2, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);
    }


    @Test
    public void testCollectionConstructor() throws Exception {
        assertEquals(Collections.emptyList(), newList(Collections.emptyList()));
        assertEquals(
                Collections.singletonList(1),
                newList(Collections.singletonList(1)));

        assertEquals(
                Arrays.asList(1, 2),
                newList(Arrays.asList(1, 2)));
    }

    @Test
    public void testAddManyElementsThenRemove() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), list);

        for (int i = 0; i < 7; i++) {
            list.remove(list.size() - 1);
            assertEquals(6 - i, list.size());
        }

        assertEquals(Collections.emptyList(), list);
    }

    private static <T> List<T> newList() {
        try {
            return (List<T>) getListClass().getConstructor().newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> newList(Collection<T> collection) {
        try {
            return (List<T>) getListClass().getConstructor(Collection.class).newInstance(collection);
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getListClass() throws ClassNotFoundException {
        return Class.forName("ru.iisuslik.test3.SmartList");
    }

}