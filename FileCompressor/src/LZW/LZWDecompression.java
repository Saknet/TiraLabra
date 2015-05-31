
package LZW;

import IO.BinaryInput;
import datastructures.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;;
import java.util.HashMap;

public class LZWDecompression {
    
    /** The main method of LZWDecompression. Starts all other methods needed for data decompression.
     * 
     * @param originalFile String, the original file which will be decompressed.
     * @throws java.io.FileNotFoundException
     * @throws IOException 
     */
    public void run(String originalFile) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(originalFile);
        ArrayList<Integer> result = readFile(fis);
        FileOutputStream fos = createOutput(originalFile);
        HashMap<Integer, String> dictionary = initializeDictionary();
        decompress(fos, dictionary, result);
    }
    
    /**
     * The main method of decompressing LZW algorithm. Decompresses the codes and uses
     * writeFile method to write output file.
     * 
     * @param fos FileOutputStream, used for output.
     * @param dictionary HashMap<Integer, String>, used for decompressing the file. 
     * @param codes ArrayList<Integer>, used for decompressing the file. 
     * @throws IOException 
     */
    public void decompress(FileOutputStream fos, HashMap<Integer, String> dictionary, ArrayList<Integer> codes) throws IOException {
        String w = "" + (char)(int)codes.get(0);
        writeFile(w, fos);
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
            writeFile(entry, fos);
            dictionary.put(size++, w + entry.charAt(0));
            w = entry;
        }
        fos.close();
    }
    
    /**
     * Method that writes the output file.
     * 
     * @param entry String, used for generating the output.
     * @param fos FilOutputStream, used for output.
     * @throws IOException 
     */
    public void writeFile(String entry, FileOutputStream fos) throws IOException {
        for (int i = 0; i < entry.length(); i++) {
            fos.write((int) entry.charAt(i));   
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
        newFile += "d";     
        FileOutputStream fos = new FileOutputStream(newFile);
        return fos;
    }
    
    /**
     * Initializes dictionary used by the algorithm with all 1-character strings.
     * 
     * @return Initialized HashMap<Integer, String> dictionary. 
     */
    public HashMap<Integer, String> initializeDictionary() {
        HashMap<Integer, String>  dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char)i);
        }
        return dictionary;
    }
    
    /**
     * Reads chars from input stream and converts them to integer codes which get added into list for decompressing.
     * 
     * @param fis FileInputStream, the input stream used for reading the compressed data.
     * @return codes ArrayList<Integer>, used for decompressing the file. 
     * @throws IOException 
     */
    
    public ArrayList<Integer> readFile(FileInputStream fis) throws IOException {
        ArrayList<Integer> codes = new ArrayList<>();
        BinaryInput bi = new BinaryInput(fis);
        while (fis.available() > 0) {
            int code = bi.readInt(12);
            codes.add(code);
        }
        return codes;
    }    
}
