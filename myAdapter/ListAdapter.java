package myAdapter;

import myLib.Vector;

public class ListAdapter implements HList
{
    private Vector vec;

    /**
     * Constructs a new ListAdapter, with initial size of zero.
     */
    public ListAdapter()
    {
        vec = new Vector();
    }

    /**
     * Constructs a new ListAdapter containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     *
     * @param   c - the collection whose elements are to be placed into this list.
     * @throws  NullPointerException - if the specified collection is null.
     */
    public ListAdapter(HCollection c)
    {
        if (c == null)
            throw new NullPointerException();

        vec = new Vector();
        HIterator iter = c.iterator();
        while (iter.hasNext())
            vec.addElement(iter.next());
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent elements to the
     * right (adds one to their indices).
     *
     * @param   index index at which the specified element is to be inserted.
     * @param   element element to be inserted.
     * @throws  IndexOutOfBoundsException if the index is out of range {@code (index < 0 || index > size())}.
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > vec.size())
            throw new IndexOutOfBoundsException();
        if (element == null)
            throw new NullPointerException();

        vec.insertElementAt(element, index);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * This list will refute the insertion of null objects.
     *
     * @param   o element to be appended to this list.
     * @return  {@code true} (as specified by {@link HCollection#add}).
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public boolean add(Object o)
    {
        add(vec.size(), o);
        return true;
    }

    /**
     * Appends all of the elements in the specified collection to the end of this list,
     * in the order that they are returned by the specified collection's iterator.
     * The behavior of this operation is unspecified if the specified collection is modified
     * while the operation is in progress. (Note that this will occur if the specified collection is
     * this list, and it's nonempty.)
     *
     * @param   c collection whose elements are to be added to this list.
     * @return  {@code true} if this list changed as a result of the call.}
     * @throws  NullPointerException if the specified collection is {@code null}
     *          or contains one or more null objects.
     */
    @Override
    public boolean addAll(HCollection c)
    {
        addAll(vec.size(), c);
        return false;
    }

    /**
     * Inserts all of the elements in the specified collection into this list at the specified position.
     * Shifts the element currently at that position (if any) and any subsequent elements to the
     * right (increases their indices). The new elements will appear in this list in the order
     * that they are returned by the specified collection's iterator. The behavior of this operation
     * is unspecified if the specified collection is modified while the operation is in progress.
     * (Note that this will occur if the specified collection is this list, and it's nonempty.)
     *
     * @param   index index at which to insert first element from the specified collection.
     * @param   c elements to be inserted into this list.
     * @return  {@code true} if this list changed as a result of the call.
     * @throws  IndexOutOfBoundsException if the index is out of range {@code (index < 0 || index > size())}.
     * @throws  NullPointerException if the specified collection is {@code null}
     *          or contains one or more null objects.
     * @see     #add(Object)
     */
    @Override
    public boolean addAll(int index, HCollection c)
    {
        if (index < 0 || index > vec.size())
            throw new IndexOutOfBoundsException();
        if (c == null)
            throw new NullPointerException();

        int size = vec.size();
        HIterator iter = c.iterator();
        while(iter.hasNext())
            add(index++, iter.next());
        
        return size != vec.size();
    }

    /**
     * Removes all of the elements from this list.
     * This list will be empty after this call returns (unless it throws an exception).
     */
    @Override
    public void clear()
    {
        vec.removeAllElements();
    }

    /**
     * Returns {@code true} if this list contains the specified element. More formally,
     * returns {@code true} if and only if this list contains at least one element {@code e} such
     * that {@code (o==null ? e==null : o.equals(e))}.
     *
     * @param   o element whose presence in this list is to be tested.
     * @return  {@code true} if this list contains the specified element.
     * @throws  NullPointerException if the specified element is {@code null}.
     */
    @Override
    public boolean contains(Object o)
    {
        if (o == null)
            throw new NullPointerException();
        
        return vec.contains(o);
    }

    /**
     * Returns true if this list contains all of the elements of the specified collection.
     * Returns true if the collection is empty.
     *
     * @param   c collection to be checked for containment in this list.
     * @return  {@code true} if this list contains all of the elements of the specified collection.
     * @throws  NullPointerException if the specified collection is null or contains null elements.
     * @see     #contains(Object)
     */
    @Override
    public boolean containsAll(HCollection c) 
    {
        if (c == null)
            throw new NullPointerException();

        HIterator iter = c.iterator();
        while(iter.hasNext())
            if (!contains(iter.next())) return false;

        return true;
    }

    /**
     * Compares the specified object with this list for equality. Returns {@code true} if
     * and only if the specified object is also a list, both lists have the same size, and
     * all corresponding pairs of elements in the two lists are <i>equal</i>. (Two
     * elements {@code e1} and {@code e2} are <i>equal</i> if {@code (e1==null ? e2==null :
     * e1.equals(e2)).)} In other words, two lists are defined to be <i>equal</i>
     * if they contain the same elements in the same order.
     *
     * @param   o the object to be compared for equality with this list.
     * @return  {@code true} if the specified object is equal to this list.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof ListAdapter)) return false;

        ListAdapter l = (ListAdapter)o;
        if (vec.size() != l.size()) return false;

        for (int i = 0; i < vec.size(); i++)
            if(!get(i).equals(l.get(i))) return false;

        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param   index index of element to return.
     * @return  the element at the specified position in this list.
     * @throws  IndexOutOfBoundsException if the index is out of range
     *          {@code (index < 0 || index >= size())}.
     */
    @Override
    public Object get(int index)
    {
        if (index < 0 || index >= vec.size())
            throw new IndexOutOfBoundsException();
        
        return vec.elementAt(index);
    }

    /**
     * Returns the hash code value for this list. The hash code of a list is defined to be the
     * result of the following calculation:
     *  {@code
     *   hashCode = 1;
     *   Iterator i = list.iterator();
     *   while (i.hasNext()) {
     *       Object obj = i.next();
     *       hashCode = 31*hashCode + obj.hashCode();
     *   }
     *  }
     * This ensures that {@code list1.equals(list2)} implies that {@code list1.hashCode()==list2.hashCode()}
     * for any two lists, {@code list1} and {@code list2}, as required by the general contract
     * {@code of Object.hashCode}.
     *
     * @return  the hash code value for this list.
     */
    @Override
    public int hashCode()
    {
        int ret = 1;
        HIterator iter = iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            ret = 31*ret + obj.hashCode();
        }

        return ret;
    }

    /**
     * Returns the index in this list of the first occurrence of the specified element,
     * or -1 if this list does not contain this element. More formally, returns the lowest
     * index i such that {@code (o==null ? get(i)==null : o.equals(get(i)))},
     * or -1 if there is no such index.
     *
     * @param   o element to search for.
     * @return  the index in this list of the first occurrence of the specified
     *          element, or -1 if this list does not contain this element.
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public int indexOf(Object o)
    {
        if (o == null)
            throw new NullPointerException();

        return vec.indexOf(o);
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return  {@code true} if this list contains no elements.
     */
    @Override
    public boolean isEmpty()
    {
        return vec.size() <= 0;
    }

    @Override
    public HIterator iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns the index in this list of the last occurrence of the specified element,
     * or -1 if this list does not contain this element. More formally, returns the highest index
     * i such that {@code (o==null ? get(i)==null : o.equals(get(i)))}, or -1 if there is no such index.
     *
     * @param   o element to search for.
     * @return  the index in this list of the last occurrence of the specified element,
     *          or -1 if this list does not contain this element.
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public int lastIndexOf(Object o)
    {
        if (o == null)
            throw new NullPointerException();

        return vec.lastIndexOf(o);
    }

    @Override
    public HListIterator listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HListIterator listIterator(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Removes the element at the specified position in this list. Shifts any
     * subsequent elements to the left (subtracts one from their indices). Returns
     * the element that was removed from the list.
     *
     * @param   index the index of the element to removed.
     * @return  the element previously at the specified position.
     * @throws  IndexOutOfBoundsException if the index is out of
     *          range {@code (index < 0 || index >= size())}.
     */
    @Override
    public Object remove(int index)
    {
        Object ret = get(index);
        vec.removeElementAt(index);
        return ret;
    }

    /**
     * Removes the first occurrence in this list of the specified element. If this
     * list does not contain the element, it is unchanged. More formally, removes
     * the element with the lowest index i such that {@code (o==null ? get(i)==null : o.equals(get(i)))}
     * (if such an element exists).
     *
     * @param   o element to be removed from this list, if present.
     * @return  {@code true} if this list contained the specified element.
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public boolean remove(Object o)
    {
        if (o == null)
            throw new NullPointerException();

        return vec.removeElement(o);
    }

    /**
     * Removes from this list all the elements that are contained in the specified collection.
     *
     * @param   c collection that defines which elements will be removed from this list.
     * @return  {@code true} if this list changed as a result of the call.
     * @throws  NullPointerException if the specified collection is null or contains null elements.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean removeAll(HCollection c) 
    {
        if (c == null)
            throw new NullPointerException();

        int size = vec.size();
        HIterator iter = c.iterator();
        while (iter.hasNext())
            remove(iter.next());

        return size != vec.size();
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection. In other words, removes from this list all
     * the elements that are not contained in the specified collection.
     *
     * @param   c collection that defines which elements this set will retain.
     * @return  {@code true} if this list changed as a result of the call.
     * @throws  NullPointerException if the specified collection is null or contains null elements.
     * @see     #remove(Object)
     * @see     #contains(Object)
     */
    @Override
    public boolean retainAll(HCollection c)
    {
        if (c == null)
            throw new NullPointerException();
        
        int size = vec.size();
        HIterator iter = iterator();
        while (iter.hasNext())
        {
            Object o = iter.next();
            if (!c.contains(o)) remove(o);
        }    
        
        return size != vec.size();
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param   index index of element to replace.
     * @param   element element to be stored at the specified position.
     * @return  the element previously at the specified position.
     * @throws  NullPointerException if the specified element is null.
     * @throws  IndexOutOfBoundsException if the index is out of range
     *          {@code (index < 0 || index >= size())}.
     */
    @Override
    public Object set(int index, Object element)
    {
        if (element == null)
            throw new NullPointerException();

        Object ret = get(index);
        vec.setElementAt(element, index);

        return ret;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list.
     */
    @Override
    public int size()
    {
        return vec.size();
    }

    @Override
    public HList subList(int fromIndex, int toIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     *
     * @return an array containing all of the elements in this list in proper sequence.
     */
    @Override
    public Object[] toArray()
    {
        Object[] ret = new Object[vec.size()];
        for (int i = 0; i < vec.size(); i++)
            ret[i] = get(i);

        return ret;
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence; the runtime type of the returned array is that of the specified array.
     *
     * @param   a the array into which the elements of this list are to be
     *          stored, if it is big enough; otherwise, a new {@code Object[]} array
     *          is created for this purpose.
     * @return  an array containing the elements of this list.
     * @throws  NullPointerException if the specified array is {@code null}.
     */
    @Override
    public Object[] toArray(Object[] a)
    {
        if (a == null)
            throw new NullPointerException();
        
        if (a.length < vec.size())
            return toArray();
        
        for (int i = 0; i < vec.size(); i++)
            a[i] = get(i);
        
            return a;
    }
    
}