import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of a ExternalChainingHashMap.
 *
 * @author Majd Khawaldeh
 * @version 1.0
 * @userid majd
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public ExternalChainingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ExternalChainingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public ExternalChainingHashMap(int initialCapacity) {
        table = new ExternalChainingMapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     * <p>
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. Resize if the load factor is greater than max LF (it is okay
     * if the load factor is equal to max LF). For example, let's say the
     * array is of length 5 and the current size is 3 (LF = 0.6). For this
     * example, assume that no elements are removed in between steps. If
     * another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
      public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The entered key is null, or the entered value is null!");
        }

        if ((size + 1) / (double) table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }

        int ind = Math.abs(key.hashCode() % table.length);

        if (table[ind] == null) {
            table[ind] = new ExternalChainingMapEntry<>(key, value);
            size++;
            return null;
        } else {
            ExternalChainingMapEntry<K, V> entry = table[ind];

            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }

            while (entry.getNext() != null && !entry.getNext().getKey().equals(key)) {
                entry = entry.getNext();
            }

            if (entry.getNext() != null && entry.getNext().getKey().equals(key)) {
                V oldValue = entry.getNext().getValue();
                entry.getNext().setValue(value);
                return oldValue;
            }

            entry.setNext(new ExternalChainingMapEntry<>(key, value));
            size++;
            return null;
        }
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
     public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key entered is null!");
        }

        int ind = Math.abs(key.hashCode() % table.length);

        if (table[ind] == null) {
            throw new NoSuchElementException("The key entered does not exist in this map.");
        }

        if (table[ind].getKey().equals(key)) {
            V oldValue = table[ind].getValue();
            table[ind] = table[ind].getNext();
            size--;
            return oldValue;
        }

        ExternalChainingMapEntry<K, V> previous = table[ind];
        ExternalChainingMapEntry<K, V> current = previous.getNext();

        while (current != null && !current.getKey().equals(key)) {
            previous = current;
            current = current.getNext();
        }

        if (current != null && current.getKey().equals(key)) {
            V oldValue = current.getValue();
            previous.setNext(current.getNext());
            size--;
            return oldValue;
        }

        throw new NoSuchElementException("The key entered does not exist in this map.");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {

        if (key == null) {
            throw new IllegalArgumentException("The entered key cannot be null.");
        }
        int ind = Math.abs(key.hashCode() % table.length);
        ExternalChainingMapEntry<K, V> entry = table[ind];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
            entry = entry.getNext();
        }
        throw new NoSuchElementException("The entered key does not exist in the map!");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The entered key cannot be null.");
        }
        int ind = Math.abs(key.hashCode() % table.length);
        ExternalChainingMapEntry<K, V> entry = table[ind];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return true;
            }
            entry = entry.getNext();
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * <p>
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
   public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        int count = 0;
        
        for (ExternalChainingMapEntry<K, V> entry : table) {
            while (entry != null) {
                keys.add(entry.getKey());
                entry = entry.getNext();
                count++;

                if (count == size) return keys;
            }
        }

        return keys;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
  public List<V> values() {
        List<V> values = new ArrayList<>();
        int count = 0;

        for (ExternalChainingMapEntry<K, V> entry : table) {
            while (entry != null) {
                values.add(entry.getValue());
                entry = entry.getNext();
                count++;

                if (count == size) return values;
            }
        }

        return values;
    }

    /**
     * Resize the backing table to length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * <p>
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * <p>
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
      public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("The length given cannot be less than the current amount of items.");
        }

        ExternalChainingMapEntry<K, V>[] newTable = new ExternalChainingMapEntry[length];
        for (ExternalChainingMapEntry<K, V> entry : table) {
            while (entry != null) {
                int ind = Math.abs(entry.getKey().hashCode() % newTable.length);

                if (newTable[ind] == null) {
                    newTable[ind] = new ExternalChainingMapEntry<>(entry.getKey(), entry.getValue());
                } else {
                    ExternalChainingMapEntry<K, V> current = newTable[ind];
                    while (current.getNext() != null) {
                        current = current.getNext();
                    }
                    current.setNext(new ExternalChainingMapEntry<>(entry.getKey(), entry.getValue()));
                }
                entry = entry.getNext();
            }
        }
        table = newTable;
    }

    /**
     * Clears the map.
     * <p>
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     */
    public void clear() {
        table = new ExternalChainingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
