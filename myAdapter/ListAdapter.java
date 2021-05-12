package myAdapter;

import myLib.Vector;
import myLib.NoSuchElementException;

public class ListAdapter implements HList
{
    //Il vettore usato come adaptee per l'adapter.
    private Vector vec;

    //Variabile usata dagli iteratori per verificare se la lista vine modificata strutturalmente
    //durante il processo di iterazione.
    private int modCount = 0;

    //Valori usati per implementare sublist.
    //Vengono  modificati, e di conseguenza resi influenti, solo quando viene istanziato un
    //ListAdapter dal costruttore specifico per sublist.
    private int from = 0;
    private int to = -1;
    private ListAdapter baseList = null;

    //Costruttore per sublist.
    private ListAdapter(ListAdapter b, int fromIndex, int toIndex)
    {
        baseList = b;
        vec = b.vec;
        from = fromIndex;
        to = toIndex;
    }


    /**
     * Constructs a new ListAdapter, with initial size of zero.
     */
    public ListAdapter()
    {
        vec = new myLib.Vector();
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

        vec = new myLib.Vector();
        HIterator iter = c.iterator();
        while (iter.hasNext())
            vec.addElement(iter.next());
    }

    /**
     * Inserts the specified element at the specified position in this ListAdapter.
     * Shifts the element currently at that position (if any) and any subsequent elements to the
     * right (adds one to their indices).
     *
     * This ListAdapter will refuse the insertion of null objects.
     *
     * @param   index index at which the specified element is to be inserted.
     * @param   element element to be inserted.
     * @throws  IndexOutOfBoundsException if the index is out of range {@code (index < 0 || index > size())}.
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        if (element == null)
            throw new NullPointerException();

        vec.insertElementAt(element, index + from);
        modCount++;

        //Se sono una sublist correggo il limite superiore.
        if (to != -1)
            adjustOffset(false);
    }

    /**
     * Appends the specified element to the end of this ListAdapter.
     *
     * This ListAdapter will refuse the insertion of null objects.
     *
     * @param   o element to be appended to this ListAdapter.
     * @return  {@code true} if the operation was successful.
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public boolean add(Object o)
    {
        add(size(), o);
        return true;
    }

    /**
     * Appends all of the elements in the specified collection to the end of this ListAdapter,
     * in the order that they are returned by the specified collection's iterator.
     * The behavior of this operation is unspecified if the specified collection is modified
     * while the operation is in progress. (Note that this will occur if the specified collection is
     * this list, and it's nonempty.)
     *
     * @param   c collection whose elements are to be added to this ListAdapter.
     * @return  {@code true} if this list changed as a result of the call.
     * @throws  NullPointerException if the specified collection is {@code null}
     *          or contains one or more null objects.
     */
    @Override
    public boolean addAll(HCollection c)
    {    
        return addAll(size(), c);
    }

    /**
     * Inserts all of the elements in the specified collection into this ListAdapter at the specified position.
     * Shifts the element currently at that position (if any) and any subsequent elements to the
     * right (increases their indices). The new elements will appear in this ListAdapter in the order
     * that they are returned by the specified collection's iterator. The behavior of this operation
     * is unspecified if the specified collection is modified while the operation is in progress.
     * (Note that this will occur if the specified collection is this list, and it's nonempty.)
     *
     * @param   index index at which to insert first element from the specified collection.
     * @param   c elements to be inserted into this ListAdapter.
     * @return  {@code true} if this list changed as a result of the call.
     * @throws  IndexOutOfBoundsException if the index is out of range {@code (index < 0 || index > size())}.
     * @throws  NullPointerException if the specified collection is {@code null}
     *          or contains one or more null objects.
     * @see     #add(Object)
     */
    @Override
    public boolean addAll(int index, HCollection c)
    {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        if (c == null)
            throw new NullPointerException();

        try
        {
            if (c.contains(null))
                throw new NullPointerException();

        }catch (NullPointerException npe){} //se c lancia NullPointerException significa che non accetta
                                            //null come elemento. Quindi sicuramente non conterrà null
                                            //ed è quindi una HCollection valida.

        int size = size();
        HIterator iter = c.iterator();
        while(iter.hasNext())
            add(index++, iter.next());
        
        return size != size();
    }

