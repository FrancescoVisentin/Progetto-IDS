package myTest;

import myAdapter.*;
import myLib.NoSuchElementException;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class SetIteratorTester
{
    private MapAdapter map;
    private HSet set;

    @Before
    public void initialize()
    {
        map = new MapAdapter();
        set = map.entrySet();
    }

    /**
     * Test del metodo hasNext() di EntryIterator, KeyValIterator implements HIterator
     */
    @Test
    public void setIteratorHasNextTest()
    {
        assertTrue(set.isEmpty());
        HIterator it = set.iterator();
        assertFalse(it.hasNext());

        fill(map);
        it = set.iterator();
        for(int i = 0; i < set.size(); i++)
        {
            assertTrue(it.hasNext());
            it.next();
        }

        assertFalse(it.hasNext());
    }

    /**
     * Test del metodo remove() di EntryIterator, KeyValIterator implements HIterator
     */
    @Test
    public void setIteratorRemoveTest()
    {
        assertTrue(set.isEmpty());

        fill(map);
        HIterator it = set.iterator();
        int size = set.size();
        for (int i = 0; i < size; i++)
        {
            Object o = it.next();
            assertTrue(set.contains(o));
            it.remove();
            assertFalse(set.contains(o));
        }

        assertTrue(set.isEmpty());
        assertTrue(map.isEmpty());
    }

    /**
     * Test delle eccezioni del metodo remove() di EntryIterator, KeyValIterator implements HIterator
     */
    @Test
    public void setIteratorRemoveExceptionsTest()
    {
        HIterator it = set.iterator();

        assertThrows(IllegalStateException.class, () -> {it.remove();});

        fill(map);
        HIterator it2 = set.iterator();
        assertThrows(IllegalStateException.class, () -> {it2.remove();});
        it2.next();
        it2.remove();
        assertThrows(IllegalStateException.class, () -> {it2.remove();});
        assertEquals(set.size(), 7);
    }

    /**
     * Test del metodo next() di EntryIterators implements HIterator
     */
    @Test
    public void setEntryIteratorNextTest()
    {
        assertTrue(set.isEmpty());

        fill(map);
        HIterator it = set.iterator();

        int size = set.size();
        for (int i = 0; i < size; i++)
        {
            Object o = it.next();
            assertTrue(o instanceof HMap.Entry);
            assertTrue(set.contains(o));
        }

        assertThrows(NoSuchElementException.class, () -> {it.next();});
    }

    /**
     * Test del metodo next() di KeyValIterator implements HIterator
     */
    @Test
    public void setKeyValIteratorNextTest()
    {
        set = map.keySet();
        assertTrue(set.isEmpty());

        fill(map);
        HIterator it = set.iterator();

        int size = set.size();
        for (int i = 0; i < size; i++)
        {
            Object o = it.next();
            assertTrue(set.contains(o));
            assertTrue(map.containsKey(o));
        }

        assertThrows(NoSuchElementException.class, () -> {it.next();});

        set = (HSet)map.values();
        HIterator it2 = set.iterator();
        size = set.size();
        for (int i = 0; i < size; i++)
        {
            Object o = it2.next();
            assertTrue(set.contains(o));
            assertTrue(map.containsValue(o));
        }

        assertThrows(NoSuchElementException.class, () -> {it2.next();});
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
