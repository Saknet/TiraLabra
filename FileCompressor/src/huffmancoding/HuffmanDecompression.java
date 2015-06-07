
package huffmancoding;

import IO.BinaryInput;
import IO.BinaryOutput;
import datastructures.HashMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;;

public class HuffmanDecompression {
           
    /**
     * The main method of decompressor. Starts all other methods needed for data decompression.
     * 
     * @param originalFile String the original file, which will be decompressed.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void run(String originalFile) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(originalFile);
        BinaryInput bi = new BinaryInput(fis);
        HashMap<Character, Integer> frequencies = readFrequenciesFromFile(bi);
        TreeBuilder tree = new TreeBuilder();
        Node root = tree.makeTree(frequencies);
        FileOutputStream fos = createOutput(originalFile);
        decompress(root, bi, fos);
    }
    
    /**
     * Decompresses the original data by finding the next symbol with findNextSymbol method and then 
     * writes the symbol into output file. Symbol £ is used to mark the end of line.
     * 
     * @param node Node, the root node of Huffman's tree.
     * @param bi BinaryInput, the input file stream.
     * @param fos FileOutputStream, file output stream used for writing output file.
     * @throws IOException 
     */
    public void decompress(Node node, BinaryInput bi, FileOutputStream fos) throws IOException {
        BinaryOutput bo = new BinaryOutput(fos);
        while (true) {
            char symbol = findNextSymbol(node, bi);
            if (symbol == '£') {
                break;
            } else {
                bo.writeByte(symbol);
            }
        }
        bo.close();
    }
    
    /**
     * Method that uses Huffman's tree to find next symbol to be written into output file.
     * @param node Node, the root of Huffman's tree.
     * @param bi BinaryInput, the input stream, used for reading bits.
     * @return returns the right symbol from Huffman's tree.
     * @throws IOException 
     */
    public char findNextSymbol(Node node, BinaryInput bi) throws IOException {
        if (node.getRight() == null) {
            return node.getSymbol();            
        } 
        else if (bi.readBit()) {
            return findNextSymbol(node.getRight(), bi);
        } else {
            return findNextSymbol(node.getLeft(), bi);
        }
    }
    
    /**
     * Method that reads the symbols and their frequencies from input stream. Symbol $ 
     * is used to mark the end of frequencies.
     * 
     * @param bi BinaryInput, the binary input stream of compressed file.
     * @return frequencies, HashMap<Character, Integer>, contains symbols and its frequencies.
     * @throws IOException 
     */
    public HashMap<Character, Integer> readFrequenciesFromFile(BinaryInput bi) throws IOException {
        HashMap<Character, Integer> frequencies = new HashMap<>();   
        while (true) {
            char c = bi.readChar();
            if (c == '$') {
                break;
            }
            int freq = bi.readInt(32);
            frequencies.put(c, freq);
        }
        return frequencies;
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
        String newFile = "";
        for (int i = 0; i < originalFile.length() - 7; i++) {
            newFile += originalFile.charAt(i);
        }
        newFile += "h";     
        FileOutputStream fos = new FileOutputStream(newFile);
        return fos;
    }
    
}
