
package IO;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class used for writing binary data into the output stream.
 */
public class BinaryOutput {
    
    /**
     * FileOutputStream for output.
     */
    private FileOutputStream fos;
    
    /**
     * 8-bit buffer for writing out bits.
     */
    private int buffer;
    
    /**
     * Number of bits left in buffer
    */
    private int bitsInBuffer;
    
    /**
     * Creates binary output stream from OutputStream.
     * 
     * @param out FileOutputStream, the OutputStream.
     */
    public BinaryOutput(FileOutputStream out) {
        this.fos = out;
    }
    
    /**
     * Writes n-bit integer to the output stream.
     * 
     * @param code being written as bits.
     * @param n Integer
     * @throws IOException 
     */
    public void write(int code, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            boolean bit = ((code >>> (n - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }
    
    /**
     * Method, that writes a bit to the output stream. First adds bit to buffer, 
     * if there is 8 bits in buffer writes them out as a byte.
     * 
     * @param bit boolean bit that is being written.
     * @throws IOException 
     */
    
    public void writeBit(boolean bit) throws IOException {
        buffer <<= 1;
        if (bit) {
            buffer |= 1;
        }
        bitsInBuffer++;
        if (bitsInBuffer == 8) {
            clearBuffer();
        }
    }
    /**
     * Writes the bits which are left in buffer into output stream.
     * 
     * @throws IOException 
     */
    public void clearBuffer() throws IOException {
        if (bitsInBuffer == 0) {
            return;
        }
        if (bitsInBuffer > 0) {
            buffer <<= (8 - bitsInBuffer);
        }
        fos.write(buffer);
        bitsInBuffer = 0;
        
    }
    
   /**
     * Clears buffer, flushes and closes the output stream. 
     * 
     * @throws java.io.IOException
     */
    public void close() throws IOException {
        clearBuffer();
        fos.flush();
        fos.close();
    }     
    
    /**
     * Used for writing 8-bit byte into output stream one bit at a time.
     * @param x Integer value of the byte from 0 to 256.
     * @throws IOException 
     */
    public void writeByte(int x) throws IOException {
        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }   
}
