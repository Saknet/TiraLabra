
package LZ77;

import IO.BinaryInput;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
     * @throws java.io.FileNotFoundException
     * @throws IOException 
     */
    public void run(String originalFile) throws FileNotFoundException, IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(originalFile)));
        FileOutputStream fos = createOutput(originalFile);
        decompress(dis, fos);
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
        
        return new FileOutputStream(newFile + "7");
    }
    
    /**
     * Method that decompresses the input file and writes decompressed data into output.
     * 
     * @param dis DataInputStream, the input stream.
     * @param fos
     * @throws IOException 
     */
    public void decompress(DataInputStream dis, FileOutputStream fos) throws IOException {
        BinaryInput bi = new BinaryInput(dis);
        String s = "";
        while (dis.available() > 0) {
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
     * @param fos
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
