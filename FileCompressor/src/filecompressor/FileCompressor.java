
package filecompressor;

import LZW.LZWCompression;
import huffmancoding.HuffmanCompression;
import java.io.IOException;

public class FileCompressor {

    public static void main(String[] args) throws IOException {
        HuffmanCompression hc = new HuffmanCompression();
        LZWCompression lzwc = new LZWCompression();
        lzwc.run(args[0]);
    }
}
