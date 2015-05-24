
package huffmancoding;

import IO.BinaryOutput;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Class that compresses the file with Huffman's Code algorithm.
 */
public class HuffmanCompression {
    
    /**
     * HashMap used for coding the file.
     */
    private final HashMap<Character, String> charToCode = new HashMap<>();
    
    /**
     * The main method of compressor. Starts all other methods needed for data compression.
     * 
     * @param originalFile String the original file which will be compressed.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void run(String originalFile) throws FileNotFoundException, IOException {
        FileOutputStream fos = createOutput(originalFile); 
        HashMap<Character, Integer> frequencies = addFrequencies(originalFile);
        PriorityQueue<Node> pq = addSymbolsToPQ(frequencies);
        Node root = buildTree(pq);
        readTree(root, "");
        writeTreeToFile(root, fos);
        fos.write('£');
        compressText(originalFile, fos);
    }
    
    /**
     * Method that uses original file to make name for new file and returns 
     * FileOutputStream for the new file.
     * @param originalFile String, the original file.
     * @return fos FileOutputStream, used for output.
     * @throws FileNotFoundException 
     */  
    public FileOutputStream createOutput(String originalFile) throws FileNotFoundException {
        String newFile = originalFile + ".huffman";     
        FileOutputStream fos = new FileOutputStream(newFile);
        return fos;
    }
    
    /**
     * Method that adds character frequencies in the original file to HashMap.
     * @param originalFile String, the Original file.
     * @return frequencies HashMap<Character, Integer>, contains symbols and its frequencies.   
     * @throws java.io.IOException  
     */
    public HashMap<Character, Integer> addFrequencies(String originalFile) throws IOException {
        FileInputStream fis = new FileInputStream(originalFile);
        HashMap<Character, Integer> frequencies = new HashMap<>();
        while (fis.available() > 0) {
            char c = (char) fis.read();
            if (frequencies.containsKey(c)) {
                frequencies.put(c, frequencies.get(c) + 1);
            } else {
                frequencies.put(c, 1);
            }
        }
        fis.close();
        
        return frequencies;
    }
    
    /**
     * Method that adds frequencies of the symbols as nodes into priority queue.
     * @param frequencies HashMap<Character, Integer> contains symbols and its frequencies.
     * @return pq, filled priority queue.
     */
    public PriorityQueue<Node> addSymbolsToPQ(HashMap<Character, Integer> frequencies) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Character c : frequencies.keySet()) {
            pq.add(new Node(c, frequencies.get(c)));
        }
        return pq;
    }
    
    /**
     * Method that is used for creating the Huffman's tree.
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
        readTree(node.getLeft(), code + "0");
        readTree(node.getRight(), code + "1");
    }
    
    /**
     * Method that writes the Huffman's tree into file.
     * TODO write the tree also as binary (will save space).
     * 
     * @param node, node which is currently being read.
     * @param fos, FileOutpuStream for writing nodes into file.
     * @throws IOException 
     */
    public void writeTreeToFile(Node node, FileOutputStream fos) throws IOException {
        if (node.getRight() == null) {
            String s = "" + node.getSymbol() + node.getFrequency();          
            fos.write(s.getBytes());
        } else {
            writeTreeToFile(node.getLeft(), fos);
            writeTreeToFile(node.getRight(), fos);
        }
    }
     
    /**
     * Reads original file as inputstream and uses writeFile method to compress the data one char time.
     * @param originalFile String, the original file.
     * @param fos FileOutputStream, used for output.
     * @throws IOException 
     */
    public void compressText(String originalFile, FileOutputStream fos) throws IOException {
        FileInputStream fis = new FileInputStream(originalFile);
        BinaryOutput bo = new BinaryOutput(fos);
        while (fis.available() > 0) {
            writeFile(bo, (char) fis.read());
        }        
        bo.close();
    }
    
    /**
     * Writes the output file with help charToCode and use of BinaryOutput class.
     * @param bo BinaryOutput
     * @param c char that is currently being compressed and written into file.
     * @throws IOException 
     */
    public void writeFile(BinaryOutput bo, char c) throws IOException {
        String code = charToCode.get(c);
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0') {
                bo.writeBit(false);
            }
            if (code.charAt(i) == '1' ) {
                bo.writeBit(true);
            }
        }      
    }
    
    /**
     * Getter used for testing, might get removed later.
     * @return charToCode HashMap<Character, String>
    */
    public HashMap<Character, String> getcharToCode() {
        return charToCode;
    }
}
