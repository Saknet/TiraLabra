
package huffmancoding;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanCompressorTest {
    HashMap<Character, Integer> frequencies = new HashMap<>();
    HuffmanCompressor hC = new HuffmanCompressor();
    
    public HuffmanCompressorTest() {
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
    public void buildTree() {
        String text = "aabbc";
        hC.addFrequencies(text);
        hC.addSymbolsToPQ();
        Node root = hC.buildTree();
        assertEquals(root.getFrequency(), 5);
        assertEquals(root.getLeft().getFrequency(), 2);
        assertEquals(root.getRight().getLeft().getSymbol(), 'c');
    }


}
