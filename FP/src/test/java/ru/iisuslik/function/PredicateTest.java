package ru.iisuslik.function;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.iisuslik.function.Predicate.ALWAYS_FALSE;
import static ru.iisuslik.function.Predicate.ALWAYS_TRUE;

public class PredicateTest {

    private boolean dontChanged = true;

    private Predicate<Integer> throwException = x -> {
        dontChanged = false;
        return true;
    };

    private Predicate<Integer> positive = x -> (x >= 0);
    private Predicate<Integer> negative = x -> (x <= 0);
    private Predicate<Integer> equal43 = x -> (x == 43);


    /**
     * Tests or with ALWAYS_TRUE and ALWAYS_FALSE
     */
    @Test
    public void or() throws Exception {
        assertTrue((boolean) ALWAYS_TRUE.or(ALWAYS_TRUE).apply(43));
        assertTrue((boolean) ALWAYS_TRUE.or(ALWAYS_FALSE).apply(43));
        assertTrue((boolean) ALWAYS_FALSE.or(ALWAYS_TRUE).apply(43));
        assertFalse((boolean) ALWAYS_FALSE.or(ALWAYS_FALSE).apply(43));
    }

    /**
     * Tests that in function or second argument will never be counted if first is true
     */
    @Test
    public void orIsLazy() throws Exception {
        assertTrue((boolean) ALWAYS_TRUE.or(throwException).apply(43));
        assertTrue(dontChanged);
    }


    /**
     * Tests that in function and second argument will never be counted if first is false
     */
    @Test
    public void andIsLazy() throws Exception {
        assertFalse((boolean) ALWAYS_FALSE.and(throwException).apply(43));
        assertTrue(dontChanged);
    }


    /**
     * Tests and with ALWAYS_TRUE and ALWAYS_FALSE
     */
    @Test
    public void and() throws Exception {
        assertTrue((boolean) ALWAYS_TRUE.and(ALWAYS_TRUE).apply(43));
        assertFalse((boolean) ALWAYS_TRUE.and(ALWAYS_FALSE).apply(43));
        assertFalse((boolean) ALWAYS_FALSE.and(ALWAYS_TRUE).apply(43));
        assertFalse((boolean) ALWAYS_FALSE.and(ALWAYS_FALSE).apply(43));
    }

    /**
     * Tests not with ALWAYS_TRUE and ALWAYS_FALSE
     */
    @Test
    public void not() throws Exception {
        assertTrue((boolean) ALWAYS_FALSE.not().apply(43));
        assertFalse((boolean) ALWAYS_TRUE.not().apply(43));
    }

    /**
     * Tests working with some predicates
     */
    @Test
    public void works() throws Exception {
        assertTrue(negative.apply(-43));
        assertFalse(positive.apply(-43));
    }

    /**
     * Tests or with some predicates
     */
    @Test
    public void orPredic() throws Exception {
        assertTrue(positive.or(negative).apply(43));
        assertFalse(positive.or(equal43).apply(-43));
    }

    /**
     * Tests and with some predicates
     */
    @Test
    public void andPredic() throws Exception {
        assertFalse(positive.and(negative).apply(43));
        assertTrue(positive.and(negative).apply(0));
    }

}