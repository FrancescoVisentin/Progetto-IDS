package myAdapter;

import myLib.Hashtable;
import myLib.Enumeration;

public class MapAdapter implements HMap
{
    //HasTable usata come adaptee per l'adapter.
    private Hashtable map;

    /**
     * Constructs a new, empty map.
     */
    public MapAdapter()
    {
        map = new myLib.Hashtable();
    }

    /**
     * Removes all mappings from this map.
     */
    @Override
    public void clear()
    {
        map.clear();        
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     * More formally, returns true if and only if this map contains a mapping for a key k
     * such that {@code (key==null ? k==null : key.equals(k))}. (There can be at most one such mapping.)

     * 
     * @param key - The key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified key.
     * @throws NullPointerException - if the key is null.
     */
    @Override
    public boolean containsKey(Object key)
    {
        if(key == null)
            throw new NullPointerException();

        return map.containsKey(key);
    }

    /**
     * Returns true if this map maps one or more keys to the specified value.
     * More formally, returns true if and only if this map contains at least one mapping to a value v
     * such that {@code (key==null ? v==null : value.equals(v))}.
     * 
     * @param value - value whose presence in this map is to be tested.
     * @return {@code true} if this map maps one or more keys to the specified value.
     * @throws NullPointerException - if the value is null.
     */
    @Override
    public boolean containsValue(Object value)
    {
        if (value == null)
            throw new NullPointerException();

        return map.contains(value);
    }

    /**
     * Returns a set view of the mappings contained in this map.
     * 
     * Each element in the returned set is a {@code Map.Entry}.
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     * If the map is modified while an iteration over the set is in progress,
     * the results of the iteration are undefined. The set supports element removal,
     * which removes the corresponding mapping from the map,
     * via the {@code Iterator.remove, Set.remove, removeAll, retainAll} and clear operations.
     * It does not support the add or addAll operations
     * 
     * @return a set view of the mappings contained in this map.
     */
    @Override
    public HSet entrySet()
    {
        return new EntrySet();
    }

    /**
     * Compares the specified object with this map for equality.
     * Returns true if the given object is also a MapAdapter and the two Maps represent the same mappings.
     * More formally, two maps t1 and t2 represent the same mappings if t1.entrySet().equals(t2.entrySet()).
     * This ensures that the equals method works properly across different implementations of the Map interface.
     * 
     * @param o - object to be compared for equality with this map.
     * @return {@code true} if the specified object is equal to this map.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof MapAdapter))
            return false;

        MapAdapter e = (MapAdapter)o;

        return entrySet().equals(e.entrySet());
    }

    /**
     * Returns the value to which this map maps the specified key.
     * Returns null if the map contains no mapping for this key.
     * 
     * @param key - the key whose associated value is to be returned.
     * @return the value to which this map maps the specified key,
     *         or null if the map contains no mapping for this key.
     * @throws NullPointerException - if the key is null.
     */
    @Override
    public Object get(Object key)
    {
        if(key == null)
            throw new NullPointerException();

        return map.get(key);
    }

    /**
     * Returns the hash code value for this map.
     * The hash code of a map is defined to be the sum of the hashCodes of each entry in the map's entrySet view.
     * This ensures that t1.equals(t2) implies that t1.hashCode()==t2.hashCode() for any two maps t1 and t2.
     * 
     * @return the hash code value for this map.
     */
    @Override
    public int hashCode() 
    {
        int ret = 0;
        HIterator i = entrySet().iterator();
        while(i.hasNext())
        {
            ret += ((HEntry)i.next()).hashCode();
        }

        return ret;
    }


    /**
     * Returns {@code true} if this map contains no key-value mappings.
     * 
     * @return {@code true} if this map contains no key-value mappings.
     */
    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    /**
     * Returns a set view of the keys contained in this map.
     * 
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     * If the map is modified while an iteration over the set is in progress, 
     * the results of the iteration are undefined. The set supports element removal,
     * which removes the corresponding mapping from the map,
     * via the {@code Iterator.remove, Set.remove, removeAll retainAll}, and clear operations.
     * It does not support the add or addAll operations.
     * 
     * @return a set view of the keys contained in this map.
     */
    @Override
    public HSet keySet()
    {
        return new KeySet();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for this key, the old value is replaced by the specified value.
     * (A map m is said to contain a mapping for a key k if and only if m.containsKey(k) would return true.))
     * 
     * @return previous value associated with specified key, or null if there was no mapping for key.
     * @throws NullPointerException - if the specified key or value is null.
     */
    @Override
    public Object put(Object key, Object value)
    {
        if (key == null || value == null)
            throw new NullPointerException();

        return map.put(key, value);
    }

    /**
     * Copies all of the mappings from the specified map to this map.
     * The effect of this call is equivalent to that of calling put(k, v) on this map once
     * for each mapping from key k to value v in the specified map.
     * The behavior of this operation is unspecified if the specified map is modified
     * while the operation is in progress.
     * 
     * @param t - Mappings to be stored in this map.
     * @throws NullPointerException - the specified map is null,
     *         or the specified map contains null keys or values.
     */
    @Override
    public void putAll(HMap t)
    {
        if (t == null)
            throw new NullPointerException();
        
        HSet s = t.entrySet();
        HIterator i = s.iterator();
        while (i.hasNext())
        {
            HEntry e = (HEntry)i.next();
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Removes the mapping for this key from this map if it is present.
     * Returns the value to which the map previously associated the key,
     * or null if the map contained no mapping for this key.
     * The map will not contain a mapping for the specified key once the call returns.
     * 
     * @param key - key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or null if there was no mapping for key.
     * @throws NullPointerException - if the specified key is null.
     */
    @Override
    public Object remove(Object key)
    {
        if (key == null)
            throw new NullPointerException();

        return map.remove(key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map.
     */
    @Override
    public int size()
    {
        return map.size();
    }

    /**
     * Returns a collection view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are reflected in the collection, and vice-versa.
     * If the map is modified while an iteration over the collection is in progress,
     * the results of the iteration are undefined. The collection supports element removal,
     * which removes the corresponding mapping from the map, via the {@code Iterator.remove, Collection.remove,
     * removeAll, retainAll, clear} operations. It does not support the add or addAll operations.
     */
    @Override
    public HCollection values()
    {
        return new ValueSet();
    }

    //Classi private di supporto

    private class HEntry implements HMap.Entry
    {
        Object val;
        Object key;


        public HEntry(Object k, Object v)
        {
            key = k;
            val = v;
        }

        /**
         * Compares the specified object with this entry for equality.
         * Returns true if the given object is also a map entry and the two entries represent the same mapping.
         * 
         * @param o - object to be compared for equality with this map entry.
         * @return {@code true} if the specified object is equal to this map entry.
         */
        @Override
        public boolean equals(Object o)
        {
            HEntry e = (HEntry)o;
            return getKey() == e.getKey() && getValue() == e.getValue();
        }

        /**
         * Returns the key corresponding to this entry.
         * 
         * @return the key corresponding to this entry.
         */
        @Override
        public Object getKey()
        {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.
         * 
         * @return the value corresponding to this entry.
         */
        @Override
        public Object getValue()
        {
            return val;
        }

        /**
         * Returns the hash code value for this map entry. The hash code of a map entry e is defined to be:
         * {@code e.getKey().hashCode() ^ e.getValue().hashCode()}
         * 
         * This ensures that e1.equals(e2) implies that e1.hashCode()==e2.hashCode() for
         * any two Entries e1 and e2, as required by the general contract of Object.hashCode.
         * 
         * @return the hash code value for this map entry.
         */
        @Override
        public int hashCode()
        {
            return getKey().hashCode() ^ getValue().hashCode();
        }

        /**
         * Replaces the value corresponding to this entry with the specified value. (Writes through to the map.)
         * 
         * @param value - new value to be stored in this entry.
         * @return old value corresponding to the entry.
         */
        @Override
        public Object setValue(Object o)
        {
            Object ret = val;
            val = o;
            return ret;
        }
    }

    private class EntrySet implements HSet
    {
        /**
         * Unsupported Operation
         */
        @Override
        public boolean add(Object o)
        {
            throw new UnsupportedOperationException();
        }

        /**
         * Unsupported Operation
         */
        @Override
        public boolean addAll(HCollection c)
        {
            throw new UnsupportedOperationException();
        }

        /**
         * Removes all of the elements from this set.
         * This set and the map it is backed by will be empty after this call returns.
         */
        @Override
        public void clear()
        {
            map.clear();
        }

        /**
         * Returns {@code true} if this set contains the specified element.
         * 
         * @param o - element whose presence in this set is to be tested.
         * @return {@code true} if this set contains the specified element.
         * @throws NullPointerException - if the specified element is null.
         */
        @Override
        public boolean contains(Object o)
        {
            if (o == null)
                throw new NullPointerException();

            if (!(o instanceof HMap.Entry))
                return false;

            HMap.Entry e = (HMap.Entry)o;

            return map.containsKey(e.getKey()) && (map.get(e.getKey()) == e.getValue());
        }

        /**
         * Returns {@code true} if this set contains all of the elements of the specified collection.
         * If the specified collection is also a set, this method returns {@code true} if it is a subset of this set.
         * 
         * @param c - collection to be checked for containment in this set.
         * @return {@code true} if this set contains all of the elements of the specified collection.
         * @throws NullPointerException - if the specified collection is null.
         * @throws NullPointerException - if the specified collection contains one or more null elements.
         */
        @Override
        public boolean containsAll(HCollection c)
        {
            if(c == null)
                throw new NullPointerException();

            HIterator i = c.iterator();
            while (i.hasNext())
                if (!contains(i.next())) return false;
            
            return true;
        }

        /**
         * Compares the specified object with this set for equality.
         * Returns true if the specified object is also a set, the two sets have the same size,
         * and every member of the specified set is contained in this set.
         * 
         * @param o - Object to be compared for equality with this set.
         */
        @Override
        public boolean equals(Object o)
        {
            if (o == null || !(o instanceof HSet))
                return false;
            
            HSet s = (HSet)o;
            if (size() != s.size())
                return false;
            
            return containsAll(s);
        }

        /**
         * Returns the hash code value for this set.
         * The hash code of a set is defined to be the sum of the hash codes of the elements in the set.
         * This ensures that {@code s1.equals(s2)} implies that {@code s1.hashCode()==s2.hashCode()}
         * for any two sets s1 and s2.
         * 
         * @return the hash code value for this set.
         */
        @Override
        public int hashCode()
        {
            int ret = 0;
            HIterator i = iterator();
            while (i.hasNext())
                ret += i.next().hashCode();

            return ret;
        }

        /**
         * Returns {@code true} if this set contains no elements.
         * 
         * @return {@code true} if this set contains no elements.
         */
        @Override
        public boolean isEmpty()
        {
            return size() <= 0;
        }

        /**
         * Returns an iterator over the elements in this set.
         * 
         * @return an iterator over the elements in this set.
         */
        @Override
        public HIterator iterator()
        {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * Removes the specified element from this set if it is present.
         * Returns {@code true} if the set contained the specified element.
         * The set and the map it is backed by will not contain the specified element once the call returns.
         * 
         * @param o - object to be removed from this set, if present.
         * @return {@code true} if the set contained the specified element.
         * @throws NullPointerException - if the specified element is null.
         */
        @Override
        public boolean remove(Object o)
        {
            if (o == null)
                throw new NullPointerException();

            if (!(o instanceof HMap.Entry))
                return false;

            HMap.Entry e = (HMap.Entry)o;
            
            return map.remove(e.getKey()) != null;
        }

        /**
         * Removes from this set all of its elements that are contained in the specified collection.
         * 
         * @param c - collection that defines which elements will be removed from this set.
         * @return {@code true} if this set changed as a result of the call.
         * @throws NullPointerException - if this set contains a null element.
         * @throws NullPointerException - if the specified collection is null.
         */
        @Override
        public boolean removeAll(HCollection c)
        {
            if (c == null)
                throw new NullPointerException();

            int size = size();
            HIterator i = c.iterator();
            while (i.hasNext())
            {
                Object o = i.next();
                if(contains(o)) remove(o);
            }

            return size != size();
        }

        /**
         * Retains only the elements in this set that are contained in the specified collection.
         * In other words, removes from this set all of its elements that are not contained in the specified collection.
         * 
         * @param c - collection that defines which elements this set will retain.
         * @return {@code true} if this set changed as a result of the call.
         * @throws NullPointerException - if this set contains a null element.
         * @throws NullPointerException - if the specified collection is null.
         */
        @Override
        public boolean retainAll(HCollection c)
        {
            if (c == null)
                throw new NullPointerException();

            int size = size();
            HIterator i = iterator();
            while (i.hasNext())
            {
                Object o = i.next();
                if(!c.contains(o)) i.remove();
            }
    
            return size != size();
        }

        /**
         * Returns the number of elements in this set (its cardinality).
         * 
         * @return the number of elements in this set.
         */
        @Override
        public int size()
        {
            return map.size();
        }

        /**
         * Returns an array containing all of the elements in this set.
         * 
         * @return an array containing all of the elements in this set.
         */
        @Override
        public Object[] toArray()
        {   
            int i = 0;
            HIterator it = iterator();
            Object[] ret = new Object[map.size()];

            while (it.hasNext())
                ret[i++] = it.next();

            return ret;
        }

        /**
         * Returns an array containing all of the elements in this set.
         * 
         * @param a - the array into which the elements of this set are to be stored, if it is big enough, 
         *        otherwise, a new array of the same runtime type is allocated for this purpose.
         * @return an array containing all of the elements in this set.
         * @throws NullPointerException - if the specified array is null.
         * @throws ArrayStoreException - the runtime type of a is not a supertype of the runtime type
         *         of every element in this set.
         */
        @Override
        public Object[] toArray(Object[] a)
        {
            if(a == null || a.length < size())
                return toArray();

            int i = 0;
            HIterator it = iterator();

            //può generare ArrayStoreException.
            while (it.hasNext())
                a[i++] = it.next();

            return a;
        }
    }

    private class KeySet extends EntrySet
    {
        /**
         * Returns {@code true} if this set contains the specified element.
         * 
         * @param o - element whose presence in this set is to be tested.
         * @return {@code true} if this set contains the specified element.
         * @throws NullPointerException - if the specified element is null.
         */
        @Override
        public boolean contains(Object o)
        {
            if (o == null)
                throw new NullPointerException();

            return map.containsKey(o);
        }

        /**
         * Returns an iterator over the elements in this set.
         * 
         * @return an iterator over the elements in this set.
         */
        @Override
        public HIterator iterator()
        {
            // TODO Auto-generated method stub
            return super.iterator();
        }

        /**
         * Removes the specified element from this set if it is present.
         * Returns {@code true} if the set contained the specified element.
         * The set and the map it is backed by will not contain the specified element once the call returns.
         * 
         * @param o - object to be removed from this set, if present.
         * @return {@code true} if the set contained the specified element.
         * @throws NullPointerException - if the specified element is null.
         */
        @Override
        public boolean remove(Object o)
        {
            if (o == null)
                throw new NullPointerException();

            return map.remove(o) != null;
        }        
    }

    private class ValueSet extends EntrySet
    {
        /**
         * Returns {@code true} if this set contains the specified element.
         * 
         * @param o - element whose presence in this set is to be tested.
         * @return {@code true} if this set contains the specified element.
         * @throws NullPointerException - if the specified element is null.
         */
        @Override
        public boolean contains(Object o)
        {
            if (o == null)
                throw new NullPointerException();

            return map.contains(o);
        }

        /**
         * Returns an iterator over the elements in this set.
         * 
         * @return an iterator over the elements in this set.
         */
        @Override
        public HIterator iterator()
        {
            // TODO Auto-generated method stub
            return super.iterator();
        }

        /**
         * Removes the specified element from this set if it is present.
         * Returns {@code true} if the set contained the specified element.
         * The set and the map it is backed by will not contain the specified element once the call returns.
         * 
         * @param o - object to be removed from this set, if present.
         * @return {@code true} if the set contained the specified element.
         * @throws NullPointerException - if the specified element is null.
         */
        @Override
        public boolean remove(Object o)
        {
            if (o == null)
                throw new NullPointerException();

            HIterator i = super.iterator();
            while (i.hasNext())
            {
                if (((Entry)i.next()).getValue() == o)
                {   
                    i.remove();
                    return true;
                }
            }

            return false;
        }
    }
}