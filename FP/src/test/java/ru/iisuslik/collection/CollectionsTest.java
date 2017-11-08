package ru.iisuslik.collection;

import org.junit.Test;
import ru.iisuslik.function.Function1;
import ru.iisuslik.function.Function2;
import ru.iisuslik.function.Predicate;

import java.util.*;

import static org.junit.Assert.*;
import static ru.iisuslik.collection.Collections.*;

/**
 * Some tests to Collections
 */
public class CollectionsTest {

    private ArrayList<Integer> arrInteger = new ArrayList<>();
    private ArrayList<String> arrString = new ArrayList<>();

    public CollectionsTest() {
        arrInteger.add(1);
        arrInteger.add(2);
        arrInteger.add(3);
        arrInteger.add(4);
        arrInteger.add(5);

        arrString.add("tops");
        arrString.add("Lol");
        arrString.add("kekes");
        arrString.add("lol");
    }

    /**
     * Tests map with function int -> int
     */
    @Test
    public void mapSimple() throws Exception {
        Function1<Integer, Integer> f = x -> x * x + 1;
        ArrayList<Integer> res = new ArrayList<>();
        res.add(2);
        res.add(5);
        res.add(10);
        res.add(17);
        res.add(26);
        assertEquals(res, map(f, arrInteger));
    }

    /**
     * Tests map with function string -> int
     */
    @Test
    public void mapDifferentTypes() throws Exception {
        Function1<String, Integer> f = x -> x.length() + 2;
        ArrayList<Integer> res = new ArrayList<>();
        res.add(6);
        res.add(5);
        res.add(7);
        res.add(5);
        assertEquals(res, map(f, arrString));
    }

    /**
     * Tests filter with function int -> boolean
     */
    @Test
    public void filterSimple() throws Exception {
        Predicate<Integer> odd = x -> x % 2 == 0;
        ArrayList<Integer> res = new ArrayList<>();
        res.add(2);
        res.add(4);
        assertEquals(res, filter(odd, arrInteger));
    }

    /**
     * Tests filter with function string -> boolean
     */
    @Test
    public void filterDifferentTypes() throws Exception {
        Predicate<String> equalsLol = x -> x.equals("Lol") || x.equals("lol");
        ArrayList<String> res = new ArrayList<>();
        res.add("Lol");
        res.add("lol");
        assertEquals(res, filter(equalsLol, arrString));
    }

    /**
     * Tests takeWhile with function int -> boolean
     */
    @Test
    public void takeWhile() throws Exception {
        Predicate<Integer> lessThen3 = x -> x < 3;
        ArrayList<Integer> res = new ArrayList<>();
        res.add(1);
        res.add(2);
        assertEquals(res, Collections.takeWhile(lessThen3, arrInteger));
    }

    /**
     * Tests map with function int -> boolean
     */
    @Test
    public void takeUntil() throws Exception {
        Predicate<Integer> moreThen3 = x -> x > 3;
        ArrayList<Integer> res = new ArrayList<>();
        res.add(1);
        res.add(2);
        res.add(3);
        assertEquals(res, Collections.takeUntil(moreThen3, arrInteger));
    }


    /**
     * Tests foldr with function int -> int -> int
     */
    @Test
    public void foldrSimple() throws Exception {
        Function2<Integer, Integer, Integer> f = (x, y) -> x + y;
        assertEquals(15, (int) foldr(f, arrInteger, 0));
    }

    /**
     * Tests foldr with function string -> int
     */
    @Test
    public void foldrDifferentTypes() throws Exception {
        Function2<Integer, String, String> f = (x, s) -> x.toString() + s;
        assertTrue(foldr(f, arrInteger, "").equals("12345"));
    }

    /**
     * Tests that foldr is not a foldl in some situations
     */
    @Test
    public void foldrDiff() throws Exception {
        Function2<Integer, Integer, Integer> f = (x, y) -> x - y;
        assertEquals(3, (int) foldr(f, arrInteger, 0));
    }

    /**
     * Tests foldl with function int -> int
     */
    @Test
    public void foldlSimple() throws Exception {
        Function2<Integer, Integer, Integer> f = (x, y) -> x + y;
        assertEquals(15, (int) foldl(f, arrInteger, 0));
    }

    /**
     * Tests foldr with function string -> int
     */
    @Test
    public void foldlDifferentTypes() throws Exception {
        Function2<String, Integer, String> f = (x, s) -> x.toString() + s;
        assertTrue(foldl(f, arrInteger, "").equals("12345"));
    }

    /**
     * Tests that foldr is not a foldl in some situations
     */
    @Test
    public void foldlDiff() throws Exception {
        Function2<Integer, Integer, Integer> f = (x, y) -> x - y;
        assertEquals(-15, (int) foldl(f, arrInteger, 0));
    }

}