
package datastructures;

import huffmancoding.Node;


/**
 * My version of Java's PriorityQueue data structure.
 */
public class PriorityQueue {
    /**
     * Array of Nodes that will implement the priority queue.
     */
    private final Node[] priorityQueue;
    
    /**
     * Integer that tells the the size of the priority queue.
     */
    private int size = 0;
    
    /**
     * Constructor, Priorityqueues's array size is set to 256 (number of ascii characters).
     */
    public PriorityQueue() {
        this.priorityQueue = new Node[256];       
    }
    
    /**
     * Finds the right place for the node in priority queue.
     *
     * @param node Node, which will be added.
     */
    public void add(Node node) {
        int i = size;
        while (i > 0 && node.getFrequency() < priorityQueue[(i - 1) / 2].getFrequency()) {
            priorityQueue[i] = priorityQueue[(i - 1) / 2];
            i = (i - 1) / 2;
        }
        addNode(node, i);
    }
    
    /**
     * Adds node into priority queue and increases the queue's size.
     * 
     * @param node Node, which will be added to the priority queue.
     * @param i Integer, the place where node will be added.
     */
    private void addNode(Node node, int i) {
        priorityQueue[i] = node;
        size++;      
    }

    /**
     * Getter for getting the size of the priority queue.
     * @return size, Int.
     */
    public int size() {
        return size;
    }
    /**
     * Returns the node with smallest value from priority queue and deletes it.
     *
     * @return node Node
     */
    public Node poll() {
        Node node = priorityQueue[0];
        priorityQueue[0] = priorityQueue[size - 1];
        size--;
        heapify();
        return node;
    }

    /**
     * Heapify method that keeps the nodes in the priority queue in right order.
     *
     */
    private void heapify() {
        int i = 0;
        int smallest = findSmallest(i);

        while (smallest < size && priorityQueue[i].getFrequency() > priorityQueue[smallest].getFrequency()) {
            swap(i, smallest);
            i = smallest;
            smallest = findSmallest(i);
        }
    }
    
    /**
     * Checks which one of the Node's child nodes has smallest frequencies.
     * @param i
     * @return 
     */
    private int findSmallest(int i) {
        if (2 * (i + 1) < size && priorityQueue[2 * (i + 1)].getFrequency() < priorityQueue[2 * i + 1].getFrequency()) {
            return 2 * (i + 1);
        } else {
            return  2 * i + 1;
        }        
    }
    
    /**
     * Swaps the locations of 2 nodes in the priority queue.
     * @param i int, location of first node.
     * @param j int, location of second node. 
     */
    private void swap(int i, int j) {
        Node temp = priorityQueue[i];
        priorityQueue[i] = priorityQueue[j];
        priorityQueue[j] = temp;
    }
}
