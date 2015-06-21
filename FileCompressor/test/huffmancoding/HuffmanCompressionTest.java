
package huffmancoding;

import IO.BinaryInput;
import IO.BinaryOutput;
import datastructures.HashMap;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HuffmanCompressionTest {
    HuffmanCompression hc = new HuffmanCompression();
    TreeBuilder treeBuilder = new TreeBuilder();
    
    public HuffmanCompressionTest() {
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
    
    @Test
    public void createOutputTest() throws FileNotFoundException, IOException {
        FileOutputStream fos = hc.createOutput("testfiles/huffmanfilecreatetest.txt");
        String s = "jeesus pelastaa";
        fos.write(s.getBytes());
        fos.close();
        FileInputStream fis = new FileInputStream("testfiles/huffmanfilecreatetest.txt.huffman");
        s = "";
        while (fis.available() > 0) {
            char c = (char) fis.read();
            s = s + c;
        }
        fis.close();
        
        Assert.assertEquals("jeesus pelastaa", s);
    }
    
    @Test
    public void addFrequenciesTest() throws IOException {
        HashMap<Character, Integer> freq = hc.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        int as = freq.get('a');
        Assert.assertEquals(78, as);
        int empty = freq.get(' ');
        Assert.assertEquals(198, empty);
        Assert.assertNull(freq.get('ä'));
    } 
    
    @Test
    public void readTreeTest() throws IOException {
        HashMap<Character, Integer> freq = hc.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        Node root = treeBuilder.makeTree(freq);  
        hc.readTree(root, "");
        Assert.assertEquals("1111", hc.getcharToCode().get('e'));
        Assert.assertNull(hc.getcharToCode().get('K'));
        Assert.assertEquals("1011001", hc.getcharToCode().get('y'));
    }
    
    @Test
    public void writeFrequenciesToFileTest() throws IOException {
        HashMap<Character, Integer> freq = hc.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        Node root = treeBuilder.makeTree(freq);  
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(hc.createOutput("testfiles/huffmantreetoFileTest.txt")));

        BinaryOutput bo = new BinaryOutput(dos);
        hc.writeFrequenciesToFile(freq, bo);
        dos.close();
        
        FileInputStream fis = new FileInputStream("testfiles/huffmantreetoFileTest.txt.huffman");
        char c = 'a';
        while (fis.available() > 0) {
            c = (char) fis.read();
        }
        fis.close();
        Assert.assertEquals('$', c);
    }
    
    @Test
    public void runAndCompressTest() throws IOException {
        hc.run("testfiles/huffmanfilefreqtest.txt");
        FileInputStream fis = new FileInputStream("testfiles/huffmanfilefreqtest.txt.huffman");
        boolean pound = false;
        while (fis.available() > 0) {
            char c = (char) fis.read();
            if (c == '£') {
                pound = true;
            }
        }
        fis.close();
        
        Assert.assertTrue(pound);
    }
    
    @Test
    public void writeFileTest() throws IOException {
        HashMap<Character, Integer> freq = hc.addFrequencies("testfiles/readFrequenciesFromFile.txt");
        hc.readTree(new TreeBuilder().makeTree(freq), "");
        hc.writeFile(new BinaryOutput(new DataOutputStream(new BufferedOutputStream(hc.createOutput("testfiles/writeFileTest.txt")))), 'c');
        BinaryInput bi = new BinaryInput(new DataInputStream(new BufferedInputStream(new FileInputStream("testfiles/writeFileTest.txt"))));
        boolean bit = bi.readBit();
        Assert.assertEquals(bit, true);
    }
}
