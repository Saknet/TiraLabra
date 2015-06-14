
package LZ77;

import IO.BinaryInput;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class that decompresses the file with LZ77 algorithm.
 */
public class LZ77Decompression {
    
    /**
     * Used to start methods needed for file decompression.
     * 
     * @param originalFile the file that will be decompressed.
     * @throws IOException 
     */
    public void run(String originalFile) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(originalFile);
        FileOutputStream fos = createOutput(originalFile);
        decompress(fis, fos);
    }
    
    /**
     * Method that uses original file name to make name for file and returns 
     * FileOutputStream for the output file.
     * 
     * @param originalFile String the original file, name of the file is used to make name for new file.
     * @return fos FileOutputStream used for output.
     * @throws FileNotFoundException 
     */ 
    public FileOutputStream createOutput(String originalFile) throws FileNotFoundException {
        String newFile = "";
        for (int i = 0; i < originalFile.length() - 4; i++) {
            newFile += originalFile.charAt(i);
        }
        newFile += "7";     
        FileOutputStream fos = new FileOutputStream(newFile);
        return fos;
    }
    
    /**
     * Method that decompresses the input file and writes decompressed data into output.
     * 
     * @param fis FileInputStream, the input stream.
     * @param fos FileOutputStream, the output stream.
     * @throws IOException 
     */
    public void decompress(FileInputStream fis, FileOutputStream fos) throws IOException {
        BinaryInput bi = new BinaryInput(fis);
        String s = "";
        while (fis.available() > 0) {
            char c = bi.readChar();
            if (c == '$') {
                int distance = bi.readInt(8);
                int length = bi.readInt(8);
                s = writeMatch(s, distance, length, fos);
                
            } else {
                s = s + c;
                fos.write(c);
            }
        }
    }
    
    /**
     * Finds the match and writes it into output.
     * 
     * @param s String, string that contains all characters that have been decompressed from input.
     * @param distance Integer, the distance of a match from current position.
     * @param length Integer, the length of the match.
     * @param fos FileOutputStream, used for writing output file.
     * @return S String, the updated string.
     * @throws IOException 
     */
    public String writeMatch(String s, int distance, int length, FileOutputStream fos) throws IOException {
        int start = s.length() - distance;
        int end = s.length() - distance + length;
        for (int i = start; i < end; i++) {
            fos.write(s.charAt(i));
            s = s + s.charAt(i);
        }
        return s;
    }
}
