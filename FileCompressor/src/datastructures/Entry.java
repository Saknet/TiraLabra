
package datastructures;

/**
 * This class implements an Entry in HashMap.
 * @param <K>
 * @param <V> 
 */
public class Entry<K, V> {
    
    /**
     * Entry's key element.
     */
    private K key;
    
    /**
     * Entry's value element.
     */
    private V value;
    
    /**
     * Pointer to next Entry.
     */
    private Entry<K, V> next;
    
    public Entry(K key, V value, Entry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
    
    public K getKey() {
        return this.key;
    }
    
    public V getValue() {
        return this.value;
    }
    
    public Entry<K, V> getNext() {
        return this.next;
    }
    
    public void setNext(Entry<K, V> next) {
        this.next = next;
    }
    
    public void setValue(V value) {
        this.value = value;
    }
    
    public void setKey(K key) {
        this.key = key;
    }
}
