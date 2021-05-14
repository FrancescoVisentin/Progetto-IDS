package myTest;

import myAdapter.*;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class SetTester
{
    private MapAdapter map;
    private HSet set;

    @Before
    public void initialize()
    {
        map = new MapAdapter();
        fill(map);
        set = map.entrySet();
    }

    /**
     * Test dei metodi add(Object o) ed addAll(HCollection c) di EntrySet, KeySet, ValueSet implements HSet
     */
    @Test
    public void setAddTest()
    {
        assertEquals(set.size(), 8);
        assertThrows(UnsupportedOperationException.class, () -> {set.add(null);});
        assertThrows(UnsupportedOperationException.class, () -> {set.addAll(new ListAdapter());});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(UnsupportedOperationException.class, () -> {set.add(null);});
        assertThrows(UnsupportedOperationException.class, () -> {set.addAll(new ListAdapter());});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo clear() di EntrySet implements HSet
     */
    @Test
    public void entrySetClearTest()
    {
        assertEquals(set.size(), 8);
        assertTrue(set.contains(getEntry("uno", 1)));
        assertTrue(map.containsKey("uno"));

        set.clear();
        assertEquals(set.size(), 0);
        assertEquals(map.size(), 0);
        assertFalse(set.contains(getEntry("uno", 1)));
        assertFalse(map.containsKey("uno"));

        set.clear();
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo clear() di KeySet implements HSet
     */
    @Test
    public void keySetClearTest()
    {
        set = map.keySet();

        assertEquals(set.size(), 8);
        assertTrue(set.contains("uno"));
        assertTrue(map.containsKey("uno"));

        set.clear();
        assertEquals(set.size(), 0);
        assertEquals(map.size(), 0);
        assertFalse(set.contains("uno"));
        assertFalse(map.containsKey("uno"));

        set.clear();
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo clear() di ValueSet implements HSet
     */
    @Test
    public void valueSetClearTest()
    {
        set = (HSet)map.values();

        assertEquals(set.size(), 8);
        assertTrue(set.contains(1));
        assertTrue(map.containsValue(1));

        set.clear();
        assertEquals(set.size(), 0);
        assertEquals(map.size(), 0);
        assertFalse(set.contains(1));
        assertFalse(map.containsValue(1));

        set.clear();
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo contains(Object o) di EntrySet implements HSet
     */
    @Test
    public void entrySetContainsTest()
    {
        assertEquals(set.size(), 8);
        assertTrue(set.contains(getEntry("due", 2)));
        assertEquals(map.get("due"), 2);

        assertFalse(set.contains(new ListAdapter()));

        assertFalse(set.contains(getEntry(2, "due")));
        assertEquals(map.get(2), null);

        assertEquals(set.size(), 8);
        set.clear();

        assertFalse(set.contains(getEntry("due", 2)));
        assertEquals(map.get("due"), null);
    }

    /**
     * Test delle eccezioni del metodo contains(Object o) di EntrySet implements HSet
     */
    @Test
    public void entrySetContainsExceptionsTest()
    {
        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.contains(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.contains(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo contains(Object o) di KeySet implements HSet
     */
    @Test
    public void keySetContainsTest()
    {
        set = map.keySet();

        assertEquals(set.size(), 8);
        assertTrue(set.contains("due"));
        assertTrue(map.containsKey("due"));

        assertFalse(set.contains(2));
        assertEquals(map.get(2), null);

        assertEquals(set.size(), 8);
        set.clear();

        assertFalse(set.contains("due"));
        assertEquals(map.get("due"), null);
    }

    /**
     * Test delle eccezioni del metodo contains(Object o) di KeySet implements HSet
     */
    @Test
    public void keySetContainsExceptionsTest()
    {
        set = map.keySet();

        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.contains(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.contains(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo contains(Object o) di ValueSet implements HSet
     */
    @Test
    public void valueSetContainsTest()
    {
        set = (HSet)map.values();

        assertEquals(set.size(), 8);
        assertTrue(set.contains(2));
        assertTrue(map.containsValue(2));

        assertFalse(set.contains("due"));
        assertFalse(map.containsValue("due"));

        assertEquals(set.size(), 8);
        set.clear();

        assertFalse(set.contains(2));
        assertFalse(map.containsValue(2));
    }

    /**
     * Test delle eccezioni del metodo contains(Object o) di ValueSet implements HSet
     */
    @Test
    public void valueSetContainsExceptionsTest()
    {
        set = (HSet)map.values();

        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.contains(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.contains(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo containsAll(HCollection c) di EntrySet implements HSet
     */
    @Test
    public void entrySetContainsAllTest()
    {
        MapAdapter m = new MapAdapter();
        HSet s = m.entrySet();

        assertTrue(s.isEmpty());
        assertTrue(set.containsAll(s));

        fill(m);
        assertTrue(set.containsAll(s));
        
        set.remove(getEntry("tre", 3));
        assertFalse(set.containsAll(s));

        s.remove(getEntry("tre", 3));
        s.remove(getEntry("uno", 1));
        assertTrue(set.containsAll(s));

        set.clear();
        s.clear();
        assertTrue(set.containsAll(s));
    }

    /**
     * Test del metodo containsAll(HCollection c) di KeySet implements HSet
     */
    @Test
    public void keySetContainsAllTest()
    {
        set = map.keySet();

        MapAdapter m = new MapAdapter();
        HSet s = m.keySet();

        assertTrue(s.isEmpty());
        assertTrue(set.containsAll(s));

        fill(m);
        assertTrue(set.containsAll(s));
        
        set.remove("tre");
        assertFalse(set.containsAll(s));

        s.remove("tre");
        s.remove("uno");
        assertTrue(set.containsAll(s));

        set.clear();
        s.clear();
        assertTrue(set.containsAll(s));
    }

    /**
     * Test del metodo containsAll(HCollection c) di ValueSet implements HSet
     */
    @Test
    public void valueSetContainsAllTest()
    {
        HSet set = (HSet)map.values();

        MapAdapter m = new MapAdapter();
        HSet s = (HSet)m.values();

        assertTrue(s.isEmpty());
        assertTrue(set.containsAll(s));

        fill(m);
        assertTrue(set.containsAll(s));
        
        set.remove(3);
        assertFalse(set.containsAll(s));

        s.remove(3);
        s.remove(1);
        assertTrue(set.containsAll(s));

        set.clear();
        s.clear();
        assertTrue(set.containsAll(s));
    }

    /**
     * Test delle eccezioni del metodo containsAll(HCollection c) di EntrySet, KeySet, ValueSet implements HSet
     */
    @Test
    public void setContainsAllExceptionsTest()
    {
        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.containsAll(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.containsAll(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo equals(Object o) di EntrySet implements HSet
     */
    @Test
    public void entrySetEqualsTest()
    {
        assertFalse(set.equals(null));
        assertFalse(set.equals(new MapAdapter()));
        
        MapAdapter m = new MapAdapter();
        HSet s = m.entrySet();

        assertFalse(set.equals(s));

        fill(m);
        assertTrue(set.equals(s));
        assertTrue(s.equals(set));
        assertEquals(set.size(), s.size());

        map.put("uno", 0);
        assertFalse(set.equals(s));

        set.clear();
        s.clear();
        assertTrue(set.equals(s));
    }

    /**
     * Test del metodo equals(Object o) di KeySet implements HSet
     */
    @Test
    public void keySetEqualsTest()
    {
        set = map.keySet();

        assertFalse(set.equals(null));
        assertFalse(set.equals(new MapAdapter()));
        
        MapAdapter m = new MapAdapter();
        HSet s = m.keySet();

        assertFalse(set.equals(s));

        fill(m);
        assertTrue(set.equals(s));
        assertTrue(s.equals(set));
        assertEquals(set.size(), s.size());

        map.put("uno", 0);
        assertTrue(set.equals(s));

        set.clear();
        s.clear();
        assertTrue(set.equals(s));
    }

    /**
     * Test del metodo equals(Object o) di ValueSet implements HSet
     */
    @Test
    public void valueSetEqualsTest()
    {
        set = (HSet)map.values();

        assertFalse(set.equals(null));
        assertFalse(set.equals(new MapAdapter()));
        
        MapAdapter m = new MapAdapter();
        HSet s = (HSet)m.values();

        assertFalse(set.equals(s));

        fill(m);
        assertTrue(set.equals(s));
        assertTrue(s.equals(set));
        assertEquals(set.size(), s.size());

        map.put("uno", 0);
        assertFalse(set.equals(s));

        set.clear();
        s.clear();
        assertTrue(set.equals(s));
    }

    /**
     * Test del metodo hashCode() di EntrySet implements HSet
     */
    @Test
    public void entrySetHashCodeTest()
    {
        MapAdapter m = new MapAdapter();
        HSet s = m.entrySet();

        fill(map);
        fill(m);

        assertTrue(set.equals(s));
        assertEquals(s.hashCode(), set.hashCode());

        set.remove(getEntry("uno", 1));
        assertNotEquals(set.hashCode(), s.hashCode());

        s.remove(getEntry("uno", 1));
        assertEquals(map.hashCode(), m.hashCode());
        
        map.clear();
        m.clear();
        assertEquals(set.hashCode(), s.hashCode());
        assertEquals(set.hashCode(), 0);
    }

    /**
     * Test del metodo hashCode() di KeySet implements HSet
     */
    @Test
    public void keySetHashCodeTest()
    {
        set = map.keySet();

        MapAdapter m = new MapAdapter();
        HSet s = m.keySet();

        fill(map);
        fill(m);

        assertTrue(set.equals(s));
        assertEquals(s.hashCode(), set.hashCode());

        set.remove("uno");
        assertNotEquals(set.hashCode(), s.hashCode());

        s.remove("uno");
        assertEquals(map.hashCode(), m.hashCode());
        
        map.clear();
        m.clear();
        assertEquals(set.hashCode(), s.hashCode());
        assertEquals(set.hashCode(), 0);
    }

    /**
     * Test del metodo hashCode() di ValueSet implements HSet
     */
    @Test
    public void valueSetHashCodeTest()
    {
        set = (HSet)map.values();

        MapAdapter m = new MapAdapter();
        HSet s = (HSet)m.values();

        fill(map);
        fill(m);

        assertTrue(set.equals(s));
        assertEquals(s.hashCode(), set.hashCode());

        set.remove(1);
        assertNotEquals(set.hashCode(), s.hashCode());

        s.remove(1);
        assertEquals(map.hashCode(), m.hashCode());
        
        map.clear();
        m.clear();
        assertEquals(set.hashCode(), s.hashCode());
        assertEquals(set.hashCode(), 0);
    }

    /**
     * Test del metodo isEmpty() di EntrySet, KeySet, ValueSet implements HSet
     */
    @Test
    public void setIsEmptyTEst()
    {
        assertEquals(set.size() , 8);
        assertFalse(set.isEmpty());

        set.clear();
        assertEquals(set.size() , 0);
        assertTrue(set.isEmpty());
        assertTrue(map.isEmpty());
    }

    /**
     * Test del metodo iterator() di EntrySet, KeySet, ValueSet implements HSet
     */
    @Test
    public void setIteratorTest()
    {
        assertEquals(set.size(), 8);
        HIterator it = set.iterator();
        Object o = it.next();
        assertTrue(set.contains(o));
        assertTrue(o instanceof HMap.Entry);

        set = map.keySet();
        it = set.iterator();
        o = it.next();
        assertTrue(set.contains(o));
        assertTrue(map.containsKey(o));

        set = (HSet)map.values();
        it = set.iterator();
        o = it.next();
        assertTrue(set.contains(o));
        assertTrue(map.containsValue(o));

        map.clear();
        it = set.iterator();
        set = map.keySet();
        it = set.iterator();
        set = map.entrySet();
        it = set.iterator();
    }

    /**
     * Test del metodo remove(Object o) di EntrySet implements HSet 
     */
    @Test
    public void entrySetRemoveTest()
    {
        assertEquals(set.size(), 8);

        assertTrue(set.remove(getEntry("uno", 1)));
        assertFalse(set.contains(getEntry("uno", 1)));
        assertFalse(map.containsKey("uno"));
        assertEquals(map.size(), 7);
        assertEquals(map.size(), 7);

        assertFalse(set.remove(getEntry("uno", 1)));
        assertEquals(map.size(), 7);
        assertEquals(map.size(), 7);

        assertFalse(set.remove(new MapAdapter()));

        Object[] a = set.toArray();
        for (Object e : a)
            assertTrue(set.remove((HMap.Entry)e));

        assertTrue(set.isEmpty());
    }

    /**
     * Test delle eccezioni del metodo remove(Object o) di EntrySet implements HSet 
     */
    @Test
    public void entrySetRemoveExceptionsTest()
    {
        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.remove(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.remove(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo remove(Object o) di KeySet implements HSet 
     */
    @Test
    public void keySetRemoveTest()
    {
        set = map.keySet();

        assertEquals(set.size(), 8);

        assertTrue(set.remove("uno"));
        assertFalse(set.contains("uno"));
        assertFalse(map.containsKey("uno"));
        assertEquals(map.size(), 7);
        assertEquals(map.size(), 7);

        assertFalse(set.remove("uno"));
        assertEquals(map.size(), 7);
        assertEquals(map.size(), 7);

        assertFalse(set.remove(new MapAdapter()));

        Object[] a = set.toArray();
        for (Object e : a)
            assertTrue(set.remove(e));

        assertTrue(set.isEmpty());
    }

    /**
     * Test delle eccezioni del metodo remove(Object o) di KeySet implements HSet 
     */
    @Test
    public void keySetRemoveExceptionsTest()
    {
        set = map.keySet();

        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.remove(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.remove(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo remove(Object o) di ValueSet implements HSet 
     */
    @Test
    public void valueSetRemoveTest()
    {
        set = (HSet)map.values();

        assertEquals(set.size(), 8);

        assertTrue(set.remove(1));
        assertFalse(set.contains(1));
        assertFalse(map.containsKey(1));
        assertEquals(map.size(), 7);
        assertEquals(map.size(), 7);

        assertFalse(set.remove(1));
        assertEquals(map.size(), 7);
        assertEquals(map.size(), 7);

        assertFalse(set.remove(new MapAdapter()));

        Object[] a = set.toArray();
        for (Object e : a)
            assertTrue(set.remove(e));

        assertTrue(set.isEmpty());
    }

    /**
     * Test delle eccezioni del metodo remove(Object o) di ValueSet implements HSet 
     */
    @Test
    public void valueSetRemoveExceptionsTest()
    {
        set = (HSet)map.values();

        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.remove(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.remove(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo removeAll(HCollection c) di EntrySet implements HSet
     */
    @Test
    public void entrySetRemoveAllTest()
    {
        MapAdapter m = new MapAdapter();
        HSet s = m.entrySet();

        assertFalse(set.removeAll(s));

        m.put("uno", 1);
        m.put(5, "cinque");
        m.put("", 0);
        assertTrue(set.removeAll(s));
        assertEquals(set.size(), 6);
        assertFalse(map.containsKey("uno"));
        assertFalse(map.containsKey(5));
        
        fill(m);

        assertTrue(set.removeAll(s));

        assertTrue(set.isEmpty());
        assertTrue(map.isEmpty());
    }

     /**
     * Test del metodo removeAll(HCollection c) di KeySet implements HSet
     */
    @Test
    public void keySetRemoveAllTest()
    {
        set = map.keySet();

        MapAdapter m = new MapAdapter();
        HSet s = m.keySet();

        assertFalse(set.removeAll(s));

        m.put("uno", 1);
        m.put(5, "cinque");
        m.put("", 0);
        assertTrue(set.removeAll(s));
        assertEquals(set.size(), 6);
        assertFalse(map.containsKey("uno"));
        assertFalse(map.containsKey(5));
        
        fill(m);

        assertTrue(set.removeAll(s));

        assertTrue(set.isEmpty());
        assertTrue(map.isEmpty());
    }

     /**
     * Test del metodo removeAll(HCollection c) di ValueSet implements HSet
     */
    @Test
    public void valueSetRemoveAllTest()
    {
        set = (HSet)map.values();

        MapAdapter m = new MapAdapter();
        HSet s = (HSet)m.values();

        assertFalse(set.removeAll(s));

        m.put("uno", 1);
        m.put(5, "cinque");
        m.put("", 0);
        assertTrue(set.removeAll(s));
        assertEquals(set.size(), 6);
        assertFalse(map.containsKey("uno"));
        assertFalse(map.containsKey(5));
        
        fill(m);

        assertTrue(set.removeAll(s));

        assertTrue(set.isEmpty());
        assertTrue(map.isEmpty());
    }

    /**
     * Test delle eccezioni del metodo removeAll(HCollection c) di EntrySet, KeySet, ValueSet implements HSet
     */
    @Test
    public void setRemoveAllExceptionsTest()
    {
        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.removeAll(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.removeAll(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo retainAll(HCollection c) di EntrySet implements HSet
     */
    @Test
    public void entrySetRetainAllTest()
    {
        MapAdapter m = new MapAdapter();
        HSet s = m.entrySet();

        fill(m);

        assertFalse(set.retainAll(s));
        assertEquals(set.size(), 8);

        s.clear();
        m.put("uno", 1);
        m.put(5, "cinque");
        m.put("due", 22);
        m.put("", 0);
        assertTrue(set.retainAll(s));
        assertEquals(set.size(), 2);
        assertTrue(map.containsKey("uno"));
        assertTrue(map.containsKey(5));
        assertFalse(map.containsKey("due"));

        s.clear();

        assertTrue(set.retainAll(s));

        assertTrue(set.isEmpty());
        assertTrue(map.isEmpty());
    }

    /**
     * Test del metodo retainAll(HCollection c) di KeySet implements HSet
     */
    @Test
    public void keySetRetainAllTest()
    {
        set = map.keySet();

        MapAdapter m = new MapAdapter();
        HSet s = m.keySet();

        fill(m);

        assertFalse(set.retainAll(s));
        assertEquals(set.size(), 8);

        s.clear();
        m.put("uno", 1);
        m.put(5, "cinque");
        m.put("duedue", 2);
        m.put("", 0);
        assertTrue(set.retainAll(s));
        assertEquals(set.size(), 2);
        assertTrue(map.containsKey("uno"));
        assertTrue(map.containsKey(5));
        assertFalse(map.containsKey("due"));

        s.clear();

        assertTrue(set.retainAll(s));

        assertTrue(set.isEmpty());
        assertTrue(map.isEmpty());
    }

    /**
     * Test del metodo retainAll(HCollection c) di ValueSet implements HSet
     */
    @Test
    public void valueSetRetainAllTest()
    {
        set = (HSet)map.values();

        MapAdapter m = new MapAdapter();
        HSet s = (HSet)m.values();

        fill(m);

        assertFalse(set.retainAll(s));
        assertEquals(set.size(), 8);

        s.clear();
        m.put("uno", 1);
        m.put(5, "cinque");
        m.put("due", 22);
        m.put("", 0);
        assertTrue(set.retainAll(s));
        assertEquals(set.size(), 2);
        assertTrue(map.containsKey("uno"));
        assertTrue(map.containsKey(5));
        assertFalse(map.containsKey("due"));

        s.clear();

        assertTrue(set.retainAll(s));

        assertTrue(set.isEmpty());
        assertTrue(map.isEmpty());
    }

    /**
     * Test delle eccezioni del metodo retainAll(HCollection c) di EntrySet, KeySet, ValueSet implements HSet
     */
    @Test
    public void setRetainAllExceptionsTest()
    {
        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.retainAll(null);});
        assertEquals(set.size(), 8);

        set.clear();
        assertThrows(NullPointerException.class, () -> {set.retainAll(null);});
        assertEquals(set.size(), 0);
    }

    /**
     * Test del metodo size() di EntrySet implements HSet
     */
    @Test
    public void entrySetSizeTest()
    {
        assertEquals(map.size(), 8);
        assertEquals(map.size(), 8);

        set.remove(getEntry("uno", 1));
        set.remove(getEntry("due", 2));
        assertEquals(set.size(), 6);
        assertEquals(map.size(), 6);

        map.put("", 0);
        assertEquals(set.size(), 7);
        assertEquals(map.size(), 7);

        set.clear();
        assertEquals(set.size(), 0);
        assertEquals(map.size(), 0);
    }

    /**
     * Test del metodo size() di KeySet implements HSet
     */
    @Test
    public void keySetSizeTest()
    {
        set = map.keySet();

        assertEquals(map.size(), 8);
        assertEquals(map.size(), 8);

        set.remove("uno");
        set.remove("due");
        assertEquals(set.size(), 6);
        assertEquals(map.size(), 6);

        map.put("", 0);
        assertEquals(set.size(), 7);
        assertEquals(map.size(), 7);

        set.clear();
        assertEquals(set.size(), 0);
        assertEquals(map.size(), 0);
    }

    /**
     * Test del metodo size() di ValueSet implements HSet
     */
    @Test
    public void valueSetSizeTest()
    {
        set = (HSet)map.values();

        assertEquals(map.size(), 8);
        assertEquals(map.size(), 8);

        set.remove(1);
        set.remove(2);
        assertEquals(set.size(), 6);
        assertEquals(map.size(), 6);

        map.put("", 0);
        assertEquals(set.size(), 7);
        assertEquals(map.size(), 7);

        set.clear();
        assertEquals(set.size(), 0);
        assertEquals(map.size(), 0);
    }

    /**
     * Test del metodo toArray() di EntrySet, KeySet, Value Set implements HSet
     */
    @Test
    public void setToArrayTest()
    {
        assertEquals(set.size(), 8);
        Object[] a = set.toArray();
        assertEquals(set.size(), 8);
        assertEquals(a.length, 8);

        for (Object o : a)
        {
            assertTrue(set.contains(o));
            assertTrue(set.remove(o));
        }

        assertTrue(set.isEmpty());

        a = set.toArray();
        assertEquals(a.length, 0);
    }

    /**
     * Test del metodo toArray(Object[] a) di EntrySet, KeySet, Value Set implements HSet
     */
    @Test
    public void setToArrayObjectTest()
    {
        Object[] a = new Object[2];
        assertNotEquals(set.size(), a.length);
        
        a = set.toArray(a);
        assertEquals(set.size(), a.length);
        for (Object o : a)
            assertTrue(set.contains(o));

        a = new Object[15];
        for (int i = 0; i < 15; i++)
            a[i] = i;

        assertNotEquals(set.size(), a.length);
        a = set.toArray(a);
        assertNotEquals(set.size(), a.length);
        assertEquals(a.length, 15);
        for (int i = 0; i < set.size(); i++)
        {
            assertTrue(set.contains(a[i]));
        }

        for (int i = set.size(); i < a.length; i++)
        {
            assertEquals(a[i], i);
        }
    }

    /**
     * Test delle eccezioni del metodo toArray(Object[] a) di EntrySet, KeySet, Value Set implements HSet
     */
    @Test
    public void setToArrayObjectExceptionsTest()
    {
        assertEquals(set.size(), 8);
        assertThrows(NullPointerException.class, () -> {set.toArray(null);});
        assertEquals(set.size(), 8);
        map.clear();
        assertThrows(NullPointerException.class, () -> {set.toArray(null);});
        assertEquals(set.size(), 0);

        fill(map);

        Integer[] i = new Integer[10];
        assertThrows(ArrayStoreException.class, () -> {set.toArray(i);});
    
        assertEquals(map.size(), 8);
        assertEquals(set.size(), 8);
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

    private static HMap.Entry getEntry(Object k, Object v)
    {
        MapAdapter m = new MapAdapter();
        m.put(k,v);
        return (HMap.Entry)m.entrySet().iterator().next();
    }
}