    /**
     * Removes all of the elements from this ListAdapter.
     * This list will be empty after this call returns.
     */
    @Override
    public void clear()
    {
        if (size() >= 0)
            modCount++;

        if (to != -1)
            while(to > from)
            {    
                vec.removeElementAt(to-1);
                adjustOffset(true);
            }
        else
            vec.removeAllElements();
        
    }

    /**
     * Returns {@code true} if this ListAdapter contains the specified element. More formally,
     * returns {@code true} if and only if this list contains at least one element {@code e} such
     * that {@code o.equals(e)}.
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
        
        if (size() <= 0)
            return false;

        //Caso per sublist.
        if (to != -1)
            return vec.lastIndexOf(o, to-1) >= from;
        
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

        try
        {
            if (c.contains(null))
                throw new NullPointerException();

        }catch (NullPointerException npe){} //se c lancia NullPointerException significa che non accetta
                                            //null come elemento. Quindi sicuramente non conterrà null
                                            //ed è quindi una HCollection valida.

        HIterator iter = c.iterator();
        while(iter.hasNext())
            if (!contains(iter.next())) return false;

        return true;
    }

    /**
     * Compares the specified object with this ListAdapter for equality. Returns {@code true} if
     * and only if the specified object is also a ListAdapter, both lists have the same size, and
     * all corresponding pairs of elements in the two lists are <i>equal</i>. (Two
     * elements {@code e1} and {@code e2} are <i>equal</i> if {@code e1.equals(e2)}
     * In other words, two lists are defined to be <i>equal</i>
     * if they contain the same elements in the same order.
     *
     * @param   o the object to be compared for equality with this list.
     * @return  {@code true} if the specified object is equal to this list.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof ListAdapter))
            return false;

        ListAdapter l = (ListAdapter)o;
        if (size() != l.size()) return false;

        for (int i = 0; i < size(); i++)
            if(!get(i).equals(l.get(i))) return false;

        return true;
    }

    /**
     * Returns the element at the specified position in this ListAdapter.
     *
     * @param   index index of element to return.
     * @return  the element at the specified position in this ListAdapter.
     * @throws  IndexOutOfBoundsException if the index is out of range
     *          {@code (index < 0 || index >= size())}.
     */
    @Override
    public Object get(int index)
    {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        
        return vec.elementAt(index + from);
    }

    /**
     * Returns the hash code value for this ListAdapter. The hash code of a list is defined to be the
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
     * Returns the index in this ListAdapter of the first occurrence of the specified element,
     * or -1 if this list does not contain this element. More formally, returns the lowest
     * index i such that {@code o.equals(get(i))}, or -1 if there is no such index.
     *
     * @param   o element to search for.
     * @return  the index in this ListAdapter of the first occurrence of the specified
     *          element, or -1 if this list does not contain this element.
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public int indexOf(Object o)
    {
        if (o == null)
            throw new NullPointerException();

        if(size() <= 0)
            return -1;
        
        if (to != -1)
        {
            int i = vec.indexOf(o, from);
            if(i != -1 && to > i)
                return i - from;
            return -1;
        }

        return vec.indexOf(o);
    }

    /**
     * Returns {@code true} if this ListAdapter contains no elements.
     *
     * @return  {@code true} if this ListAdapter contains no elements.
     */
    @Override
    public boolean isEmpty()
    {
        return size() <= 0;
    }

    /**
     * Returns an iterator over the elements in this ListAdapter in proper sequence.
     *
     * @return  an iterator over the elements in this ListAdapter in proper sequence.
     */
    @Override
    public HIterator iterator()
    {
        return new Iter(vec, from);
    }

