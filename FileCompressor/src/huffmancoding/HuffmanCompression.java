
package huffmancoding;

import IO.BinaryOutput;
import datastructures.ArrayList;
import datastructures.HashMap;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(createOutput(originalFile))); 
        HashMap<Character, Integer> frequencies = addFrequencies(originalFile);
        TreeBuilder tree = new TreeBuilder();
        Node root = tree.makeTree(frequencies);
        readTree(root, "");        
        compressText(originalFile, dos, frequencies);
    }
    
    /**
     * Method that uses original file to make name for new file and returns 
     * FileOutputStream for writing the new file.
     * 
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
     * 
     * @param originalFile String, the Original file.
     * @return frequencies HashMap<Character, Integer>, contains symbols and their frequencies.   
     * @throws java.io.IOException  
     */
    public HashMap<Character, Integer> addFrequencies(String originalFile) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(originalFile)));
        HashMap<Character, Integer> frequencies = new HashMap<>();
        while (dis.available() > 0) {
            char c = (char) dis.read();
            if (frequencies.containsKey(c)) {
                frequencies.put(c, frequencies.get(c) + 1);
            } else {
                frequencies.put(c, 1);
            }
        }
        dis.close();
        
        return frequencies;
    }
    
    /**
     * Method that is used for creating the code for each character in original text.
     * 
     * It creates the code by traveling recursively in the tree.
     * 
     * @param node current node, starting in the root.
     * @param code current code, starting with empty string.
     */
    public void readTree(Node node, String code) {
        if (node.getLeft() == null) {
            charToCode.put(node.getSymbol(), code);
            return;
        }
        readTree(node.getLeft(), code + "0");
        readTree(node.getRight(), code + "1");
    }
     
    /**
     * Method that writes symbols and its frequencies to output file. Symbol $ is 
     * used to mark the end of frequencies and the real compressed data begins after it.
     * 
     * @param frequencies HashMap<Character, Integer>, contains symbols and their frequencies.  
     * @param bo BinaryOutput, used for writing symbols and their frequencies into file.
     * @throws java.io.IOException
    */
    public void writeFrequenciesToFile(HashMap<Character, Integer> frequencies, BinaryOutput bo) throws IOException {
        ArrayList<Character> keys = frequencies.keySet();
        for (int i = 0; i < keys.size(); i++) {
            bo.writeByte(keys.get(i));
            int freq = frequencies.get(keys.get(i));
            bo.write(freq, 32);
        }
        bo.writeByte('$');
    }
     
    /**
     * First this method starts method that writes frequencies at start of the file, then it 
     * reads original file as inputstream and uses writeFile method to compress the data one char time.
     * Symbol £ is used to mark the end of compression.
     * 
     * @param originalFile String, the original file.
     * @param fos FileOutputStream, used for output.
     * @param frequencies HashMap<Character, Integer>, contains symbols and their frequencies.
     * @throws IOException 
     */
    public void compressText(String originalFile, DataOutputStream dos, HashMap<Character, Integer> frequencies) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(originalFile)));
        BinaryOutput bo = new BinaryOutput(dos);
        writeFrequenciesToFile(frequencies, bo);
        while (dis.available() > 0) {
            writeFile(bo, (char) dis.read());
        }    
        dis.close();
        writeFile(bo, '£');
        bo.close();
    }
    
    /**
     * Writes the output file with help charToCode and use of BinaryOutput class.
     * 
     * @param bo BinaryOutput
     * @param c char that is currently being compressed and written into file.
     * @throws IOException 
     */
    public void writeFile(BinaryOutput bo, char c) throws IOException {
        String code = charToCode.get(c);
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '1') {
                bo.writeBit(true);
            } else {
                bo.writeBit(false);
            }
        }      
    }
    
    /**
     * Getter used for testing, might get removed later.
     * 
     * @return charToCode HashMap<Character, String>
    */
    public HashMap<Character, String> getcharToCode() {
        return charToCode;
    }
}
