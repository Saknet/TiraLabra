
package IO;

import java.io.FileOutputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BinaryOutputTest {
    
    public BinaryOutputTest() {
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
    public void writingTest() {
        // voidaan testata vasta "kunnolla" kun binaryinput stream on luotu ja
        // sitten sen avulla testata onko dataa kirjoitettu oikein.
        try {
            FileOutputStream fos = new FileOutputStream("testfiles/testoutput.txt");
            BinaryOutput bo = new BinaryOutput(fos);
            bo.clearBuffer();           
            bo.write(2132332423);
            bo.write(-123);
            bo.write(0);
            bo.writeBit(true);
            bo.writeBit(false);
            bo.close();
        } catch (Exception e) {
        }
    }
}
