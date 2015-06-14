
package huffmancoding;

/**
 * Class that represents a node in the huffman tree.
 */

public class Node {
    /**
     * char symbol, symbol in the node
     */
    private char symbol;
    
    /**
     * integer frequency, the count of frequencies of a symbol in the node.
     */
    private int frequency;
    
    /**
     * Node left, current nodes left child.
     */  
    private Node left;
    
    /**
     * Node right, current nodes right child.
     */
    private Node right;
    
    /**
     * Constructor for leaf node
     * 
     * @param symbol
     * @param frequency 
     */
    public Node(char symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }
    
    /**
     * 
     * Constructor for non leaf node
     * 
     * @param frequency
     * @param left
     * @param right 
     */
    public Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
    
    public Node getLeft() {
        return this.left;
    }
    
    public Node getRight() {
        return this.right;
    }
    
    public char getSymbol() {
        return this.symbol;
    }
    
    public int getFrequency() {
        return this.frequency;
    }
}
