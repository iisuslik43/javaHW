package ru.iisuslik.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {
    @Test
    void createMap() {
        HashMap map = new HashMap();
        assertEquals(0, map.size());
    }


    @Test
    void put() {
        HashMap map = new HashMap();
        assertEquals(null, map.put("KEY", "VALUE"));
        assertEquals(1,map.size());
    }

    @Test
    void contains() {
        HashMap map = new HashMap();
        map.put("KEY", "VALUE");
        assertEquals(true, map.contains("KEY"));
        assertEquals(false, map.contains("WRONG"));
    }

    @Test
    void get() {
        HashMap map = new HashMap();
        map.put("KEY", "VALUE");
        assertEquals(true, map.contains("KEY"));
        assertEquals(false, map.contains("WRONG"));

    }


    @Test
    void remove() {

        HashMap map = new HashMap();
        map.put("KEY1", "VALUE1");
        map.put("KEY2", "VALUE2");
        assertEquals(null, map.remove("WRONG"));
        assertEquals("VALUE1", map.remove("KEY1"));
        assertEquals(false, map.contains("KEY1"));
        assertEquals(null, map.get("KEY1"));
    }

    @Test
    void clear() {
        HashMap map = new HashMap();
        map.put("KEY1", "VALUE1");
        map.put("KEY2", "VALUE2");
        map.clear();
        assertEquals(0, map.size());
        assertEquals(false, map.contains("KEY1"));
        assertEquals(false, map.contains("KEY2"));
    }

    @Test
    void putAndRehash(){
        HashMap map = new HashMap();
        map.put("KEY1", "VALUE1");
        map.put("KEY2", "VALUE2");
        map.put("KEY3", "VALUE3");
        assertEquals(3, map.size());
        assertEquals(true, map.contains("KEY1"));
        assertEquals(true, map.contains("KEY2"));
        assertEquals(true, map.contains("KEY3"));
        map.remove("KEY3");
        assertEquals(false, map.contains("KEY3"));
    }

}