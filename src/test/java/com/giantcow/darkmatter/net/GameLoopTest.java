package com.giantcow.darkmatter.net;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class GameLoopTest extends TestCase {

    public GameLoopTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(GameLoopTest.class);
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

    /** Test of run method, of class GameLoop. */
    public void testRun() {
        System.out.println("run");
        try {
            GameLoop.getGL().start();
        } catch (Exception e) {
            fail("Could not fun GameLoop");
        }
        assertTrue(true);
    }
}
