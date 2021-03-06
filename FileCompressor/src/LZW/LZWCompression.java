
package LZW;

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
 * Class that compresses the file with LZW algorithm.
 */
public class LZWCompression {
    
    /** The main method of LZWcompression. Starts all other methods needed for data compression.
     * 
     * @param originalFile the file that will be compressed.
     * @throws IOException 
     */
    public void run(String originalFile) throws IOException { 
        HashMap<String, Integer> dictionary = initializeDictionary();
        ArrayList<Integer> codes = compress(dictionary, originalFile);
        writeFile(codes, originalFile);
        
    }
    
    /**
     * Method that uses original file name to make name for new file and returns 
     * FileOutputStream for the new file.
     * 
     * @param originalFile String the original file, name of the file is used to make name for new file.
     * @return fos FileOutputStream used for output.
     * @throws FileNotFoundException 
     */   
    public FileOutputStream createOutput(String originalFile) throws FileNotFoundException {          
        return new FileOutputStream(originalFile + ".LZW");
    }
    
    /**
     * Method that uses BinaryOutput class to write bits into file.
     * @param codes ArrayList<Integer> list of codes used for writing the compressed file.
     * @param originalFile String, used to create FileOutputStream.
     * @throws IOException 
     */
    public void writeFile(ArrayList<Integer> codes, String originalFile) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(createOutput(originalFile)));
        BinaryOutput bo = new BinaryOutput(dos);
        for (int i = 0; i < codes.size(); i++) {
            bo.write(codes.get(i), 24);
        }
        // just here to make testing easier
        dos.write('$');
        bo.close();
        
    }
        
    /**
     * Method that generates codes for compression. This the core part of LZW algorithm.
     * 
     * @param dictionary HashMap<String, Integer> which algorithm uses to make codes for compression.
     * @param originalFile String, the original file.
     * @return codes ArrayList<Integer> list of codes used compression.
     * @throws IOException 
     */
    public ArrayList<Integer> compress(HashMap<String, Integer> dictionary, String originalFile) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(originalFile)));
        ArrayList<Integer> codes = new ArrayList<>();
        String w = "";
        char c;
        int size = 256;
        while (dis.available() > 0) {
            c = (char) dis.read();
            String wc = w + c;
            if (dictionary.containsKey(wc)) {
                w = wc;
            } else {
                codes.add(dictionary.get(w));
                dictionary.put(wc, size++);
                w = "" + c;
            }
        }
        
        if (!w.equals("")) {
            codes.add(dictionary.get(w));            
        }
        dis.close();
        
        return codes;
    }
    
    /**
     * Initializes dictionary used by the algorithm.
     * 
     * @return Initialized HashMap<String, Integer> dictionary.
     */
    public HashMap<String, Integer> initializeDictionary() {
        HashMap<String, Integer> dictionary = new HashMap<>(65536);
        for (int i = 0; i < 256; i++) {
            dictionary.put(""+ (char) (i), i);
        }
        return dictionary;
    }
}
