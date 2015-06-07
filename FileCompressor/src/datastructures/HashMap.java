
package datastructures;
 
/**
 * My version of Java's HashMap data structure.
 * @param <K>
 * @param <V>
 */
public class HashMap<K, V> {
    
    /**
     * HashMap implemented as Array of Entries.
     */
    private final Entry<K, V>[] hashMap;
    
    /**
     * Size of the HashMap.
     */
    private final int size = 256;
    
    /**
     * This ArrayList contains the key values of the HashMap.
     */
    private final ArrayList<K> keySet;
    
    public HashMap() {
        this.hashMap = new Entry[size];
        this.keySet = new ArrayList<>();
    }
    
    /**
     * Adds Key and Value into the map. If Map already contains the key
     * the old value is replaced.
     * @param key K
     * @param value V  
     */
    public void put(K key, V value) {
        int hash = hash(key);
        Entry entry = hashMap[hash];
        if (!containsKey(key)) {
            keySet.add(key);
        }
        if(entry != null) {
            if(entry.getKey().equals(key)) {
                entry.setValue(value);
            } else {
                while(entry.getNext() != null) {
                    entry = entry.getNext();
                }
                entry.setNext(new Entry(key, value, null));
            }
        } else {
            hashMap[hash] = new Entry(key, value, null);
        }
    }
    
    /**
     * Getter for keyset.
     * @return keySet ArrayList<K> contains the keys.
     */
    public ArrayList<K> keySet() {
        return keySet;
    }
    
    /**
     * Returns the value associated with the specified key in the HashMap.
     * If the HashMap has no mapping for the key it returns null.
     * @param key K.
     * @return 
     */
    public V get(K key) {
        Entry<K, V> entry = hashMap[hash(key)];
        if (entry == null) {
            return null;
        }
        while (entry.getKey().hashCode() != key.hashCode()) {
            entry = entry.getNext();
            if (entry == null) {
                return null;
            }
        }
        return entry.getValue();
    }
    
    /**
     * Checks if HashMap contains the key element. Returns true if it does,
     * false if it don't.
     * @param key K, the key element.
     * @return 
     */
    public boolean containsKey(K key) {
        if (hashMap[hash(key)] == null) {
            return false;
        } else {
            Entry<K, V> temp = hashMap[hash(key)];
            while (temp != null) {
                if (temp.getKey().equals(key)) {
                    return true;
                }
                temp = temp.getNext();
            }
        }
        return false;
    }
    
    /**
     * Almighty "hash function" ;).
     * @param key K
     * @return Integer
     */
    private int hash(K key) {
        return key.hashCode() % size;
    }
}
