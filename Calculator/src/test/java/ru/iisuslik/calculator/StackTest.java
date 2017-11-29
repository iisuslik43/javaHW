package ru.iisuslik.calculator;


import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {

    @Test
    public void constr() throws Exception {
        Stack<Integer> st = new Stack<>();
        Stack<String> st2 = new Stack<>();
    }

    @Test
    public void size() throws Exception {
        Stack<Integer> st = new Stack<>();
        assertEquals(0, st.size());
    }

    @Test
    public void push() throws Exception {
        Stack<Integer> st = new Stack<>();
        st.push(43);
        assertEquals(1, st.size());
        assertEquals(43, (int) st.top());
    }

    @Test
    public void popOne() throws Exception {
        Stack<Integer> st = new Stack<>();
        st.push(43);
        assertEquals(43, (int) st.pop());
        assertEquals(0, st.size());
    }

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

    @Test
    public void top() throws Exception {
    }

}