    /**
     * Returns the index in this ListAdapter of the last occurrence of the specified element,
     * or -1 if this list does not contain this element. More formally, returns the highest index
     * i such that {@code o.equals(get(i))}, or -1 if there is no such index.
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
        
        if (size() <= 0)
            return -1;

        int i = vec.lastIndexOf(o, size() + from -1);
        if (i >= from)
            return i - from;
        return -1;
    }

    /**
     * Returns a list iterator of the elements in this ListAdapter (in proper sequence).
     *
     * @return a list iterator of the elements in this ListAdapter (in proper sequence).
     */
    @Override
    public HListIterator listIterator()
    {
        return new ListIter(vec, from);
    }

    /**
     * Returns a list iterator of the elements in this ListAdapter (in proper sequence),
     * starting at the specified position in this list. The specified index indicates the
     * first element that would be returned by an initial call to the {@code next} method. An
     * initial call to the {@code previous} method would return the element with the specified
     * index minus one.
     *
     * @param   index index of first element to be returned from
     *          the list iterator (by a call to the next method).
     * @return  a list iterator of the elements in this list (in proper sequence),
     *          starting at the specified position in this list.
     * @throws  IndexOutOfBoundsException if the index is out of range
     *          {@code (index < 0 || index > size())}.
     */
    @Override
    public HListIterator listIterator(int index)
    {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();

        return new ListIter(vec, index + from);
    }

    /**
     * Removes the element at the specified position in this ListAdapter. Shifts any
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
        vec.removeElementAt(index + from);
        
        if (to != -1)
            adjustOffset(true);

        modCount++;

        return ret;
    }

    /**
     * Removes the first occurrence in this ListAdapter of the specified element. If this
     * list does not contain the element, it is unchanged. More formally, removes
     * the element with the lowest index i such that {@code o.equals(get(i))}
     * (if such an element exists).
     *
     * @param   o element to be removed from this ListAdapter, if present.
     * @return  {@code true} if this list contained the specified element.
     * @throws  NullPointerException if the specified element is null.
     */
    @Override
    public boolean remove(Object o)
    {
        if (o == null)
            throw new NullPointerException();

        int i = indexOf(o);
        if (i >= 0)
        {
            vec.removeElementAt(i + from);
            if (to != -1)
                adjustOffset(true);

            modCount++;

            return true;
        }

        return false;
    }

    /**
     * Removes from this ListAdapter all the elements that are contained in the specified collection.
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

        try
        {
            if (c.contains(null))
                throw new NullPointerException();

        }catch (NullPointerException npe){} //se c lancia NullPointerException significa che non accetta
                                            //null come elemento. Quindi sicuramente non conterrà null
                                            //ed è quindi una HCollection valida.

        int size = size();
        HIterator iter = c.iterator();
        while (iter.hasNext())
            remove(iter.next());

        return size != size();
    }

    /**
     * Retains only the elements in this ListAdapter that are contained in the
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
        
        try
        {
            if (c.contains(null))
                throw new NullPointerException();

        }catch (NullPointerException npe){} //se c lancia NullPointerException significa che non accetta
                                            //null come elemento. Quindi sicuramente non conterrà null
                                            //ed è quindi una HCollection valida.

        int size = size();
        HIterator iter = iterator();
        while (iter.hasNext())
        {
            Object o = iter.next();
            if (!c.contains(o)) iter.remove();
        }    
        
        if (size != size())
        {
            modCount++;
            return true;
        }

        return false;
    }

    /**
     * Replaces the element at the specified position in this ListAdapter with
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
        vec.setElementAt(element, index + from);

        return ret;
    }

    /**
     * Returns the number of elements in this ListAdapter.
     *
     * @return the number of elements in this ListAdapter.
     */
    @Override
    public int size()
    {
        //Sono una sublist
        if (to != -1)
            return to - from;
        
        return vec.size();
    }

