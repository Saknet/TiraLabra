
package filecompressor;

import LZW.LZWCompression;
import LZW.LZWDecompression;
import huffmancoding.HuffmanCompression;
import huffmancoding.HuffmanDecompression;
import java.io.IOException;

public class FileCompressor {

    public static void main(String[] args) throws IOException {
        HuffmanCompression hc = new HuffmanCompression();
        HuffmanDecompression hd = new HuffmanDecompression();
        LZWCompression lzwc = new LZWCompression();
        LZWDecompression lzwd = new LZWDecompression();
        hd.run(args[0]);
    }
}
