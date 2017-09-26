package ru.iisuslik.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {
    @Test
    void createList() {
        List list = new List();
        assertEquals(null, list.getHeadKey());
        assertEquals(null, list.getHeadValue());
    }

    @Test
    void addKey() {
        List list = new List();
        list.add("KEY", "VALUE");
        assertEquals("KEY", list.getHeadKey());
        assertEquals("VALUE", list.getHeadValue());
    }

    @Test
    void getKey() {
        List list = new List();
        list.add("KEY", "VALUE");
        assertEquals("VALUE", list.findKey("KEY"));
        assertEquals(null, list.findKey("WRONG"));
    }

    @Test
    void addTwo() {
        List list = new List();
        list.add("KEY1", "VALUE1");
        list.add("KEY2", "VALUE2");
        assertEquals("VALUE1", list.findKey("KEY1"));
        assertEquals("VALUE2", list.findKey("KEY2"));
        assertEquals(null, list.findKey("WRONG"));
    }

    @Test
    void deleteKey() {
        List list = new List();
        list.add("KEY", "VALUE");
        assertEquals(null, list.delete("WRONG"));
        assertEquals("VALUE", list.delete("KEY"));
        assertEquals(null, list.findKey("KEY"));
    }

    @Test
    void addCopy() {
        List list = new List();
        list.add("KEY", "VALUE");
        assertEquals("VALUE", list.add("KEY", "OTHER_VALUE"));
        assertEquals(null, list.add("KEY2", "VALUE2"));
    }

    @Test
    void clear() {
        List list = new List();
        list.add("KEY", "VALUE");
    }

}