    /**
     * Returns a view of the portion of this ListAdapter between the specified {@code fromIndex},
     * inclusive, and {@code toIndex}, exclusive. (If {@code fromIndex} and {@code toIndex}
     * are equal, the returned list is empty.) The returned list is backed by this ListAdapter, so
     * non-structural changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations supported by this ListAdapter.
     * This method eliminates the need for explicit range operations (of the sort that commonly
     * exist for arrays). Any operation that expects a list can be used as a range operation by passing
     * a subList view instead of a whole list. For example, the following idiom removes a range of
     * elements from a list:
     *
     * 	    {@code ListAdapter.subList(from, to).clear();}
     *
     * Similar idioms may be constructed for {@code indexOf} and {@code lastIndexOf}, and all
     * of the algorithms in the {@code Collections} class can be applied to a subList.
     * The semantics of the list returned by this method become undefined if the backing list
     * (i.e., this ListAdapter) is <i>structurally modified</i> in any way other than via the returned
     * list. (Structural modifications are those that change the size of this list, or otherwise
     * perturb it in such a fashion that iterations in progress may yield incorrect results.)
     *
     * @param   fromIndex low endpoint (inclusive) of the subList.
     * @param   toIndex high endpoint (exclusive) of the subList.
     * @return  a view of the specified range within this list.
     * @throws  IndexOutOfBoundsException for an illegal endpoint index value
     *          {@code (fromIndex < 0 || toIndex > size || fromIndex > toIndex)}.
     */
    @Override
    public HList subList(int fromIndex, int toIndex)
    {
        if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        return new ListAdapter(this, fromIndex + from, toIndex + from);
    }

    /**
     * Returns an array containing all of the elements in this ListAdapter in proper sequence.
     *
     * @return an array containing all of the elements in this ListAdapter in proper sequence.
     */
    @Override
    public Object[] toArray()
    {
        Object[] ret = new Object[size()];
        for (int i = 0; i < size(); i++)
            ret[i] = get(i);

        return ret;
    }

    /**
     * Returns an array containing all of the elements in this ListAdapter in proper
     * sequence; the runtime type of the returned array is that of the specified array.
     *
     * @param   a the array into which the elements of this ListAdapter are to be
     *          stored, if it is big enough; otherwise, a new {@code Object[]} array
     *          is created for this purpose.
     * @return  an array containing the elements of this list.
     * @throws  NullPointerException if the specified array is {@code null}.
     * @throws  ArrayStoreException - if the runtime type of the specified array
     *          is not a supertype of the runtime type of every element in this list.
     */
    @Override
    public Object[] toArray(Object[] a)
    {
        if (a == null)
            throw new NullPointerException();
        
        if (a.length < size())
            return toArray();
        
        //Può generare ArrayStoreException
        for (int i = 0; i < size(); i++)
            a[i] = get(i);
        
        return a;
    }
    


    //Sottoclassi di supporto ai metodi:
    //Configurate sia per lavorare su ListAdapter che su sublist.

    /**
     * Simple Iterator over a ListAdapter object. It can traverse the vector associated
     * with the ListAdapter forwards, it can performs remove operations.
     */
    private class Iter implements HIterator
    {
        protected myLib.Vector v;
        protected int index;
        protected int expectedModCount;
        protected boolean nextRemovable;
        
        /**
         * Construct a new Iterator over the given Vector, starting from
         * the specified index
         *
         * @param   vv the given Vector
         * @param   startIndex the index the iteration will start from.
         */
        Iter(myLib.Vector vv, int startIndex)
        {
            v = vv;
            index = startIndex;
            expectedModCount = modCount;
            nextRemovable = false;
        }


        /**
         * Returns {@code true} if the iteration has more elements. (In
         * other words, returns {@code true} if {@code next} would return an element
         * rather than throwing an exception).
         *
         * @return  {@code true} if the iterator has more elements.
         */
        @Override
        public boolean hasNext()
        {
            if (to != -1)
                return index < to;
            
            return index < v.size();
        }

        /**
         * Returns the {@code next} element in the iteration.
         * The behavior of the iterator is unspecified if the underlying collection is modified
         * while the iteration is in progress.
         *
         * @return  the {@code next} element in the iteration.
         * @throws  NoSuchElementException iteration has no more elements.
         */
        @Override
        public Object next()
        {
            if (!hasNext())
                throw new NoSuchElementException();

            nextRemovable = true;
            return vec.elementAt(index++);
        }

