
package datastructures;

/**
 * My version of Java's mighty ArrayList data structure. 
 * @param <E> 
 */
public class ArrayList<E> {
    
    private Object[] list;
    private int size;
    
    public ArrayList() {
        this.list = new Object[256];
        this.size = 0;  
    }
    
    /**
     * If lists size is 0 uses method init to initialize list, 
     * else increases list size and adds the object at the end of list.
     * 
     * @param o Object, object that will be added into list.
     */
    public void add(Object o) {
        if (size == list.length) {
            makeListBigger();
        } 
        list[size] = o;
        size++;       
    }
    
    /**
     * Increases the size of list by making new list that is 1 element bigger than old
     * list, then copies the old list into new one.
     */
    public void makeListBigger() {
        Object[] biggerList = new Object[list.length * 2];
        System.arraycopy(list, 0, biggerList, 0, size);
        list = biggerList; 
    }
    
    
    /**
     * Method used for getting element from list.
     * 
     * @param index Integer, the index of element requested.
     * @return 
     */
    public E get(int index) {
        checkRange(index);
        return (E) list[index];
    }
    
    /**
     * Check if index is inside the list.
     * 
     * @param index   
     */
    public void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    /**
     * Getter for getting current size of the list.
     * 
     * @return size Integer 
     */
    public int size() {
        return size;
    }
}