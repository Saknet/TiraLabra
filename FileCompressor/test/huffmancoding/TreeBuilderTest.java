
package huffmancoding;

import datastructures.HashMap;
import datastructures.PriorityQueue;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TreeBuilderTest {
    HuffmanCompression hC = new HuffmanCompression();
    TreeBuilder treeBuilder = new TreeBuilder();
    
    public TreeBuilderTest() {
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
    public void addSymbolsToPQ() throws IOException {
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        PriorityQueue pq = treeBuilder.addSymbolsToPQ(freq);
        Assert.assertEquals('£', pq.poll().getSymbol());
        pq.poll();
        Assert.assertEquals('/', pq.poll().getSymbol());
        Assert.assertEquals(1, pq.poll().getFrequency());
    }

    @Test
    public void buildTreeTest() throws IOException {
        HashMap<Character, Integer> freq = hC.addFrequencies("testfiles/huffmanfilefreqtest.txt");
        PriorityQueue pq = treeBuilder.addSymbolsToPQ(freq);
        Node root = treeBuilder.buildTree(pq);
        Assert.assertEquals(1329, root.getFrequency());
        Assert.assertEquals(537, root.getLeft().getFrequency());
        Assert.assertEquals(792, root.getRight().getFrequency());
    }
}
