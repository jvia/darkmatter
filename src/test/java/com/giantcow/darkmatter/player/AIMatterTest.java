package com.giantcow.darkmatter.player;

import java.util.Collection;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class AIMatterTest extends TestCase {
    
    public AIMatterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(AIMatterTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of changeMove method, of class AIMatter.
     */
    public void testChangeMove() {
        System.out.println("changeMove");
        double x = 0.0;
        double y = 0.0;
        Collection<Matter> others = null;
//        AIMatter instance = null;
//        Matter expResult = null;
//        Matter result = instance.changeMove(x, y, others);
        // TODO review the generated test code and remove the default call to fail.
        assertTrue(true);
    }

}
