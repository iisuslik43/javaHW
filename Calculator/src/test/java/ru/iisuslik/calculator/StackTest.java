package ru.iisuslik.calculator;


import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

/**
 * Some tests to Stack
 */
public class StackTest {

    /**
     * Try to make new Stack
     */
    @Test
    public void construct() throws Exception {
        Stack<Integer> st = new Stack<>();
        Stack<String> st2 = new Stack<>();
    }

    /**
     * Check that empty stack size equals to 0
     */
    @Test
    public void size() throws Exception {
        Stack<Integer> st = new Stack<>();
        assertEquals(0, st.size());
    }

    /**
     * Try to add new element to stack
     */
    @Test
    public void push() throws Exception {
        Stack<Integer> st = new Stack<>();
        st.push(43);
        assertEquals(1, st.size());
        assertEquals(43, (int) st.top());
    }

    /**
     * Try to add new element and delete it
     */
    @Test
    public void popOne() throws Exception {
        Stack<Integer> st = new Stack<>();
        st.push(43);
        assertEquals(43, (int) st.pop());
        assertEquals(0, st.size());
    }

    /**
     * Check that pop from empty stack throws exception
     */
    @Test
    public void popFromEmpty() throws Exception {
        Stack<Integer> st = new Stack<>();
        boolean wasException = false;
        try {
            st.pop();
        } catch (EmptyStackException e) {
            wasException = true;
        }
        assertTrue(wasException);
    }

    /**
     * Try to add and delete many elements
     */
    @Test
    public void popMany() throws Exception {
        Stack<Integer> st = new Stack<>();
        st.push(43);
        st.push(42);
        st.push(2);
        st.push(4);
        assertEquals(4, (int) st.top());
        assertEquals(4, (int) st.pop());
        assertEquals(3, st.size());

        assertEquals(2, (int) st.top());
        assertEquals(2, (int) st.pop());
        assertEquals(2, st.size());

        assertEquals(42, (int) st.top());
        assertEquals(42, (int) st.pop());
        assertEquals(1, st.size());


        assertEquals(43, (int) st.top());
        assertEquals(43, (int) st.pop());
        assertEquals(0, st.size());
    }

    /**
     * Check that top works correctly
     */
    @Test
    public void top() throws Exception {
        Stack<Integer> st = new Stack<>();
        st.push(43);
        st.push(42);
        st.push(2);
        st.push(4);
        assertEquals(4, (int) st.top());
        st.pop();
        assertEquals(2, (int) st.top());
        st.pop();
        assertEquals(42, (int) st.top());
        st.pop();
        assertEquals(43, (int) st.top());
        st.pop();
    }

}