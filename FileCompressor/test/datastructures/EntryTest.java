
package datastructures;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EntryTest {
    
    public EntryTest() {
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
    public void keyTest() {
        Entry entry = new Entry(2, "jee", null);
        Assert.assertEquals(2, entry.getKey());
        Entry entry2 = new Entry(null, "jee", null);
        Assert.assertNull(entry2.getKey());
        entry2.setKey(3);
        Assert.assertEquals(3, entry2.getKey());
    }
    
    @Test
    public void valueTest() {
        Entry entry = new Entry(2, "jee", null);
        Assert.assertEquals("jee", entry.getValue());
        Entry entry2 = new Entry(3, null, null);
        Assert.assertNull(entry2.getValue());
        entry2.setValue("jee");
        Assert.assertEquals("jee", entry2.getValue());
    }
    
    @Test
    public void nextTest() {
        Entry entry3 = new Entry(4, "dsfsd", null);
        Entry entry = new Entry(2, "jee", entry3);
        Assert.assertEquals(entry3, entry.getNext());
        Entry entry2 = new Entry(3, null, null);
        Assert.assertNull(entry2.getNext());
        entry2.setNext(entry);
        Assert.assertEquals(entry, entry2.getNext());
    }
}
