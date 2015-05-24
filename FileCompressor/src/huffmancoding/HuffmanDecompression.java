
package huffmancoding;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * täysin kesken oleva luokka, varmaan paljon tulee muuttumaan, ei myöskään testattu vielä.
*/
public class HuffmanDecompression {
        
//    public void run(String originalFile) throws FileNotFoundException, IOException {
        
//        HashMap<Character, Integer> frequencies = addFrequencies(originalFile);
//        PriorityQueue<Node> pq = addSymbolsToPQ(frequencies);
//        String newFile = "huffman";
//        Node root = makeTree();

//    }
    
//    public Node makeTree() throws IOException {
//        PriorityQueue<Node> pq = new PriorityQueue<>();
//       while (pq.size() > 1) {
//            Node left = pq.poll();
//            Node right = pq.poll();
//            Node parent = new Node(left.getFrequency() + right.getFrequency(), left, right);
//            pq.add(parent);
//        }
//        return pq.poll();
//    }
    
        /**
     * Method that adds character frequencies in the original file to HashMap. Not working and no time to fix yet.
     * @param originalFile the Original file.
     * @return frequencies HashMap<Character, Integer> contains symbols and its frequencies.   
     * @throws java.io.IOException  
     */
//    public HashMap<Character, Integer> addFrequencies(String originalFile) throws IOException {
//        FileInputStream fis = new FileInputStream(originalFile);
//        HashMap<Character, Integer> frequencies = new HashMap<>();
//        String s = "";
//        while ((char) fis.read() != '£') {
//            char c = (char) fis.read();
//            s = ""+ c;
//        }
//        fis.close();
        
//        for (int i = 0; i < s.length(); i++) {
//            if (!Character.isDigit(s.charAt(i))) {
//                frequencies.put(s.charAt(i), Integer.parseInt(""+s.charAt(i + 1)));
//            }
//        }
        
//        return frequencies;
//    }
    
    /**
     * Method that adds frequencies of the symbols as nodes into priority queue.
     * @param s
     * @return 
     */
//    public PriorityQueue<Node> addSymbolsToPQ(HashMap<Character, Integer> frequencies) {
//        PriorityQueue<Node> pq = new PriorityQueue<>();
//        for (Character c : frequencies.keySet()) {
//            pq.add(new Node(c, frequencies.get(c)));
//        }
//        return pq;
//    }
    
}
