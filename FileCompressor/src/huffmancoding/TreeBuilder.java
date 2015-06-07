
package huffmancoding;

import datastructures.ArrayList;
import datastructures.HashMap;
import datastructures.PriorityQueue;

/**
 * Class used for building Huffman's tree
 */
public class TreeBuilder {
    
    /**
     * Method that makes Huffman's tree.
     * 
     * @param frequencies HashMap<Character, Integer> contains symbols and its frequencies.
     * @return root Node of Huffman's tree.
     */
    public Node makeTree(HashMap<Character, Integer> frequencies) {
        PriorityQueue pq = addSymbolsToPQ(frequencies);
        return buildTree(pq);
    }
    
    /**
     * Method that adds frequencies of the symbols as nodes into priority queue.
     * @param frequencies HashMap<Character, Integer> contains symbols and its frequencies.
     * 
     * @return pq, filled priority queue.
     */
    public PriorityQueue addSymbolsToPQ(HashMap<Character, Integer> frequencies) {
        PriorityQueue pq = new PriorityQueue();
        ArrayList<Character> keys = frequencies.keySet();
        for (int i = 0; i < keys.size(); i++) {;
            pq.add(new Node(keys.get(i), frequencies.get(keys.get(i))));
        }
        pq.add(new Node('£', 0));
        return pq;
    }
    
    /**
     * Method that is used for creating the Huffman's tree.
     * 
     * @param pq PriorityQueue, data structure used for creating the tree.
     * @return returns root of the tree.
     */
    public Node buildTree(PriorityQueue pq) {
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.getFrequency() + right.getFrequency(), left, right);
            pq.add(parent);
        }
        return pq.poll();
    }   
}
