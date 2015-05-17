package huffmancoding;


import huffmancoding.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {
    Node leaf;
    Node notLeaf;
    
    public NodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        leaf = new Node('a', 3);
        notLeaf = new Node(3, leaf, null);
    }
    
    @Test
    public void leafNodeTest() {
        assertEquals(leaf.getFrequency(), 3);
        assertEquals(leaf.getSymbol(), 'a');
        assertEquals(leaf.getLeft(), null);
        assertEquals(leaf.getRight(), null);
    }
    
    @Test
    public void notLeafTest() {
        assertEquals(notLeaf.getFrequency(), 3);
        assertEquals(notLeaf.getSymbol(), 0);
        assertEquals(notLeaf.getLeft(), leaf);
        assertEquals(notLeaf.getRight(), null);
    }
}
