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
        //Inizializza un oggetto ListAdapter di tipo "lista base" su cui effettura i test.
        list = new ListAdapter();
        

        //Inizializza un oggetto ListAdapter di tipo "baselist" su cui effettura i test.
        /*
        ListAdapter baseList = new ListAdapter();
        baseList.addAll(makeCollection());
        list = (ListAdapter)baseList.subList(5,5);
        */
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
     * Test eccezioni del metodo add(int index, Object o)
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
    public void iteratorHasNextTest()
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
    public void iteratorRemoveTest()
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
    public void iteratorRemoveExceptionsTest()
    {
        HIterator it = list.iterator();

        assertThrows(IllegalStateException.class, () -> {it.remove();});

        list.addAll(makeCollection());
        assertThrows(IllegalStateException.class, () -> {it.remove();});

        HIterator it2 = list.iterator();
        assertThrows(IllegalStateException.class, () -> {it2.remove();});
        it2.next();
        it2.remove();
        assertThrows(IllegalStateException.class, () -> {it2.remove();});
    }

    /**
     * Test del metodo lastIndexOf()
     */
    @Test
    public void lastIndexOfTest()
    {
        assertTrue(list.isEmpty());

        list.addAll(makeCollection());

        assertEquals(list.lastIndexOf(5), 4);
        assertEquals(list.lastIndexOf("uno"), 0);

        list.addAll(makeCollection());
        assertEquals(list.lastIndexOf(5), 12);
        assertEquals(list.lastIndexOf("uno"), 8);

        assertEquals(list.lastIndexOf(""), -1);
        list.clear();
        assertEquals(list.lastIndexOf("uno"), -1);
    }

    /**
     * Test delle eccezioni del metodo lastIndexOf()
     */
    @Test
    public void lastIndexOfExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.lastIndexOf(null);});
        assertEquals(list.size(), 0);
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.lastIndexOf(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");  
    }

    /**
     * Test delle eccezioni del metodo listIterator(int index) 
     */
    @Test
    public void listIteratorExceptionsTest()
    {
        assertTrue(list.isEmpty());

        assertThrows(IndexOutOfBoundsException.class, () -> {list.listIterator(1);});

        list.add("");
        assertThrows(IndexOutOfBoundsException.class, () -> {list.listIterator(list.size()+1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.listIterator(-1);});
    }

    /**
     * Test del metodo add(Object o) di ListIter implements HListIterator
     */
    @Test
    public void listIteratorAddTest()
    {
        assertTrue(list.isEmpty());

        HListIterator it = list.listIterator();
        assertFalse(it.hasNext());
        it.add("");
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "");
        assertTrue(it.hasPrevious());
        assertFalse(it.hasNext());

        list.clear();
        list.addAll(makeCollection());

        it = list.listIterator(3);
        it.add("cinque");
        it.add("sei");
        assertTrue(it.hasPrevious());
        assertEquals(list.size(), 10);
        assertEquals(list.get(3), "cinque");
        assertEquals(list.get(4), "sei");
        assertEquals(list.get(5), it.next());
    }

    /**
     * Test delle eccezioni di add(Object o) di ListIter implements HListIterator
     */
    @Test
    public void listIteratorAddExceptionsTest()
    {
        assertTrue(list.isEmpty());

        HListIterator it = list.listIterator();
        assertThrows(NullPointerException.class, () -> {it.add(null);});
        assertEquals(list.size(), 0);
        
        list.add("");
        HListIterator it2 = list.listIterator();
        assertThrows(IllegalStateException.class, () -> {it.add(null);});
        assertThrows(NullPointerException.class, () -> {it2.add(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     * Test del metodo hasPrevious() di ListIter implements HListIterator
     */
    @Test
    public void listIteratorHasPreviousTest()
    {
        assertTrue(list.isEmpty());

        HListIterator it = list.listIterator();
        assertFalse(it.hasNext());

        list.addAll(makeCollection());
        it = list.listIterator(list.size());
        for(int i = list.size(); i > 0; i--)
        {
            assertTrue(it.hasPrevious());
            it.previous();
        }
        assertFalse(it.hasPrevious());
        assertTrue(it.hasNext());
    }

    /**
     * Test del metodo next() di ListIter implements HListIterator
     */
    @Test
    public void listIteratorNextTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        list.addAll(c);
        HListIterator it = list.listIterator();

        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            assertEquals(it.next(), list.get(i));
        }

        assertThrows(NoSuchElementException.class, () -> {it.next();});
    }

    /**
     * Test del metodo nextIndex() di ListIter implements HListIterator 
     */
    @Test
    public void listIteratorNextIndexTest()
    {
        assertTrue(list.isEmpty());

        HListIterator it = list.listIterator();
        assertEquals(it.nextIndex(), 0);

        list.addAll(makeCollection());
        it = list.listIterator(3);

        for (int i = 3; i < list.size(); i++)
        {
            assertEquals(it.nextIndex(), i);
            assertEquals(list.get(i), it.next());
        }

        assertEquals(list.size(), it.nextIndex());
    }

    /**
     * Test del metodo previous() di ListIter implements HListIterator 
     */
    @Test
    public void listIteratorPreviousTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        list.addAll(c);
        HListIterator it = list.listIterator(list.size());

        for (int i = list.size()-1; i >= 0; i--)
        {
            assertEquals(it.previous(), list.get(i));
        }

        assertThrows(NoSuchElementException.class, () -> {it.previous();});
    }

    /**
     * Test del metodo previousIndex() di ListIter implements HListIterator 
     */
    @Test
    public void listIteratorPreviousIndexTest()
    {
        assertTrue(list.isEmpty());

        HListIterator it = list.listIterator(0);
        assertEquals(it.previousIndex(), -1);

        list.addAll(makeCollection());
        it = list.listIterator(7);

        for (int i = 6; i >= 0; i--)
        {
            assertEquals(it.previousIndex(), i);
            assertEquals(list.get(i), it.previous());
        }

        assertEquals(-1, it.previousIndex());
    }

    /**
     * Test del metodo remove() di ListIter implements HListIterator 
     */
    @Test
    public void listIteratorRemoveTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        list.addAll(c);
        HListIterator it = list.listIterator(4);

        while (it.hasNext())
        {
            Object o = it.next();
            it.remove();
            assertFalse(list.contains(o));
        }

        while (it.hasPrevious())
        {
            Object o = it.previous();
            it.remove();
            assertFalse(list.contains(o));
        }

        assertEquals(list.size(), 0);

        for (Object o : c.toArray())
            it.add(o);

        assertThrows(IllegalStateException.class, () -> {it.remove();});
        it.previous();
        it.remove();
        assertThrows(IllegalStateException.class, () -> {it.remove();});

        it.previous();
        list.add("");
        assertThrows(IllegalStateException.class, () -> {it.remove();});
    }

    /**
     * Tes del metodo set(Object o) di ListIter implements HListIterator 
     */
    @Test
    public void listIteratorSetTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        list.addAll(c);
        HListIterator it = list.listIterator(4);

        while (it.hasNext())
        {
            Object o = it.next();
            it.set("next");
            assertFalse(list.contains(o));
            assertEquals(list.get(it.previousIndex()), "next");
        }

        while (list.get(it.previousIndex()).equals("next"))
            it.previous();

        while (it.hasPrevious())
        {
            Object o = it.previous();
            it.set("prev");
            assertFalse(list.contains(o));
            assertEquals(list.get(it.nextIndex()), "prev");
        }

        assertEquals(list.size(), 8);

        for (Object o : c.toArray())
            it.add(o);

        assertThrows(IllegalStateException.class, () -> {it.set("");});
        assertThrows(IllegalStateException.class, () -> {it.set(null);});
        it.previous();
        assertThrows(NullPointerException.class, () -> {it.set(null);});

        it.previous();
        list.add("");
        assertThrows(IllegalStateException.class, () -> {it.set("");});   
    }

    /**
     * Test del metodo remove(int index)
     */
    @Test
    public void removeIndexTest()
    {
        assertTrue(list.isEmpty());

        list.addAll(makeCollection());

        Object o = list.get(4);
        Object r = list.remove(3);
        assertFalse(list.contains(r));
        assertEquals(list.get(3), o);
        assertEquals(list.size(), 7);

        while(!list.isEmpty())
            assertFalse(list.contains(list.remove(list.size()-1)));

        assertTrue(list.isEmpty());
    }

    /**
     * Test delle eccezioni del metodo remove(int index) 
     */
    @Test
    public void removeIndexExceptionsTest()
    {
        assertTrue(list.isEmpty());

        assertThrows(IndexOutOfBoundsException.class, () -> {list.remove(1);});

        list.add("");
        assertThrows(IndexOutOfBoundsException.class, () -> {list.remove(list.size());});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.remove(-1);});
    }

    /**
     * Test del metodo remove(Object o)
     */
    @Test
    public void removeObjectTest()
    {
        assertTrue(list.isEmpty());

        HCollection c = makeCollection();
        list.addAll(c);

        for (Object o : c.toArray())
        {
            assertTrue(list.remove(o));
            assertFalse(list.contains(o));
        }

        assertEquals(list.size(), 0);
        assertFalse(list.remove(""));

        list.add("uno");
        list.add("due");
        list.add("uno");

        assertTrue(list.remove("uno"));
        assertEquals(list.get(0), "due");
        assertEquals(list.get(1), "uno");
    }

    /**
     * Test delle eccezioni del metodo remove(Object o)
     */
    @Test
    public void removeObjectExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.remove(null);});
        assertEquals(list.size(), 0);
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.remove(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     * Test del metodo removeAll(HCollection c)
     */
    @Test
    public void removeAllTest()
    {
        assertTrue(list.isEmpty());
        HCollection c = makeCollection();

        assertFalse(list.removeAll(c));

        list.addAll(c);
        list.add(0, "zero");
        list.add(4, "quattro");
        list.add(8, "otto");
        assertTrue(list.removeAll(c));
        assertEquals(list.size() , 3);
        assertEquals(list.get(0), "zero");
        assertEquals(list.get(1), "quattro");
        assertEquals(list.get(2), "otto");

        c.add("zero");
        c.add("quattro");
        c.add("otto");
        assertTrue(list.removeAll(c));
        assertEquals(list.size() , 0);
    }

    /**
     * Test delle eccezioni del metodo removeAll(HCollection c)
     */
    @Test
    public void removeAllExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.removeAll(null);});
        assertEquals(list.size(), 0);
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.removeAll(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     * Test del metodo retainAll(HCollection c)
     */
    @Test
    public void retainAllTest()
    {
        assertTrue(list.isEmpty());
        HCollection c = makeCollection();

        assertFalse(list.retainAll(c));

        list.addAll(c);
        list.add(0, "zero");
        list.add(4, "quattro");
        list.add(8, "otto");
        assertTrue(list.retainAll(c));
        assertEquals(list.size() , 9);
        assertFalse(list.contains("zero"));
        assertTrue(list.contains("quattro"));
        assertFalse(list.contains("otto"));

        c.clear();
        assertTrue(list.retainAll(c));
        assertEquals(list.size() , 0);
    }

    /**
     * Test delle eccezioni del metodo retainAll(HCollection c)
     */
    @Test
    public void retainAllExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.retainAll(null);});
        assertEquals(list.size(), 0);
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.retainAll(null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");
    }

    /**
     * Test del metodo set(int index, Object o)
     */
    @Test
    public void setTest()
    {
        assertTrue(list.isEmpty());

        list.addAll(makeCollection());

        Object o = list.set(5, "a");
        assertFalse(list.contains(o));
        assertTrue(list.contains("a"));
        assertEquals(list.size(), 8);

        o = list.set(0, "b");
        assertFalse(list.contains(o));
        assertTrue(list.contains("b"));
        assertEquals(list.size(), 8);

        o = list.set(list.size()-1, "c");
        assertFalse(list.contains(o));
        assertTrue(list.contains("c"));
        assertEquals(list.size(), 8);
    }

    /**
     * Test delle eccezioni del metodo set(int index, Object o)
     */
    @Test
    public void setExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.set(0, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.set(0, "");});
        assertEquals(list.size(), 0);
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.set(0, null);});
        assertEquals(list.size(), 1);
        assertEquals(list.get(0),"");

        assertThrows(IndexOutOfBoundsException.class, () -> {list.set(list.size(), "");});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.set(-1, "");});
    }

    /**
     * Test del metodo size()
     */
    @Test
    public void sizeTest()
    {
        assertEquals(list.size(), 0);

        list.add("");
        assertEquals(list.size(), 1);

        list.addAll(makeCollection());
        assertEquals(list.size(), 9);

        list.remove("");
        assertEquals(list.size(), 8);

        list.clear();
        assertEquals(list.size(), 0);
    }

    /**
     * Test del metodo sublist(int fromIndex, int toIndex)
     */
    @Test
    public void sublistTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());

        HList l = list.subList(0, list.size());
        assertEquals(l.size(), list.size());

        l = list.subList(3, 8);
        assertEquals(l.size(), 5);

        l = list.subList(2, 2);
        assertEquals(l.size(), 0);
    }

    /**
     * Test delle eccezioni del metodo sublist(int fromIndex, int toIndex)
     */
    @Test
    public void sublistExceptionsTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());

        assertThrows(IndexOutOfBoundsException.class, () -> {list.subList(-1,3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.subList(0,10);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.subList(8,2);});

        assertEquals(list.size(), 8);
    }

    /**
     * Test del metodo toArray()
     */
    @Test
    public void toArrayTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());

        Object[] a = list.toArray();
        assertEquals(list.size(), a.length);
        for (int i = 0; i < a.length; i++)
        {
            assertEquals(a[i], list.get(i));
        }
    }

    /**
     * Test del metodo toArray(Object[] a)
     */
    @Test
    public void toArrayObjectTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());

        Object[] a = new Object[2];
        assertNotEquals(list.size(), a.length);
        a = list.toArray(a);
        assertEquals(list.size(), a.length);
        for (int i = 0; i < a.length; i++)
        {
            assertEquals(a[i], list.get(i));
        }

        Object[] o = new Object[15];
        for (int i = 0; i < 15; i++)
            o[i] = i;

        assertNotEquals(list.size(), o.length);
        o = list.toArray(o);
        assertNotEquals(list.size(), o.length);
        assertEquals(o.length, 15);
        for (int i = 0; i < list.size(); i++)
        {
            assertEquals(o[i], list.get(i));
        }

        for (int i = list.size(); i < o.length; i++)
        {
            assertEquals(o[i], i);
        }
    }

    /**
     * Test delle eccezioni del metodo toArray(Object[] a)
     */
    @Test
    public void toArrayObjectExceptionsTest()
    {
        assertThrows(NullPointerException.class, () -> {list.toArray(null);});
        list.add("");
        assertThrows(NullPointerException.class, () -> {list.toArray(null);});

        Integer[] s = new Integer[10];
        assertThrows(ArrayStoreException.class, () -> {list.toArray(s);});
    
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "");
    }

    /**
     * Test del metodo add(int index, Object o) in "modalità sublist"
     */
    @Test
    public void subListAddIndexTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());
        HList sub = list.subList(3, 6);

        sub.add(0, "one");
        assertEquals(sub.size(), 4);
        assertEquals(list.size(), 9);
        sub.add(0, "two");
        sub.add(2, "three");
        assertEquals(sub.size(), 6);
        assertEquals(list.size(), 11);

        assertEquals(sub.get(0), "two");
        assertEquals(sub.get(1), "one");
        assertEquals(sub.get(2), "three");

        assertEquals(list.get(3), "two");
        assertEquals(list.get(4), "one");
        assertEquals(list.get(5), "three");
    }

    /**
     * Test delle eccezioni del metodo add(int index, Object o) in "modalità sublist"
     */
    @Test
    public void subListAddIndexExceptionsTest()
    {
        list.addAll(makeCollection());
        HList sub = list.subList(3, 3);

        assertThrows(NullPointerException.class, () -> {sub.add(0, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {sub.add(1, "valido");});
        assertThrows(IndexOutOfBoundsException.class, () -> {sub.add(-42, "valido");});
        assertThrows(IndexOutOfBoundsException.class, () -> {sub.add(2, null);});
        
        assertEquals(sub.size(), 0);
        assertEquals(list.size(), 8);
        sub.add(0, "");
        
        assertThrows(NullPointerException.class, () -> {sub.add(1, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {sub.add(2, "valido");});
        assertThrows(IndexOutOfBoundsException.class, () -> {sub.add(-42, "valido");});
        assertThrows(IndexOutOfBoundsException.class, () -> {sub.add(2, null);});
        assertEquals(sub.size(), 1);
        assertEquals(list.size(), 9);
        assertEquals(sub.get(0),"");
        assertEquals(list.get(3),"");
    }

    /**
     * Test del metodo clear() in "modalità sublist"
     */
    @Test
    public void subListClearTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());
        HList sub = list.subList(3, 6);

        sub.clear();
        assertTrue(sub.isEmpty());
        assertEquals(list.size(), 5);

        HCollection c = makeCollection();
        sub.addAll(c);

        sub.clear();
        assertTrue(sub.isEmpty());

        HIterator it = c.iterator();
        while(it.hasNext())    
            assertFalse(sub.contains(it.next()));

        assertEquals(list.size(), 5);
    }

    /**
     * Test del metodo contains(Object o) in "modalità sublist"
     */
    @Test
    public void subListContainsTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());
        HList sub = list.subList(4, 5);

        assertFalse(sub.contains(""));

        sub.add("uno");
        assertTrue(sub.contains("uno"));
        sub.add(0, "due");
        sub.add(1, "tre");
        assertTrue(sub.contains("uno"));
        assertTrue(sub.contains("due"));
        assertTrue(sub.contains("tre"));

        assertEquals(list.size(), 11);
        sub.clear();
        assertEquals(list.size(), 7);

        assertFalse(sub.contains("uno"));
        assertFalse(sub.contains("due"));
        assertFalse(sub.contains("tre"));

        assertTrue(list.contains("uno"));
        assertTrue(list.contains("due"));
        assertTrue(list.contains("tre"));
    }

    /**
     * Test delle eccezioni del metodo contains(Object o) in "modalità sublist"
     */
    @Test
    public void subListContainsExceptionsTest()
    {
        list.addAll(makeCollection());
        HList sub = list.subList(3, 3);

        assertThrows(NullPointerException.class, () -> {sub.contains(null);});
        assertEquals(sub.size(), 0);
        assertEquals(list.size(), 8);
        sub.add(0, "");
        assertThrows(NullPointerException.class, () -> {sub.contains(null);});
        assertEquals(sub.size(), 1);
        assertEquals(list.size(), 9);
        assertEquals(sub.get(0),"");
        assertEquals(list.get(3),"");
    }

    /**
     * Test del metodo indexOf(Object o) in "modalità sublist"
     */
    @Test
    public void subListIndexOfTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());
        HList sub = list.subList(3, 3);

        sub.addAll(makeCollection());
        assertEquals(sub.indexOf("due"), 1);
        assertEquals(sub.indexOf(6), 5);
        assertEquals(list.indexOf("due"), 1);
        assertEquals(list.indexOf(6), 8);
        
        sub.add(2,6);
        assertEquals(sub.indexOf(6), 2);
        assertEquals(sub.indexOf(""), -1);

        sub.clear();
        assertEquals(sub.indexOf(6), -1);
        assertEquals(list.indexOf(6), 5);
    }

    /**
     * Test delle eccezioni del metodo indexOf(Object o) in "modalità sublist"
     */
    @Test
    public void subListIndexOfExceptionsTest()
    {
        list.addAll(makeCollection());
        HList sub = list.subList(3, 3);

        assertThrows(NullPointerException.class, () -> {sub.indexOf(null);});
        assertEquals(sub.size(), 0);
        assertEquals(list.size(), 8);
        sub.add(0, "");
        assertThrows(NullPointerException.class, () -> {sub.indexOf(null);});
        assertEquals(sub.size(), 1);
        assertEquals(list.size(), 9);
        assertEquals(sub.get(0),"");
        assertEquals(list.get(3),"");
    }

    /**
     * Test del metodo remove(int index) in "modalità sublist"
     */
    @Test
    public void subListRemoveIndexTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());
        HList sub = list.subList(2, 8);


        Object o = sub.get(4);
        Object r = sub.remove(3);
        assertFalse(sub.contains(r));
        assertFalse(list.contains(r));
        assertEquals(sub.get(3), o);
        assertEquals(sub.size(), 5);

        while(!sub.isEmpty())
            assertFalse(sub.contains(sub.remove(sub.size()-1)));

        assertTrue(sub.isEmpty());
        assertEquals(list.size(), 2);
    }

    /**
     * Test delle eccezioni del metodo remove(int index) in "modalità sublist"
     */
    @Test
    public void subListRemoveIndexExceptionsTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());
        HList sub = list.subList(3, 3);

        assertThrows(IndexOutOfBoundsException.class, () -> {sub.remove(1);});

        sub.add("");
        assertThrows(IndexOutOfBoundsException.class, () -> {sub.remove(sub.size());});
        assertThrows(IndexOutOfBoundsException.class, () -> {sub.remove(-1);});

        assertEquals(sub.size(), 1);
    }

    /**
     * Test del metodo remove(Object o) in "modalità sublist"
     */
    @Test
    public void subListRemoveObjectTest()
    {
        assertTrue(list.isEmpty());
        list.addAll(makeCollection());
        HList sub = list.subList(3, 3);

        HCollection c = makeCollection();
        sub.addAll(c);

        for (Object o : c.toArray())
        {
            assertTrue(sub.remove(o));
            assertFalse(sub.contains(o));
        }

        assertEquals(sub.size(), 0);
        assertEquals(list.size(), 8);
        assertFalse(sub.remove(""));

        sub.add("uno");
        sub.add("due");
        sub.add("uno");

        assertTrue(sub.remove("uno"));
        assertEquals(sub.get(0), "due");
        assertEquals(sub.get(1), "uno");
        assertEquals(list.size(), 10);
    }

    /**
     * Test delle eccezioni del metodo remove(Object o) in "modalità sublist"
     */
    @Test
    public void subListRemoveObjectExceptionsTest()
    {
        list.addAll(makeCollection());
        HList sub = list.subList(3, 3);

        assertThrows(NullPointerException.class, () -> {sub.remove(null);});
        assertEquals(sub.size(), 0);
        assertEquals(list.size(), 8);
        sub.add(0, "");
        assertThrows(NullPointerException.class, () -> {sub.remove(null);});
        assertEquals(sub.size(), 1);
        assertEquals(list.size(), 9);
        assertEquals(sub.get(0),"");
        assertEquals(list.get(3),"");
    }

    /**
     * Test del metodo size() in "modalità sublist"
     */
    @Test
    public void subListSizeTest()
    {
        assertEquals(list.size(), 0);
        list.addAll(makeCollection());
        HList sub = list.subList(3, 3);
        
        assertEquals(sub.size(), 0);

        sub.add("");
        assertEquals(sub.size(), 1);
        assertEquals(list.size(), 9);

        sub.addAll(makeCollection());
        assertEquals(sub.size(), 9);
        assertEquals(list.size(), 17);

        sub.remove("");
        assertEquals(sub.size(), 8);
        assertEquals(list.size(), 16);

        sub.clear();
        assertEquals(sub.size(), 0);
        assertEquals(list.size(), 8);
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