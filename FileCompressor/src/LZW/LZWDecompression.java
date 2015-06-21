
package LZW;

import IO.BinaryInput;
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
 * Class that decompresses the file with LZW algorithm.
 */
public class LZWDecompression {
    
    /** The main method of LZWDecompression. Starts all other methods needed for data decompression.
     * 
     * @param originalFile String, the original file which will be decompressed.
     * @throws java.io.FileNotFoundException
     * @throws IOException 
     */
    public void run(String originalFile) throws FileNotFoundException, IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(originalFile)));
        ArrayList<Integer> result = readFile(dis);
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(createOutput(originalFile)));
        HashMap<Integer, String> dictionary = initializeDictionary();
        decompress(dos, dictionary, result);
    }
    
    /**
     * The main method of decompressing LZW algorithm. Decompresses the codes and uses
     * writeFile method to write output file.
     * S
     * @param dos DataOutputStream, used for output.
     * @param dictionary HashMap<Integer, String>, used for decompressing the file. 
     * @param codes ArrayList<Integer>, used for decompressing the file. 
     * @throws IOException 
     */
    public void decompress(DataOutputStream dos, HashMap<Integer, String> dictionary, ArrayList<Integer> codes) throws IOException {
        String w = "" + (char)(int)codes.get(0);
        writeFile(w, dos);
        int size = 256;
        for (int i = 1; i < codes.size(); i++) {
            int code = codes.get(i);
            String entry = "";
            if (dictionary.containsKey(code)) {
                entry = dictionary.get(code);
            }
            else if (code == size) {
                entry = w + w.charAt(0);
            } 
            writeFile(entry, dos);
            dictionary.put(size++, w + entry.charAt(0));
            w = entry;
        }
        dos.close();
    }
    
    /**
     * Method that writes the output file.
     * 
     * @param entry String, used for generating the output.
     * @param dos FilOutputStream, used for output.
     * @throws IOException 
     */
    public void writeFile(String entry, DataOutputStream dos) throws IOException {
        for (int i = 0; i < entry.length(); i++) {
            dos.write((int) entry.charAt(i));   
        }    
    }
    
    /**
     * Method that, creates name for output file and file output stream for it.
     * 
     * @param originalFile String, the original file.
     * @return newFile, String, name of the new file.
     * @throws FileNotFoundException 
     */
    public FileOutputStream createOutput(String originalFile) throws FileNotFoundException {
        String newFile = "";
        for (int i = 0; i < originalFile.length() - 3; i++) {
            newFile += originalFile.charAt(i);
        }
        
        return new FileOutputStream(newFile + "d");
    }
    
    /**
     * Initializes dictionary used by the algorithm with all 1-character strings.
     * 
     * @return Initialized HashMap<Integer, String> dictionary. 
     */
    public HashMap<Integer, String> initializeDictionary() {
        HashMap<Integer, String>  dictionary = new HashMap<>(65536);
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char)i);
        }
        return dictionary;
    }
    
    /**
     * Reads chars from input stream and converts them to integer codes which get added into list for decompressing.
     * 
     * @param dis DataInputStream, the input stream used for reading the compressed data.
     * @return codes ArrayList<Integer>, used for decompressing the file. 
     * @throws IOException 
     */
    
    public ArrayList<Integer> readFile(DataInputStream dis) throws IOException {
        ArrayList<Integer> codes = new ArrayList<>();
        BinaryInput bi = new BinaryInput(dis);
        while (dis.available() > 0) {
            int code = bi.readInt(24);
            codes.add(code);
        }
        dis.close();
        return codes;
    }    
}
