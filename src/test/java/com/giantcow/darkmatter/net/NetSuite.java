package com.giantcow.darkmatter.net;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class NetSuite extends TestCase {
    
    public NetSuite(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("NetSuite");
        suite.addTest(GameLoopTest.suite());
        suite.addTest(GameMsgTest.suite());
        suite.addTest(ClientTest.suite());
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