        /**
         * Removes from the underlying collection the last element returned by the iterator.
         * This method can be called only once per call to {@code next}. The behavior of the iterator
         * is unspecified if the underlying collection is modified while the iteration
         * is in progress in any way other than by calling this method.
         *
         * @throws  IllegalStateException if the next method has not yet been called, or
         *          the {@code remove} method has already been called after the last call to
         *          the {@code next} method.
         */
        @Override
        public void remove()
        {
            //In questo caso se la lista è stata modificata durante il processo di iterazione
            //lancio un eccezione piuttosto che avere undefined behavior.
            if (expectedModCount  != modCount)
                throw new IllegalStateException("Underlying list has been modified during the iteration");

            if (!nextRemovable)
                throw new IllegalStateException();
            
            nextRemovable = false;
            vec.removeElementAt(--index);

            //Correggo il range per sublist.
            if (to != -1)
                adjustOffset(true);
        }
        
    }

    /**
     * ListIterator over the given Vector. It can traverse the vector associated
     * with the ListAdapter forwards and in reverse, it can performs remove and inserction
     * operations.
     */
    private class ListIter extends Iter implements HListIterator
    {
        private boolean prevRemovable;

        /**
         * Construct a new ListIterator over the given Vector, starting from
         * the specified index.
         *
         * @param   vv the given Vector.
         * @param   startindex the index the iteration will start from.
         */
        ListIter(myLib.Vector vv, int startIndex)
        {
            super(vv, startIndex);
            prevRemovable = false;
        }

        /**
         * Inserts the specified element into the list. The element is inserted immediately
         * before the next element that would be returned by {@code next}, if any,
         * and after the next element that would be returned by {@code previous}, if any.
         * (If the list contains no elements, the new element becomes the sole element on the list.)
         * The new element is inserted before the implicit cursor: a subsequent call to {@code next}
         * would be unaffected, and a subsequent call to {@code previous} would return the new element.
         * (This call increases by one the value that would be returned by a call to {@code nextIndex}
         * or {@code previousIndex}.)
         * 
         * The behavior of the iterator is unspecified if the underlying collection is modified while the iteration
         * is in progress in any way other than by calling this method or the {@code remove} method.
         *
         * @param   o the element to insert.
         * @throws  NullPointerException if the specified element is null.
         * @see  #remove()
         */
        @Override
        public void add(Object o)
        {
            //In questo caso se la lista è stata modificata durante il processo di iterazione
            //lancio un eccezione piuttosto che avere undefined behavior.
            if (expectedModCount  != modCount)
                throw new IllegalStateException("Underlying list has been modified during the iteration");

            if (o == null)
                throw new NullPointerException();

            nextRemovable = false;
            prevRemovable = false;
            vec.insertElementAt(o, index++);

            //Correggo il range per sublist.
            if (to != -1)
                adjustOffset(false);
        }

        /**
         * Returns {@code true} if this list iterator has more elements when traversing the
         * list in the reverse direction. (In other words, returns {@code true} if
         * {@code previous} would return an element rather than throwing an exception).
         *
         * @return  {@code true} if the list iterator has more elements when traversing
         *          the list in the reverse direction.
         */
        @Override
        public boolean hasPrevious()
        {
            return index > 0 + from;
        }

        /**
         * Returns the next element in the list. This method may be called repeatedly
         * to iterate through the list, or intermixed with calls to {@code previous} to go
         * back and forth. (Note that alternating calls to {@code next} and {@code previous}
         * will return the same element repeatedly).
         *
         * @return  the next element in the list.
         * @throws  NoSuchElementException if the iteration has no next element.
         */
        @Override
        public Object next()
        {
            if (!hasNext())
                throw new NoSuchElementException();

            prevRemovable = false;
            return super.next();
        }

        /**
         * Returns the index of the element that would be returned by a subsequent
         * call to {@code next}. (Returns list size if the list iterator is at the
         * end of the list.)
         *
         * @return  the index of the element that would be returned by a
         *          subsequent call to {@code next}, or list size if the
         *          list iterator is at end of list.
         */
        @Override
        public int nextIndex() 
        {
            return index - from;
        }

