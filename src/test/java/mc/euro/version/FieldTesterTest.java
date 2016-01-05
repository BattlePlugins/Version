package mc.euro.version;

import mc.euro.version.AnimalsForTest.Animal;
import mc.euro.version.AnimalsForTest.Cat;
import mc.euro.version.AnimalsForTest.Mouse;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author Nikolai
 */
public class FieldTesterTest extends TestCase {
    
    Animal animal = new Animal();
    Cat cat = new Cat();
    Mouse mouse = new Mouse();
    
    public FieldTesterTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        FieldTester.isFullyInitialized(this);
        FieldTester.isInitialized(this);
    }
    
    public void testIsInitialized() {
        boolean actual = FieldTester.isInitialized(animal);
        boolean expected = false;
        Assert.assertEquals(expected, actual);
        
        actual = FieldTester.isInitialized(cat);
        expected = true;
        Assert.assertEquals(expected, actual);
        
        actual = FieldTester.isInitialized(mouse);
        expected = true;
        Assert.assertEquals(expected, actual);
    }
    
    public void testIsFullyInitialized() {
        boolean actual = FieldTester.isFullyInitialized(animal);
        boolean expected = false;
        Assert.assertEquals(expected, actual);
        
        actual = FieldTester.isFullyInitialized(cat);
        expected = false;
        Assert.assertEquals(expected, actual);
        
        actual = FieldTester.isFullyInitialized(mouse);
        expected = true;
        Assert.assertEquals(expected, actual);
    }
}
