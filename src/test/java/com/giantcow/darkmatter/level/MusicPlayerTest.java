package com.giantcow.darkmatter.level;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the MusicPlayer. Just a quick test to see if tracks can be loaded and played and then we interrupt the thread
 * and continue with the test suite
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 */
public class MusicPlayerTest extends TestCase {

    public MusicPlayerTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(MusicPlayerTest.class);
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

    /** Test of run method, of class MusicPlayer. */
    public void testRun() {
        System.out.println("run");
        MusicPlayer instance = new MusicPlayer();
        try {
            instance.start();
        } catch (Exception e) {
            fail("Could not play track");
        }

        instance.interrupt();
        assertTrue(true);
    }

}
