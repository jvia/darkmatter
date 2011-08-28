package com.giantcow.darkmatter.player;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class PlayerSuite extends TestCase {
    
    public PlayerSuite(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("PlayerSuite");
        suite.addTest(HumanMatterTest.suite());
        suite.addTest(MatterTest.suite());
        suite.addTest(AIMatterTest.suite());
        suite.addTest(VelocityVectorTest.suite());
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

}
