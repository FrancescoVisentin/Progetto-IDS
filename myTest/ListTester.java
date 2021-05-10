package myTest;

import myAdapter.*;
import myLib.NoSuchElementException;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ListTester
{
    private ListAdapter list;

    @Before
    public void initialize()
    {
        list = new ListAdapter();
    }

    /**
     * Test del metodo add(int index, Object o)
     */
    @Test
    public void addIndexTest()
    {
        assertTrue(list.isEmpty());

        list.add(0, "uno");
        assertEquals(list.size(), 1);
        list.add(1, 2);
        assertEquals(list.size(), 2);
        list.add(0, "tre");
        list.add(2, "quattro");
        assertEquals(list.size(), 4);

        assertEquals(list.get(0), "tre");
        assertEquals(list.get(1), "uno");
        assertEquals(list.get(2), "quattro");
        assertEquals(list.get(3), 2);

        assertTrue(list.contains("uno"));
        assertTrue(list.contains(2));
        assertTrue(list.contains("tre"));
    }

    /**
     * Test eccezioni del metodo add(Object o)
     */
    @Test
    public void addIndexExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.add(0, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(1, "valido");});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(-42, "valido");});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(2, null);});
        
        assertEquals(list.size(), 0);
        list.add(0, "");
        
        assertThrows(NullPointerException.class, () -> {list.add(1, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(2, "valido");});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(-42, "valido");});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(2, null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     *  Test del metodo add(Object o)
     */
    @Test
    public void addTest()
    {
        assertTrue(list.isEmpty());

        assertTrue(list.add("uno"));
        assertEquals(list.size(), 1);
        assertTrue(list.add(2));
        assertEquals(list.size(), 2);
        assertTrue(list.add("tre"));

        assertEquals(list.get(0), "uno");
        assertEquals(list.get(1), 2);
        assertEquals(list.get(2), "tre");

        assertTrue(list.contains("uno"));
        assertTrue(list.contains(2));
        assertTrue(list.contains("tre"));
    }

    /**
     * Test eccezioni del metodo add(Object o)
     */
    @Test
    public void addExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.add(null);});
        assertEquals(list.size(), 0);
        assertTrue(list.add(""));
        assertThrows(NullPointerException.class, () -> {list.add(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }
    
    /**
     *  Test del metodo addAll(HCollection c)
     */
    @Test
    public void addAllTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        assertTrue(list.addAll(c));
        assertEquals(list.size(), 8);
        assertEquals(list.size(), c.size());

        list.add("");
        assertEquals(list.size(), c.size() + 1);

        assertTrue(list.addAll(c));
        assertEquals(list.size(), 2*c.size() + 1);

        HIterator it = c.iterator();
        for (int i = 0; i < c.size(); i++)
            assertEquals(it.next(), list.get(i));

        assertFalse(list.addAll(new ListAdapter()));
        assertEquals(list.size(), 17);
    }

    /**
     * Test eccezioni del metodo addAll(HCollection c)
     */
    @Test
    public void addAllExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.addAll(null);});
        assertEquals(list.size(), 0);
        assertTrue(list.add(""));
        assertThrows(NullPointerException.class, () -> {list.addAll(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     *  Test del metodo addAll(int index, Hcollection c)
     */
    @Test
    public void addAllIndexTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        assertTrue(list.addAll(0, c));
        assertEquals(list.size(), 8);
        assertEquals(list.size(), c.size());

        list.add("");
        assertEquals(list.size(), c.size() + 1);

        assertTrue(list.addAll(3, c));
        assertEquals(list.size(), 2*c.size() + 1);

        HIterator it = c.iterator();
        for (int i = 0; i < c.size(); i++)
            assertEquals(it.next(), list.get(i+3));

        assertFalse(list.addAll(7, new ListAdapter()));
        assertFalse(list.addAll(list.size(), new ListAdapter()));   
        assertEquals(list.size(), 17);
    }

    /**
     * Test eccezioni del metodo addAll(int index, HCollection c)
     */
    @Test
    public void addAllIndexExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.addAll(0, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.addAll(1, new ListAdapter());});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(-42, new ListAdapter());});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(2, null);});
        
        assertEquals(list.size(), 0);
        list.add(0, "");
        
        assertThrows(NullPointerException.class, () -> {list.addAll(1, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.addAll(2, new ListAdapter());});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(-42, new ListAdapter());});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(2, null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     * Test del metodo clear()
     */
    @Test
    public void clearTest()
    {
        assertTrue(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        list.addAll(c);

        list.clear();
        assertTrue(list.isEmpty());

        HIterator it = c.iterator();
        while(it.hasNext())    
            assertFalse(list.contains(it.next()));
    }

    /**
     * Test del metodo contains(Object o)
     */
    @Test
    public void containsTest()
    {
        assertTrue(list.isEmpty());

        assertFalse(list.contains(""));

        list.add("uno");
        assertTrue(list.contains("uno"));
        list.add(0, "due");
        list.add(1, "tre");
        assertTrue(list.contains("uno"));
        assertTrue(list.contains("due"));
        assertTrue(list.contains("tre"));

        list.clear();

        assertFalse(list.contains("uno"));
        assertFalse(list.contains("due"));
        assertFalse(list.contains("tre"));
    }

    /**
     * Test delle eccezioni del metodo contains(Object o)
     */
    @Test
    public void containsExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.contains(null);});
        assertEquals(list.size(), 0);
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.contains(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "");
    }

    /**
     * Test del metodo containsAll(HCollection c)
     */
    @Test
    public void containsAll()
    {
        assertTrue(list.isEmpty());

        assertTrue(list.containsAll(new ListAdapter()));

        HCollection c = makeCollection();
        list.addAll(c);
        assertTrue(list.containsAll(c));

        list.remove(4);
        list.remove(6);
        assertFalse(list.containsAll(c));
        list.addAll(c);
        assertTrue(list.containsAll(c));

        list.clear();
        assertFalse(list.containsAll(c));
    }

    /**
     * Test delle eccezioni del metodo containsAll(HCollection c)
     */
    @Test
    public void containsAllExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.containsAll(null);});
        assertEquals(list.size(), 0);
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.containsAll(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     * Test del metodo equals(Object o)
     */
    @Test
    public void equalsTest()
    {
        assertTrue(list.isEmpty());

        assertFalse(list.equals(null));
        assertTrue(list.equals(new ListAdapter()));
        assertFalse(list.equals(new MapAdapter()));

        HCollection c = makeCollection();
        list.addAll(c);
        ListAdapter l = new ListAdapter();
        l.addAll(c);

        assertTrue(list.equals(l));
        assertTrue(l.equals(list));
        l.add(l.get(4));
        l.remove(4);
        assertFalse(list.equals(l));
    }

    /**
     * Test del metodo get(int index)
     */
    @Test
    public void getTest()
    {
        assertTrue(list.isEmpty());

        list.addAll(makeCollection());
        assertEquals(list.get(3), "quattro");
        assertEquals(list.get(list.size()-1), 8);
        list.remove(0);
        assertEquals(list.get(list.size()-1), 8);
    }

    /**
     * Test delle eccezioni del metodo get(int index)
     */
    @Test
    public void getExceptionsTest()
    {
        assertTrue(list.isEmpty());

        assertThrows(IndexOutOfBoundsException.class, () -> {list.get(0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.get(-1);});

        list.add("");
        assertThrows(IndexOutOfBoundsException.class, () -> {list.get(list.size());});
    }

    /**
     * Test del metodo hashCode()
     */
    @Test
    public void hashCodeTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        list.addAll(c);
        ListAdapter l = new ListAdapter();
        l.addAll(c);

        assertEquals(l.hashCode(), list.hashCode());
        l.add("");
        assertNotEquals(l.hashCode(), list.hashCode());
        l.remove("o");
        l.set(0,"due");
        l.set(1,"uno");
        assertNotEquals(l.hashCode(), list.hashCode());

        list.clear();
        l.clear();
        assertEquals(l.hashCode(), list.hashCode());
    }

    /**
     * Test del metodo indexOf(Object o)
     */
    @Test
    public void indexOfTest()
    {
        assertTrue(list.isEmpty());

        list.addAll(makeCollection());
        assertEquals(list.indexOf("due"), 1);
        assertEquals(list.indexOf(6), 5);
        
        list.add(2,6);
        assertEquals(list.indexOf(6), 2);
        assertEquals(list.indexOf(""), -1);

        list.clear();
        assertEquals(list.indexOf(6), -1);
    }

    /**
     * Test delle eccezioni del metodo indexOf(Object o)
     */
    @Test
    public void indexOfExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.indexOf(null);});
        assertEquals(list.size(), 0);
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.indexOf(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     * Test del metodo isEmpty()
     */
    @Test
    public void isEmptyTest()
    {
        assertEquals(list.size(), 0);
        assertTrue(list.isEmpty());

        list.add("");
        assertEquals(list.size(), 1);
        assertFalse(list.isEmpty());

        list.clear();
        assertTrue(list.isEmpty());
    }

    /**
     * Test del metodo hasNext() di Iter implements HIterator
     */
    @Test
    public void IteratorHasNextTest()
    {
        assertTrue(list.isEmpty());
        HIterator it = list.iterator();
        assertFalse(it.hasNext());

        list.addAll(makeCollection());
        it = list.iterator();
        for(int i = 0; i < list.size(); i++)
        {
            assertTrue(it.hasNext());
            it.next();
        }

        assertFalse(it.hasNext());
    }

    /**
     * Test del metodo next() di Iter implements HIterator
     */
    @Test
    public void iteratorNextTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        list.addAll(c);
        HIterator it = list.iterator();

        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            assertEquals(it.next(), list.get(i));
        }

        assertThrows(NoSuchElementException.class, () -> {it.next();});
    }

    /**
     * Test del metodo remove() di Iter implements HIterator 
     */
    @Test
    public void IteratorRemoveTest()
    {
        assertTrue(list.isEmpty());

        list.addAll(makeCollection());
        HIterator it = list.iterator();
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            Object o = it.next();
            assertTrue(list.contains(o));
            it.remove();
            assertFalse(list.contains(o));
        }

        assertTrue(list.isEmpty());
    }

    /**
     * Test delle eccezioni del metodo remove() di Iter implements HIterator
     */
    @Test
    public void IteratorRemoveExceptionsTest()
    {
        HIterator it = list.iterator();

        assertThrows(IllegalStateException.class, () -> {it.remove();});

        list.addAll(makeCollection());
        assertThrows(IllegalStateException.class, () -> {it.remove();});

        HIterator it2 = list.iterator();
        it2.next();
        it2.remove();
        assertThrows(IllegalStateException.class, () -> {it2.remove();});
    }






    //metodi di supporto 
    private HCollection makeCollection()
    {
        ListAdapter ret = new ListAdapter();
        ret.add("uno");
        ret.add("due");
        ret.add("tre");
        ret.add("quattro");
        ret.add(5);
        ret.add(6);
        ret.add(7);
        ret.add(8);

        return ret;
    }

}

