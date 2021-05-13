package myTest;

import myAdapter.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class EntryTester
{
    private HMap.Entry e;

    /**
     * Test del metodo equals() di HEntry implements HMap.Entry
     */
    @Test 
    public void entryEqualsTest()
    {
        e = getEntry("uno", 1);
        HMap.Entry e2 = getEntry("uno", 1);

        assertTrue(e.equals(e2));
        assertTrue(e2.equals(e));

        e2 = getEntry(1, "uno");
        assertFalse(e.equals(e2));
        assertFalse(e2.equals(e));

        e2 = getEntry("", 0);
        assertFalse(e.equals(e2));
        assertFalse(e2.equals(e));

        assertFalse(e.equals(null));
        assertFalse(e.equals(new MapAdapter()));

    }

    /**
     * Test dei metodi getKey() e getValue() di HEntry implements HMap.Entry
     */
    @Test
    public void entryGettersTest()
    {
        e = getEntry("uno", 1);
        assertEquals(e.getKey(), "uno");
        assertEquals(e.getValue(), 1);
    }

    /**
     * Test del metodo hashCode() di HEntry implements HMap.Entry
     */
    @Test
    public void entryHashCodeTest()
    {
        e = getEntry("uno", 1);
        HMap.Entry e2 = getEntry("uno", 1);
        assertTrue(e.equals(e2));
        assertEquals(e.hashCode(), e2.hashCode());

        e.setValue(0);
        e2.setValue(0);
        assertTrue(e.equals(e2));
        assertEquals(e.hashCode(), e2.hashCode());
    }

    /**
     * Test del metodo setValue(Object o) di Hentry implements HMap.Entry
     */
    @Test
    public void entrySetValueTest()
    {
        MapAdapter m = new MapAdapter();
        m.put("uno", 1);
        e = (HMap.Entry)m.entrySet().iterator().next();
    
        assertEquals(e.setValue(11), 1);
        assertEquals(e.getKey(), "uno");
        assertEquals(e.getValue(), 11);

        assertTrue(m.containsKey("uno"));
        assertTrue(m.containsValue(11));
        assertFalse(m.containsValue(1));
    }

    private static HMap.Entry getEntry(Object k, Object v)
    {
        MapAdapter m = new MapAdapter();
        m.put(k,v);
        return (HMap.Entry)m.entrySet().iterator().next();
    }
}
