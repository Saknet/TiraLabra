
package filecompressor;

import LZW.LZWCompression;
import LZW.LZWDecompression;
import huffmancoding.HuffmanCompression;
import huffmancoding.HuffmanDecompression;
import java.io.IOException;

public class FileCompressor {

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("wrong command");
            System.exit(0);
        }
        final long startTime = System.currentTimeMillis();
        if (args[1].equals("c") && args[2].equals("l")) {
            LZWCompression lzwc = new LZWCompression();    
            lzwc.run(args[0]);
        }
        if (args[1].equals("c") && args[2].equals("h")) {
            HuffmanCompression hc = new HuffmanCompression(); 
            hc.run(args[0]);
        }
        if (args[1].equals("d") && args[2].equals("l")) {
            LZWDecompression lzwd = new LZWDecompression();    
            lzwd.run(args[0]);
        }
        if (args[1].equals("d") && args[2].equals("h")) {
            HuffmanDecompression hd = new HuffmanDecompression(); 
            hd.run(args[0]);
        }        
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time was:" + (endTime - startTime));
    }
}
