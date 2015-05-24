
package IO;

import java.io.FileOutputStream;
import java.io.IOException;

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
     * @param out FileOutputStream, the OutputStream.
     */
    public BinaryOutput(FileOutputStream out) {
        this.fos = out;
    }
    
    /**
     * Writes 12-bit integer to the output stream.
     * @param code being written as bits.
     * @throws IOException 
     */
    public void write(int code) throws IOException {
        for (int i = 0; i < 12; i++) {
            boolean bit = ((code >>> (12 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }
    
    /**
     * Method, that writes a bit to the output stream. First adds bit to buffer, 
     * if there is 8 bits in buffer writes them out as a byte.
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
     * @throws java.io.IOException
     */
    public void close() throws IOException {
        clearBuffer();
        fos.flush();
        fos.close();
    }     
}
