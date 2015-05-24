
package LZW;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LZWDecompression {
    
    /** The main method of LZWDecompression. Starts all other methods needed for data decompression.
     * @param originalFile String, the original file which will be decompressed.
     * @throws IOException 
     */
//    public void run(String originalFile) throws FileNotFoundException, IOException {
//        FileInputStream fis = new FileInputStream(originalFile);
 //       ArrayList<Integer> result = readFile(fis);
//        FileOutputStream fos = createOutput(originalFile);
//        HashMap<Integer, String> dictionary = initializeDictionary();
 //       decompress(fos, dictionary, result);
 //   }
    
    /**
     * The main dcompressing LZW algorithm. Wont work before method that makes Compressed ArrayList is working.
     * @param fos FileOutputStream, used for output.
     * @param dictionary HashMap<Integer, String>, used for decompressing the file. 
     * @param codes ArrayList<Integer>, used for decompressing the file. 
     * @throws IOException 
     */
    public void decompress(FileOutputStream fos, HashMap<Integer, String> dictionary, ArrayList<Integer> codes) throws IOException {
        String w = "" + (char)(int)codes.remove(0);
        int size = 256;
        for (int k : codes) {
            String entry;
            if (dictionary.containsKey(k)) {
                entry = dictionary.get(k);
            }
            else if (k == size) {
                entry = w + w.charAt(0);
            } else {
                throw new IllegalArgumentException("Bad compressd k" + k);
            }
            writeFile(entry, fos);
            dictionary.put(size++, w + entry.charAt(0));
            w = entry;
        }
        fos.close();
    }
    
    /**
     * Writes output file, not fully ready yet.
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
     * Creates name for output file.
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
     * Very much not ready yet!
     * @param fis
     * @return
     * @throws IOException 
     */
 //   public ArrayList<Integer> readFile(FileInputStream fis) throws IOException {
 //       ArrayList<Integer> result = new ArrayList<>();
  //      BinaryInput bi = new BinaryInput(fis);
  //      while (fis.available() > 0) {
    //       int code = bi.readInt();
      //      result.add(code);
  //      }
    //    bi.close();
  //      return result;
  //  }    
}
