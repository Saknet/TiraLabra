
package IO;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class used for reading binary data from the input stream.
 */
public final class BinaryInput {
    
    /**
     * FileOutputStream for output.
     */
    private FileInputStream fis;      // the input stream
    /**
     * 8-bit buffer for writing out bits.
     */
    private int buffer;
    
    /**
     * Number of bits left in buffer
    */
    private int bitsInBuffer;

   /**
     * Create a binary input stream from an InputStream.
     * @param fis
     * @throws java.io.IOException
     */
    public BinaryInput(FileInputStream fis) throws IOException {
        this.fis = fis;
        fillBuffer();
    }

    /**
     * Fills the buffer.
     * 
     * @throws IOException 
     */
    public void fillBuffer() throws IOException {
        buffer = fis.read(); 
        bitsInBuffer = 8;
    }

   /**
     * Reads the next bit of data from the input stream.
     * @return boolean
     * @throws java.io.IOException
     */
    public boolean readBit() throws IOException {
        bitsInBuffer--;
        boolean bit = ((buffer >> bitsInBuffer) & 1) == 1;
        if (bitsInBuffer == 0) {
            fillBuffer();
        }
        return bit;
    }

   /**
     * Method that returns char by reading the next 8 bits from the input stream.
     * @return char
     * @throws java.io.IOException
     */
    public char readChar() throws IOException {
        int x = buffer;
        x <<= (8-bitsInBuffer);
        int old = bitsInBuffer;
        fillBuffer();
        bitsInBuffer = old;
        x |= (buffer >>> bitsInBuffer);
        return (char) (x & 0xff);
    }

   /**
     * Read the next n bits from the input stream and returns it as n-bit int.
     * 
     * @param n Integer
     * @return int Integer value of next n-bits.
     * @throws java.io.IOException
     */
    public int readInt(int n) throws IOException {
        int x = 0;
        for (int i = 0; i < n; i++) {
            x <<= 1;
            boolean bit = readBit();
            if (bit) {
                x |= 1;
            }
        }
        return x;
    }
}
