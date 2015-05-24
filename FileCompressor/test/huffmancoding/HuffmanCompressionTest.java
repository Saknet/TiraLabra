
package huffmancoding;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HuffmanCompressionTest {
    HuffmanCompression hC = new HuffmanCompression();
    
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
        FileOutputStream fos = hC.createOutput("testfiles/huffmanfilecreatetest.txt");
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
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        int as = freq.get('a');
        Assert.assertEquals(78, as);
        int empty = freq.get(' ');
        Assert.assertEquals(198, empty);
        Assert.assertNull(freq.get('ä'));
    } 
    
    @Test
    public void addSymbolsToPQ() throws IOException {
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        PriorityQueue<Node> pq = hC.addSymbolsToPQ(freq);
        Assert.assertEquals('/', pq.poll().getSymbol());
        pq.poll();
        Assert.assertEquals('B', pq.poll().getSymbol());
        Assert.assertEquals(1, pq.poll().getFrequency());
    }

    @Test
    public void buildTreeTest() throws IOException {
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        PriorityQueue<Node> pq = hC.addSymbolsToPQ(freq);
        Node root = hC.buildTree(pq);
        Assert.assertEquals(1329, root.getFrequency());
        Assert.assertEquals(537, root.getLeft().getFrequency());
        Assert.assertEquals(792, root.getRight().getFrequency());
    }
    
    @Test
    public void readTreeTest() throws IOException {
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        PriorityQueue<Node> pq = hC.addSymbolsToPQ(freq);
        Node root = hC.buildTree(pq);  
        hC.readTree(root, "");
        Assert.assertEquals("1111", hC.getcharToCode().get('e'));
        Assert.assertNull(hC.getcharToCode().get('K'));
        Assert.assertEquals("1011010", hC.getcharToCode().get('y'));
    }
    
    @Test
    public void writeTreeToFileTest() throws IOException {
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        PriorityQueue<Node> pq = hC.addSymbolsToPQ(freq);
        Node root = hC.buildTree(pq);
        FileOutputStream fos = hC.createOutput("testfiles/huffmantreetoFileTest.txt");
        hC.writeTreeToFile(root, fos);
        fos.close();
        
        FileInputStream fis = new FileInputStream("testfiles/huffmantreetoFileTest.txt.huffman");
        String s = "";
        while (fis.available() > 0) {
            char c = (char) fis.read();
            s = s + c;
        }
        fis.close();
        Assert.assertEquals("C7J8,8\"2:2k2S2p31l63\n" +
        "32D4g4w4M4/1L1B1z1*4R8i65f33A8P8b9T9o68s70a78c38I10W1Z1F3v5m20r42=48t96."
                + "12-6N3O3y12u13h52 198n55)3(3U3E4j14d31e116", s);
    }
    
    @Test
    public void runTest() throws IOException {
        hC.run("testfiles/huffmanfilefreqtest.txt");
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


}
