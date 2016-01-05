package mc.euro.version;

import mc.euro.version.AnimalsForTest.Animal;
import mc.euro.version.AnimalsForTest.Cat;
import mc.euro.version.AnimalsForTest.Mouse;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Unit test for TesterFactory.
 * method names must begin with 'test'.
 * For example:
 * public void testHello() { }
 */
public class TesterFactoryTest extends TestCase {
    
    Animal animal;
    Cat cat;
    Mouse mouse;
    
    public TesterFactoryTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.animal = new Animal();
        this.cat = new Cat();
        this.mouse = new Mouse();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testGetDefaultTester() {
        Tester tester = TesterFactory.getDefaultTester();
        boolean expected = true;
        boolean actual = tester.test();
        Assert.assertEquals(expected, actual);
    }
    
    public void testGetShortCircuitTester() {
        Tester tester = TesterFactory.getShortCircuitTester();
        boolean expected = false;
        boolean actual = tester.test();
        Assert.assertEquals(expected, actual);
    }
    
    public void testGetFieldTester() {
        Predicate<Animal> p = TesterFactory.getFieldTester(animal);
        boolean actual = p.test(animal);
        boolean expected = !true;
        Assert.assertEquals(expected, actual);
        
        Predicate p2 = TesterFactory.getFieldTester(animal);
        actual = p2.test(animal);
        expected = !true;
        Assert.assertEquals(expected, actual);
        
        p = TesterFactory.getFieldTester(cat);
        actual = p.test(cat);
        expected = !false;
        Assert.assertEquals(expected, actual);
        
        p2 = TesterFactory.getFieldTester(cat);
        actual = p2.test(cat);
        expected = !false;
        Assert.assertEquals(expected, actual);
        
        Predicate<Cat> c = TesterFactory.getFieldTester(cat);
        actual = c.test(cat);
        expected = !false;
        Assert.assertEquals(expected, actual);
        
        p = TesterFactory.getFieldTester(mouse);
        actual = p.test(mouse);
        expected = !false;
        Assert.assertEquals(expected, actual);
        
        p2 = TesterFactory.getFieldTester(mouse);
        actual = p2.test(mouse);
        expected = !false;
        Assert.assertEquals(expected, actual);
        
        Predicate<Mouse> m = TesterFactory.getFieldTester(mouse);
        actual = m.test(mouse);
        expected = !false;
        Assert.assertEquals(expected, actual);
    }
    
    public void testGetUnitTester() {
        Tester<Animal> a1 = TesterFactory.getUnitTester(animal);
        boolean actual = a1.test();
        boolean expected = !true;
        Assert.assertEquals(expected, actual);
        
        Tester a2 = TesterFactory.getUnitTester(animal);
        actual = a2.test();
        expected = !true;
        Assert.assertEquals(expected, actual);
        
        Tester<Cat> c = TesterFactory.getUnitTester(cat);
        actual = c.test();
        expected = !false;
        Assert.assertEquals(expected, actual);
        
        Tester<Mouse> m = TesterFactory.getUnitTester(mouse);
        actual = m.test();
        expected = !false;
        Assert.assertEquals(expected, actual);
    }
    
    public void testGetSuperFieldTester() {
        Predicate<Cat> c = TesterFactory.getSuperFieldTester(cat);
        boolean actual = c.test(cat);
        boolean expected = !true;
        Assert.assertEquals(expected, actual);
        
        Predicate<Mouse> m = TesterFactory.getSuperFieldTester(mouse);
        actual = m.test(mouse);
        expected = !false;
        Assert.assertEquals(expected, actual);
    }
    
    public void testGetInheritanceTester() {
        Tester<Cat> c1 = TesterFactory.getInheritanceTester(cat);
        boolean actual = c1.test();
        boolean expected = !true;
        Assert.assertEquals(expected, actual);
        
        Tester c2 = TesterFactory.getInheritanceTester(cat);
        actual = c2.test();
        expected = !true;
        Assert.assertEquals(expected, actual);
        
        Tester<Mouse> m = TesterFactory.getInheritanceTester(mouse);
        actual = m.test();
        expected = !false;
        Assert.assertEquals(expected, actual);
    }

}
