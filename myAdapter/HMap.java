package myAdapter;

public interface HMap
{
    public static interface Entry
    {
        boolean equals(Object o);

        Object getKey();

        Object getValue();

        int hashCode();

        Object setValue(Object o);
    }

    void clear();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    HSet entrySet();

    boolean equals(Object o);

    Object get(Object key);

    int hashCode();

    boolean isEmpty();

    HSet keySet();

    Object put(Object key, Object value);

    void putAll(HMap t);

    Object remove(Object key);

    int size();

    HCollection values();
}