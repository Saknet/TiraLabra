
package huffmancoding;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Class that compresses the file with Huffman's Code algorithm.
 */
public class HuffmanCompressor {
    
    /**
     * HashMap for counting the character frequencies in the file.
     */
    private HashMap<Character, Integer> frequencies = new HashMap<>();
    
    /**
     * PriorityQueue for making the Huffman tree.
     */
    private PriorityQueue<Node> pq = new PriorityQueue<>();
    
    /**
     * HashMap used for coding the file.
     */
    private HashMap<Character, String> charToCode = new HashMap<>();
    
    /**
     * DataOutputStream used for writing new file.
     */
    private DataOutputStream dos;
    
    /**
     * The main method of compressor. Starts all other methods needed for data compression.
     * 
     * @param originalFile Name of original file.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void run(String originalFile) throws FileNotFoundException, IOException {
        String newFile = originalFile + ".huffman";
        dos = new DataOutputStream(new FileOutputStream(newFile));
        addFrequencies(originalFile);
        addSymbolsToPQ();
        Node root = buildTree();
        readTree(root, "");
        writeTreeToFile();
        compressText(originalFile);
    }
    
    /**
     * Method that adds character frequencies in the text to HashMap.
     * @param text 
     */
    public void addFrequencies(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (frequencies.containsKey(c)) {
                frequencies.put(c, frequencies.get(c) + 1);
            } else {
                frequencies.put(c, 1);
            }
        }
    }
    
    /**
     * Method that adds frequencies of the symbols as nodes into priority queue.
     */
    public void addSymbolsToPQ() {
        for (Character c : frequencies.keySet()) {
            pq.add(new Node(c, frequencies.get(c)));
        }
    }
    
    /**
     * Method that is used for creating the Huffman's tree.
     * @return returns root of the tree.
     */
    public Node buildTree() {
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.getFrequency() + right.getFrequency(), left, right);
            pq.add(parent);
        }
        return pq.poll();
    }
    
    /**
     * Method that is used for creating the code for each character in original text.
     * It creates the code by traveling recursively in the tree.
     * @param node current node, starting in the root.
     * @param code current code, starting with empty string.
     */
    public void readTree(Node node, String code) {
        if (node.getRight() == null) {
            charToCode.put(node.getSymbol(), code);
            return;
        }
        readTree(node.getLeft(), code + "1");
        readTree(node.getRight(), code + "0");
    }
    
    /**
     * Method that should write the Huffman's tree into file, but at the moment writes 
     * frequencies there. Dollar symbol is used at the end as mark for end of the tree.
     * @throws IOException 
     */
    public void writeTreeToFile() throws IOException {
        for (Character c : frequencies.keySet()) {
            writeSymbolAndFrequency(c, frequencies.get(c));
        }
        dos.write('$');
    }
    
    /**
     * Writes character symbol and its frequencies into file, pound and euro symbols
     * are used between them
     * @param symbol
     * @param frequency
     * @throws IOException 
     */
    public void writeSymbolAndFrequency(char symbol, int frequency) throws IOException {
        dos.write('£');
        dos.write(symbol);
        dos.write('€');
        dos.write(frequency);
    }
    
    /**
     * Writes Huffman code into file. Probably not working yet.
     * @param text
     * @throws IOException 
     */
    public void compressText(String text) throws IOException {
        for (int i = 0; i < text.length(); i++) {
            dos.writeChars(charToCode.get(text.charAt(i)));
        }
        dos.close();
    }
}