        /**
         * Returns the previous element in the list. This method may be called repeatedly
         * to iterate through the list backwards, or intermixed with calls to {@code next} to go
         * back and forth. (Note that alternating calls to {@code next} and {@code previous}
         * will return the same element repeatedly.)
         *
         * @return  the previous element in the list.
         * @throws  NoSuchElementException if the iteration has no previous element.
         */
        @Override
        public Object previous()
        {
            if(!hasPrevious())
                throw new NoSuchElementException();
            
            nextRemovable = false;
            prevRemovable = true;

            return vec.elementAt(--index);
        }

        /**
         * Returns the index of the element that would be returned by a subsequent
         * call to {@code previous}. (Returns -1 if the list iterator is at the beginning of the list.)
         *
         * @return  the index of the element that would be returned by a subsequent call
         *          to {@code previous}, or -1 if list iterator is at beginning of list.
         */
        @Override
        public int previousIndex()
        {
            return index - 1 - from;
        }

        /**
         * Removes from the list the last element that was returned by {@code next} or
         * {@code previous}. This call can only be made once per call to {@code next} or
         * {@code previous}. It can be made only if {@code ListIterator.add} has not been
         * called after the last call to {@code next} or {@code previous}.
         *
         * The behavior of the iterator is unspecified if the underlying collection is modified while the iteration
         * is in progress in any way other than by calling this method or the {@code add} method.
         * 
         * @throws  IllegalStateException neither {@code next} nor {@code previous} have
         *          been called, or {@code remove} or {@code add} have been called after
         *          the last call to {@code previous} or {@code previous}.
         * @see     #add(Object)
         */
        @Override
        public void remove()
        {
            //In questo caso se la lista è stata modificata durante il processo di iterazione
            //lancio un eccezione piuttosto che avere undefined behavior.
            if (expectedModCount  != modCount)
                throw new IllegalStateException("Underlying list has been modified during the iteration");

            if (!nextRemovable && !prevRemovable)
                throw new IllegalStateException();

            if (nextRemovable)
                vec.removeElementAt(--index);
            else
                vec.removeElementAt(index);
            
            //Correggo il range per sublist.
            if (to != -1)
                adjustOffset(true);
            
            nextRemovable = false;
            prevRemovable = false;
        }

        /**
         * Replaces the last element returned by {@code next} or {@code previous} with
         * the specified element. This call can be made only if neither {@code ListIterator.remove}
         * nor {@code ListIterator.add} have been called after the last call to {@code next}
         * or {@code previous}.
         * 
         * The behavior of the iterator is unspecified if the underlying collection is modified while the iteration
         * is in progress.
         *
         * @param   o the element to insert.
         * @throws  IllegalStateException if neither next nor previous have been called,
         *          or {@code remove} or {@code add} have been called after the last call
         *          to {@code next} or {@code previous}.
         * @throws  NullPointerException if the specified element is null.
         */
        @Override
        public void set(Object o)
        {
            //In questo caso se la lista è stata modificata durante il processo di iterazione
            //lancio un eccezione piuttosto che avere undefined behavior.
            if (expectedModCount  != modCount)
                throw new IllegalStateException("Underlying list has been modified during the iteration");

            if (!nextRemovable && !prevRemovable)
                throw new IllegalStateException();

            if (o == null)
                throw new NullPointerException();

            if (nextRemovable)
                vec.setElementAt(o, index-1);
            else
                vec.setElementAt(o, index);
        }
    }

    //Metodo che corregge il range su cui una sublist può lavorare.
    //Nel caso in cui la sublist fosse stata generata a partire da un'altra sublist
    //tutti i range vengono mofificati adegutamente tramite la chiamata ricorsiva.
    private void adjustOffset(boolean decrease)
    {
        if (baseList == null)
            return;

        if (decrease)
        {
            to--;
            baseList.adjustOffset(true);
        }
        else
        {
            to++;
            baseList.adjustOffset(false);
        }
    }
}