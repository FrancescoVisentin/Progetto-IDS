package myTest;

import myAdapter.*;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MapTester
{
    private MapAdapter map;
    
    @Before
    public void inizialize()
    {
        map = new MapAdapter();
    }

    /**
     * Test del metodo clear()
     */
    @Test
    public void clearTest()
    {
        assertEquals(map.size() , 0);

        map.clear();
        assertEquals(map.size() , 0);

        map.put("uno", 1);
        map.put("due", 2);
        assertEquals(map.size() , 2);
        map.clear();
        assertEquals(map.size() , 0);
        assertFalse(map.containsKey("uno"));
        assertFalse(map.containsKey("due"));
    }

    /**
     * Test del metodo containsKey(Object key)
     */
    @Test
    public void containsKeyTest()
    {
        assertEquals(map.size(), 0);
        assertFalse(map.containsKey(""));

        fill(map);

        assertTrue(map.containsKey("tre"));
        assertTrue(map.containsKey("uno"));
        assertTrue(map.containsKey(6));
        assertFalse(map.containsKey(""));

        map.remove("uno");
        map.remove(6);

        assertFalse(map.containsKey("uno"));
        assertFalse(map.containsKey(6));
        assertTrue(map.containsKey(5));

        map.clear();
        assertFalse(map.containsKey(8));
    }

    /**
     * Test delle eccezioni del metodo containsKey(Object key)
     */
    @Test
    public void containsKeyExceptionsTest()
    {
        assertEquals(map.size(), 0);
        assertThrows(NullPointerException.class, () -> {map.containsKey(null);});
        map.put("",0);
        assertThrows(NullPointerException.class, () -> {map.containsKey(null);});
        assertEquals(map.size(), 1);
        assertTrue(map.containsKey(""));
    }

    /**
     * Test del metodo containsValue(Object value)
     */
    @Test
    public void containsValueTest()
    {
        assertEquals(map.size(), 0);
        assertFalse(map.containsValue(""));

        fill(map);

        assertTrue(map.containsValue(3));
        assertTrue(map.containsValue(1));
        assertTrue(map.containsValue("sei"));
        assertFalse(map.containsValue(""));

        map.remove("uno");
        map.remove(6);

        assertFalse(map.containsValue(1));
        assertFalse(map.containsValue("sei"));
        assertTrue(map.containsValue("cinque"));

        map.clear();
        assertFalse(map.containsValue("otto"));
    }

    /**
     * Test delle eccezioni del metodo containsValue(Object value)
     */
    @Test
    public void containsValueExceptionsTest()
    {
        assertEquals(map.size(), 0);
        assertThrows(NullPointerException.class, () -> {map.containsValue(null);});
        map.put("",0);
        assertThrows(NullPointerException.class, () -> {map.containsValue(null);});
        assertEquals(map.size(), 1);
        assertTrue(map.containsKey(""));
    }

    /**
     * Test del metodo entrySet()
     */
    @Test
    public void entrySetTest()
    {
        assertEquals(map.size(), 0);
        HSet s = map.entrySet();

        fill(map);

        s = map.entrySet();
        assertEquals(map.size(), s.size());
        Object o = s.iterator().next();
        assertTrue(o instanceof HMap.Entry);
    }

    /**
     * Test del metodo equals(Object o)
     */
    @Test
    public void equalsTest()
    {
        assertFalse(map.equals(new ListAdapter()));

        assertFalse(map.equals(null));

        MapAdapter m = new MapAdapter();

        fill(map);
        fill(m);

        assertTrue(map.equals(m));
        assertTrue(m.equals(map));

        map.remove("uno");
        assertFalse(map.equals(m));
        assertFalse(m.equals(map));

        map.put("uno", 1);
        assertTrue(map.equals(m));
        assertTrue(m.equals(map));
        
        map.clear();
        m.clear();
        assertTrue(map.equals(m));
        assertTrue(m.equals(map));
    }

    /**
     * Test del metodo get(Object key)
     */
    @Test
    public void getTest()
    {
        assertEquals(map.size(), 0);
        assertEquals(map.get(""), null);

        fill(map);

        assertEquals(map.get("due"), 2);
        assertEquals(map.get("quattro"), 4);
        assertEquals(map.get(6), "sei");
        assertEquals(map.get(8), "otto");
        assertEquals(map.get("cinque"), null);

        map.clear();
        assertEquals(map.get("quattro"), null);
        assertEquals(map.get(6), null);
    }

    /**
     * Test delle eccezioni del metodo get(Object key)
     */
    @Test
    public void getExceptionsTest()
    {
        assertEquals(map.size(), 0);
        assertThrows(NullPointerException.class, () -> {map.get(null);});
        map.put("",0);
        assertThrows(NullPointerException.class, () -> {map.get(null);});
        assertEquals(map.size(), 1);
        assertTrue(map.containsKey(""));
    }

    /**
     * Test del metodo hashCode()
     */
    @Test
    public void hashCodeTest()
    {
        MapAdapter m = new MapAdapter();

        fill(map);
        fill(m);

        assertTrue(map.equals(m));
        assertEquals(map.hashCode(), m.hashCode());

        map.remove("uno");
        assertNotEquals(map.hashCode(), m.hashCode());

        map.put("uno", 1);
        assertEquals(map.hashCode(), m.hashCode());
        
        map.clear();
        m.clear();
        assertEquals(map.hashCode(), m.hashCode());
        assertEquals(map.hashCode(), 0);
    }

    /**
     * Test del metodo isEmpty()
     */
    @Test
    public void isEmptyTest()
    {
        assertEquals(map.size() , 0);
        assertTrue(map.isEmpty());

        fill(map);

        assertEquals(map.size() , 8);
        assertFalse(map.isEmpty());

        map.clear();
        assertEquals(map.size() , 0);
        assertTrue(map.isEmpty());
    }

    /**
     * Test del metodo keySet()
     */
    @Test
    public void keySetTest()
    {
        assertEquals(map.size(), 0);
        HSet s = map.keySet();

        fill(map);

        s = map.keySet();
        assertEquals(map.size(), s.size());
        
        HIterator it = s.iterator();
        while (it.hasNext())
            assertTrue(map.containsKey(it.next()));
    }

    /**
     * Test del metodo put(Object key, Object value)
     */
    @Test
    public void putTest()
    {
        assertTrue(map.isEmpty());
        assertEquals(map.put("uno", 1), null);
        assertEquals(map.size(), 1);
        assertTrue(map.containsKey("uno"));
        assertTrue(map.containsValue(1));

        assertEquals(map.put("due", 2), null);
        assertEquals(map.put("uno", 2), 1);
        assertEquals(map.size(), 2);

        assertEquals(map.put("tre", 2), null);        
        assertEquals(map.put("uno", 2), 2);
        assertEquals(map.size(), 3);

        assertTrue(map.containsKey("uno"));
        assertTrue(map.containsKey("due"));
        assertTrue(map.containsKey("tre"));
        assertTrue(map.containsValue(2));
    }

    /**
     * Test delle eccezioni del metodo put(Object key, Object value)
     */
    @Test
    public void putExceptionsTest()
    {
        assertEquals(map.size(), 0);
        assertThrows(NullPointerException.class, () -> {map.put(null, null);});
        assertThrows(NullPointerException.class, () -> {map.put("", null);});
        assertThrows(NullPointerException.class, () -> {map.put(null, "");});
        map.put("",0);
        assertThrows(NullPointerException.class, () -> {map.put(null, null);});
        assertThrows(NullPointerException.class, () -> {map.put("", null);});
        assertThrows(NullPointerException.class, () -> {map.put(null, "");});
        assertEquals(map.size(), 1);
        assertTrue(map.containsKey(""));
    }  

    /**
     * Test del metodo putAll(HMap t)
     */
    @Test
    public void putAllTest()
    {
        assertTrue(map.isEmpty());
        map.putAll(new MapAdapter());
        assertTrue(map.isEmpty());

        map.put("tre", 33);
        map.put("sette", 7);
        map.put(5,"cinque");
        assertEquals(map.size(), 3);

        MapAdapter m = new MapAdapter();
        fill(m);
        map.putAll(m);
        assertEquals(map.size(), 9);
        assertEquals(map.get("tre"), 3);
        assertEquals(map.get(5), "cinque");
        assertFalse(map.containsValue(33));
    }

    /**
     * Test delle eccezioni del metodo putAll(HMap t)
     */
    @Test
    public void putAllExceptionsTest()
    {
        assertEquals(map.size(), 0);
        assertThrows(NullPointerException.class, () -> {map.putAll(null);});
        map.put("",0);
        assertThrows(NullPointerException.class, () -> {map.putAll(null);});
        assertEquals(map.size(), 1);
        assertTrue(map.containsKey(""));
    }

    /**
     * Test del metodo remove(Object key)
     */
    @Test
    public void removeTest()
    {
        assertTrue(map.isEmpty());
        assertEquals(map.remove(""), null);
        assertTrue(map.isEmpty());

        fill(map);
        assertEquals(map.size(), 8);

        assertEquals(map.remove("uno"), 1);
        assertEquals(map.remove(5), "cinque");
        assertEquals(map.remove(2), null);
        assertEquals(map.size(), 6);

        Object[] a = map.keySet().toArray();
        for (Object o : a)
            assertFalse(map.containsValue(map.remove(o)));

        assertEquals(map.size(), 0);
    }

    /**
     * Test delle eccezioni del metodo remove(Object key)
     */
    @Test
    public void removeExceptionsTest()
    {
        assertEquals(map.size(), 0);
        assertThrows(NullPointerException.class, () -> {map.remove(null);});
        map.put("",0);
        assertThrows(NullPointerException.class, () -> {map.remove(null);});
        assertEquals(map.size(), 1);
        assertTrue(map.containsKey(""));
    }

    /**
     * Test del metodo size()
     */
    @Test
    public void sizeTest()
    {
        assertTrue(map.isEmpty());
        assertEquals(map.size(), 0);

        map.put("uno", 1);
        map.put("due", 2);
        assertEquals(map.size(), 2);

        map.remove("uno");
        assertEquals(map.size(), 1);

        fill(map);
        assertEquals(map.size(), 8);

        map.clear();
        assertEquals(map.size(), 0);
    }

    /**
     * Test del metodo values()
     */
    @Test
    public void valuesTest()
    {
        assertEquals(map.size(), 0);
        HCollection c = map.values();

        fill(map);

        c = map.values();
        assertEquals(map.size(), c.size());
        
        HIterator it = c.iterator();
        while (it.hasNext())
            assertTrue(map.containsValue(it.next()));
    }

    private static void fill(MapAdapter m)
    {
        m.put("uno", 1);
        m.put("due", 2);
        m.put("tre", 3);
        m.put("quattro", 4);
        m.put(5,"cinque");
        m.put(6,"sei");
        m.put(7,"sette");
        m.put(8,"otto");
    }
}
