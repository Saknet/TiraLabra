
package huffmancoding;

import java.util.HashMap;
import java.util.PriorityQueue;

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
        PriorityQueue<Node> pq = addSymbolsToPQ(frequencies);
        return buildTree(pq);
    }
    
    /**
     * Method that adds frequencies of the symbols as nodes into priority queue.
     * @param frequencies HashMap<Character, Integer> contains symbols and its frequencies.
     * 
     * @return pq, filled priority queue.
     */
    public PriorityQueue<Node> addSymbolsToPQ(HashMap<Character, Integer> frequencies) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Character c : frequencies.keySet()) {
            pq.add(new Node(c, frequencies.get(c)));
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
    public Node buildTree(PriorityQueue<Node> pq) {
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.getFrequency() + right.getFrequency(), left, right);
            pq.add(parent);
        }
        return pq.poll();
    }   
}
