
package LZ77;

import IO.BinaryOutput;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class that compresses the file with LZ77 algorithm.
 */
public class LZ77Compression {
    
    /**
     * Integer that points to the current location of file that is being compressed.
     */
    private int currentLocation = 0;
    
    /**
     * Used to start methods needed for file compression.
     * 
     * @param originalFile the file that will be compressed.
     * @throws IOException 
     */
    public void run(String originalFile) throws IOException { 
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(createOutput(originalFile)));
        compress(originalFile, dos, 256);
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
        String newFile = originalFile + ".LZ77";     
        FileOutputStream fos = new FileOutputStream(newFile);
        return fos;
    }
    
    /**
     * The main main method for data compression.
     * 
     * @param originalFile String, the file that will be compressed.
     * @param dos DataOutputStream. file output stream for the output.
     * @param bufferSize Integer, size of the buffer.
     * @throws IOException 
     */
    public void compress(String originalFile, DataOutputStream dos, int bufferSize) throws IOException {
        String data = readFile(originalFile);
        BinaryOutput bo = new BinaryOutput(dos);
        char[] dataArray = data.toCharArray();  
        char[] buffer = fillBuffer(dataArray, bufferSize);
        while (true) {
            if (buffer.length < bufferSize) {
                break;
            }
            Match match = findMatch(buffer);
            writeUntilMatch(buffer, match, bo, bufferSize);
            buffer = fillBuffer(dataArray, bufferSize);
        }
        Match match = findMatch(buffer);
        writeBlock(buffer, match, bo);
        bo.close();
    }
    
    /**
     * Writes output file until the match if there is a match. else writes all chars from buffer into file.
     * Also updates currentLocation in compression file.
     * 
     * @param buffer char[], contains the chars currently in buffer.
     * @param match Match, best match object.
     * @param bo BinaryOutput, binary output stream used for writing the output.
     * @param bufferSize Integer, fixed size of the buffer.
     * @throws IOException 
     */
    public void writeUntilMatch(char[] buffer, Match match, BinaryOutput bo, int bufferSize) throws IOException {
        if (match.getStartPointer() > 0) {
            writeCharsFromBuffer(buffer, match.getStartPointer(), bo);
            writeMatch(match, bo);          
            currentLocation = currentLocation + match.getStartPointer() + match.getLength();
        } else {
            writeCharsFromBuffer(buffer, buffer.length, bo);
            currentLocation = currentLocation + bufferSize; 
        }        
    }
    
    /**
     * Writes fixed amount of chars from buffer into file.
     * 
     * @param buffer char[], the buffer that contains chars that will be written into file.
     * @param end Integer, the spot where writing stops.
     * @param bo BinaryOutput, binary output stream used for writing the output.
     * @throws IOException 
     */
    public void writeCharsFromBuffer(char[] buffer, int end, BinaryOutput bo) throws IOException {
        for (int i = 0; i < end; i++) {
                bo.writeByte(buffer[i]);            
        }        
    }
    
    /**
     * Writes the match data into file, $ symbol is used to mark the start of match.
     * @param match Match, best match object.
     * @param bo BinaryOutput, output stream used for writing the output file.
     * @throws IOException 
     */
    public void writeMatch(Match match, BinaryOutput bo) throws IOException {
        bo.writeByte('$');
        bo.write(match.getDistance(), 8);
        bo.write(match.getLength(), 8);       
    }
    
    /**
     * Writes full buffer sized length of data into output file.
     * @param buffer char[], the buffer of chars that will be written into file.
     * @param match Match, the best match object.
     * @param bo BinaryOutput, output stream used for writing the output file.
     * @throws IOException 
     */
    public void writeBlock(char[] buffer, Match match, BinaryOutput bo) throws IOException {
        for (int i = 0; i < buffer.length; i++) {
            if (i == match.getStartPointer()) {
                writeMatch(match, bo);
                i = i + match.getLength() - 1;
            } else {
                bo.writeByte(buffer[i]);
            }
        }
    }
    
    /**
     * Method that sets the attributes for best match object.
     * @param l Integer, length of the match.
     * @param j starting location of the 2nd String(and the match).
     * @param i starting location of the 1st String.
     * @return bestMatch, match object that is the best match.
     */
    public Match setBestMatch(int l, int j, int i) {
        Match bestMatch = new Match();
        bestMatch.setLength(l);
        bestMatch.setDistance(j - i);
        bestMatch.setStartPointer(j);
        return bestMatch;
    }
    
    /**
     * Method that is used for finding the length of a match.
     * @param current String, the first string that should match the 2nd string.
     * @param other String, the second string that should match the 1st string.
     * @param buffer char[], contains the chars in the buffer.
     * @param i 1st string's starting location in the buffer.
     * @param j 2nd string's starting location in the buffer.
     * @return l Integer, the length of the match.
     */
    public int findMatchLength(String current, String other, char[] buffer, int i, int j) {
        int l = 1;
        while (other.equals(current) && (j + l) < buffer.length ) {
            other = other + buffer[j + l];
            current = current + buffer[i + l];
            l++;
        } 
        return l - 1;
    }
    
    /**
     * Method used for find the best match.
     * @param buffer char[], char array from where the best match is being searched at.
     * @return Match, the bestMatch object.
     */
    public Match findMatch(char[] buffer) {
        Match bestMatch = new Match();
        for (int i = 0; i < buffer.length; i++) {
            char c = buffer[i];
            for (int j = i + 1; j < buffer.length; j++) {
                if (buffer[j] == c) {
                    String current = "" + c;
                    String other = ""+ buffer[j];
                    int l = findMatchLength(current, other, buffer, i, j);
                    if (l > 2 && l > bestMatch.getLength()) {
                        bestMatch = setBestMatch(l, j, i);
                    }
                    
                }
            }
        }
        return bestMatch;
    }
    
    /**
     * Reads all chars from original file and writes them into String.
     * 
     * @param originalFile String, the original file.
     * @return String that contains all chars from original file.
     * @throws IOException 
     */
    public String readFile(String originalFile) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(originalFile)));
        String s = "";
        while (dis.available() > 0) {
            char c = (char) dis.read();
            s = s + c;
        }
        s = s + "\n";
        return s;
    }
    
    /**
     * Fills buffer with chars. If there less chars left in file than bufferSize, 
     * fills buffer with leftover chars.
     * 
     * @param dataArray char Array that contains all chars from original file.
     * @param bufferSize Integer, prefixed size of the buffer.
     * @return buffer, char Array that represents the buffer.
     */
    public char[] fillBuffer(char[] dataArray, int bufferSize) {
        char[] buffer;
        if (dataArray.length - currentLocation > bufferSize) {
            buffer = new char[bufferSize];
        } else {
            buffer = new char[dataArray.length - currentLocation];
        }
        System.arraycopy(dataArray, currentLocation, buffer, 0, buffer.length);
        return buffer;
    }
    
}
