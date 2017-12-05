package ru.iisuslik.function;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Some tests to Function1
 */
public class Function1Test {


    private Function1<Integer, Double> divide2 = x -> (double) x / 2;
    private Function1<String, Integer> len = s -> s.length();

    private Function1<Integer, Integer> square = x -> x * x;
    private Function1<Integer, Integer> plus43 = x -> x + 43;

    /**
     * Try to count some simple functions
     */
    @Test
    public void applySimple() throws Exception {
        assertEquals(25, (int) square.apply(5));
        assertEquals(48, (int) plus43.apply(5));
    }

    /**
     * Try to count functions from string to int and from int to double
     */
    @Test
    public void applyDifferentTypes() throws Exception {
        assertEquals(3, (int) len.apply("kek"));
        assertEquals(2.5, (double) divide2.apply(5), 0.1);
    }

    /**
     * Try to compose functions int->int
     */
    @Test
    public void composeSimple() throws Exception {
        assertEquals(47, (int) square.compose(plus43).apply(2));
    }

    /**
     * Try to compose functions string->int and int->double
     */
    @Test
    public void composeDifferentTypes() throws Exception {
        assertEquals(1.5, len.compose(divide2).apply("kek"), 0.1);
    }

}