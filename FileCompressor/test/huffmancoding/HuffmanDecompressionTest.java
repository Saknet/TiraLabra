
package huffmancoding;

import IO.BinaryInput;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanDecompressionTest {
    HuffmanDecompression hd = new HuffmanDecompression();
    HuffmanCompression hc = new HuffmanCompression();
    
    public HuffmanDecompressionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    //tämä testi riittää antamaan 100% testikattavuuden sekä HuffmanCompression että HuffmanDecompression luokalle.
    //mutta tehdään nyt muutama testi lisää että ohjaajalla ei olisi tylsää :)
    @Test
    public void runTest() throws IOException {
        FileInputStream fisa = new FileInputStream("testfiles/huffmanfilefreqtest.txt");
        String a = "";
        while (fisa.available() > 0) {
            char c = (char) fisa.read();
            a = a + c;
        }
        fisa.close();
        
        hc.run("testfiles/huffmanfilefreqtest.txt");
        hd.run("testfiles/huffmanfilefreqtest.txt.huffman");
        
        FileInputStream fisb = new FileInputStream("testfiles/huffmanfilefreqtest.txt.h");
        String b = "";
        while (fisb.available() > 0) {
            char c = (char) fisb.read();
            b = b + c;
        }
        fisb.close();
        
        Assert.assertEquals(a, b);
    }
    
    @Test
    public void decompressAndReadSymbolTest() throws IOException {
        hc.run("testfiles/huffmanfilefreqtest.txt"); 
        BinaryInput bi = new BinaryInput(new FileInputStream("testfiles/huffmanfilefreqtest.txt.huffman"));
        Node root = new TreeBuilder().makeTree(hd.readFrequenciesFromFile(bi));
        hd.decompress(root, bi, hd.createOutput("testfiles/huffmanfilefreqtest.,txt.huffman"));
        
        FileInputStream fisb = new FileInputStream("testfiles/huffmanfilefreqtest.txt.h");
        boolean wrongSymbol = true;
        while (fisb.available() > 0) {
            char c = (char) fisb.read();
            if (c == '%' || c == '£') {
                wrongSymbol = false;
            }
        }
        
        Assert.assertTrue(wrongSymbol);
    }
    
    @Test
    public void readFrequenciesFromFileTest() throws IOException {
        hc.run("testfiles/readFrequenciesFromFile.txt");
        BinaryInput bi = new BinaryInput(new FileInputStream("testfiles/readFrequenciesFromFile.txt.huffman"));  
        HashMap<Character, Integer> freq = hd.readFrequenciesFromFile(bi);
        int a = freq.get('a');
        int b = freq.get('b');
        int c = freq.get('c');
        int d = freq.get('d');
        Assert.assertEquals(3, a);
        Assert.assertEquals(2, b);
        Assert.assertEquals(1, c);
        Assert.assertEquals(4, d);
    }
    
    @Test
    public void createOutputTest() throws FileNotFoundException, IOException {
        FileOutputStream fos = hd.createOutput("testfiles/huffmandcreatetest.txt.huffman");
        String s = "jeesus ei pelasta";
        fos.write(s.getBytes());
        fos.close();
        FileInputStream fis = new FileInputStream("testfiles/huffmandcreatetest.txt.h");
        s = "";
        while (fis.available() > 0) {
            char c = (char) fis.read();
            s = s + c;
        }
        fis.close();
        
        org.junit.Assert.assertEquals("jeesus ei pelasta", s);
    }
}
