package myTest;

import myAdapter.*;

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

    @Test
    public void initializeTest() {
        assertEquals(0, list.size());
        System.out.println("The main list is always empty at first.");
        System.out.println("The collection contains the following objects:");
    }
}

